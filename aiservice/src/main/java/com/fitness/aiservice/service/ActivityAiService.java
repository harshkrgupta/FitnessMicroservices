package com.fitness.aiservice.service;
import com.fitness.aiservice.model.Recommendation;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
	
	
	private final GeminiService geminiService;
	
	public Recommendation getRecommendation(Activity activity) {
		
		//generating prompt for activity
		String prompt = createPromptForActivity(activity);
		
		
		String aiResponse = geminiService.getAnswer(prompt);
//		log.info("Response From AI: {}",aiResponse);
		
		
		return processAiResponse(activity,aiResponse);  //converting airesponse(string) to json and a getting a recommendation with activity id n all.
//		return aiResponse;
	}
	
	private Recommendation processAiResponse(Activity activity, String aiResponse) {
		
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode rootNode = mapper.readTree(aiResponse);
	        JsonNode textNode = rootNode.path("candidates")
	                .get(0).path("content").path("parts").get(0).path("text");

	        String jsonContent = textNode.asText().trim()
	                .replaceAll("^```(?:json)?\\s*", "")
	                .replaceAll("\\s*```$", "")
	                .trim();

	        JsonNode analysisJson = mapper.readTree(jsonContent);

	        // 1. Build the flattened "recommendation" analysis string
	        JsonNode analysisNode = analysisJson.path("analysis");
	        
	        
	        
	        //getting analysis as string
	        StringBuilder fullAnalysis = new StringBuilder();
	        
	        addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall: ");
	        addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace: ");
	        addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate: ");
	        addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories: ");

	        // 2. Flatten "improvements" objects into strings
	        List<String> improvements = new ArrayList<>();
	        for (JsonNode improvement : analysisJson.path("improvements")) {
	            String area = improvement.path("area").asText();
	            String recommendation = improvement.path("recommendation").asText();
	            improvements.add(area + ": " + recommendation);
	        }

	        // 3. Flatten "suggestions" objects into strings
	        List<String> suggestions = new ArrayList<>();
	        for (JsonNode suggestion : analysisJson.path("suggestions")) {
	            String workout = suggestion.path("workout").asText();
	            String description = suggestion.path("description").asText();
	            suggestions.add(workout + ": " + description);
	        }

	        // 4. "safety" already matches List<String>, so this is direct
	        List<String> safety = new ArrayList<>();
	        for (JsonNode safetyItem : analysisJson.path("safety")) {
	            safety.add(safetyItem.asText());
	        }

	        Recommendation recommendation = Recommendation.builder()
	                .activityId(activity.getId())
	                .userId(activity.getUserid())
	                .activityType(activity.getType())
	                .recommendation(fullAnalysis.toString())
	                .improvements(improvements)
	                .suggestions(suggestions)
	                .safety(safety)
	                .build();

//	        recommendationRepository.save(recommendation);
	        
	        return recommendation;

	    } catch (Exception e) {
	        log.error("Failed to process AI response for activity {}", activity.getId(), e);
	        return defaultRecommendation(activity);
	    }
	}

	private Recommendation defaultRecommendation(Activity activity) {
		return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserid())
                .activityType(activity.getType())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness professional"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
	}

	private void addAnalysisSection(StringBuilder fullanalysis, JsonNode analysisnode, String key, String prefix) {
		if(!analysisnode.path(key).isMissingNode()) {
			fullanalysis.append(prefix).append(analysisnode.path(key).asText())
			.append("\n\n");
		}
	}

	private String createPromptForActivity(Activity activity) {
		return String.format("""
		        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
		        {
		          "analysis": {
		            "overall": "Overall analysis here",
		            "pace": "Pace analysis here",
		            "heartRate": "Heart rate analysis here",
		            "caloriesBurned": "Calories analysis here"
		          },
		          "improvements": [
		            {
		              "area": "Area name",
		              "recommendation": "Detailed recommendation"
		            }
		          ],
		          "suggestions": [
		            {
		              "workout": "Workout name",
		              "description": "Detailed workout description"
		            }
		          ],
		          "safety": [
		            "Safety point 1",
		            "Safety point 2"
		          ]
		        }

		        Analyze this activity:
		        Activity Type: %s
		        Duration: %d minutes
		        Calories Burned: %d
		        Additional Metrics: %s
		        
		        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
		        Ensure the response follows the EXACT JSON format shown above.
		        """,
		                activity.getType(),
		                activity.getDuration(),
		                activity.getCaloriesBurnt(),
		                activity.getAddtionalMetrics()
		        );
	}
	
}
