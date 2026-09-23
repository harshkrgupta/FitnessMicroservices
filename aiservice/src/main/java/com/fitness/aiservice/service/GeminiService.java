package com.fitness.aiservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {  // in gemini service we are just making a call
	
    private final WebClient webClient;

    @Value("${gemini.api.url}")     //this is gemini api url

    private String geminiApiUri;	
	

    @Value("${gemini.api.key}")
    private String geminiApiKey;
   
    
    
    public String getAnswer(String prompt) {
    	
    	
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", prompt)
                        })
                });

        
        String response = webClient.post()
                .uri(geminiApiUri)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        return response;
    
    }
}