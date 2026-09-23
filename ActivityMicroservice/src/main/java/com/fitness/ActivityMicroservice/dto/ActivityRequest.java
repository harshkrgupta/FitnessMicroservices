package com.fitness.ActivityMicroservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fitness.ActivityMicroservice.model.ActivityType;

import lombok.Data;

@Data
public class ActivityRequest {
	
	private ActivityType type;
	
	private Integer duration;
	
	private Integer caloriesBurnt;
	
	private LocalDateTime startedAt;
	
	private String userid;
	
	private Map<String,Object> addtionalMetrics;
}
