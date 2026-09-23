package com.fitness.ActivityMicroservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fitness.ActivityMicroservice.dto.ActivityRequest;
import com.fitness.ActivityMicroservice.dto.ActivityResponse;
import com.fitness.ActivityMicroservice.model.Activity;
import com.fitness.ActivityMicroservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service //it becomes a spring manage component
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
	
	private final ActivityRepository activityRepository;
	
	private final RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	
	private final UserValidationService userValidationService;

	public ActivityResponse trackActivity(ActivityRequest request) {
		boolean validUser = userValidationService.validateUser(request.getUserid());
		if(!validUser) throw new RuntimeException("Invalid User Id"+request.getUserid());
		Activity activity = Activity.builder()
				.userid(request.getUserid())
				.duration(request.getDuration())
				.caloriesBurnt(request.getCaloriesBurnt())
				.type(request.getType())
				.startedAt(request.getStartedAt())
				.addtionalMetrics(request.getAddtionalMetrics())
				.build();
		
		Activity save = activityRepository.save(activity);
		
		//publish to rabbitMQ for AI processing;
		
		
		try {
			rabbitTemplate.convertAndSend(exchange,routingKey,save);
		}catch(Exception E) {
			log.error("Failed to publish activity to RabbitMQ",E);
		}
		
		
		return mapToResponse(save);
		
		
		
	}
	
	private ActivityResponse mapToResponse(Activity activity) {
		ActivityResponse response = ActivityResponse.builder()
				.userid(activity.getUserid())
				.createdAt(activity.getCreatedAt())
				.duration(activity.getDuration())
				.type(activity.getType())
				.addtionalMetrics(activity.getAddtionalMetrics())
				.caloriesBurnt(activity.getCaloriesBurnt())
				.updatedAt(activity.getUpdatedAt())
				.id(activity.getId())
				.startedAt(activity.getStartedAt())
				.build();
		
		return response;

	}

	public List<ActivityResponse> getActivities(String userid) {
		List<Activity> l= activityRepository.findByUserid(userid);
		return l.stream()
				.map(this:: mapToResponse)
				.collect(Collectors.toList());
	}

	public ActivityResponse getActivityById(String acitivityId) {
	    Activity activity = activityRepository.findById(acitivityId)
	            .orElseThrow(() -> new RuntimeException("Activity not found"));

	    return mapToResponse(activity);
	}

}
