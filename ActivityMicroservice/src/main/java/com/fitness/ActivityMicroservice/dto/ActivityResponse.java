package com.fitness.ActivityMicroservice.dto;

import java.time.LocalDateTime;
import java.util.Map;


import com.fitness.ActivityMicroservice.model.ActivityType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityResponse {
	private String id;
	
	private String userid;
	private ActivityType type;
	private Integer duration;
	private Integer caloriesBurnt;
	private LocalDateTime startedAt;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private Map<String,Object> addtionalMetrics;
}
