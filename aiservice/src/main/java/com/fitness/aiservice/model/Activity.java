package com.fitness.aiservice.model;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class Activity {
	private String id;
	
	private String userid;
	private String type;
	private Integer duration;
	private Integer caloriesBurnt;
	private LocalDateTime startedAt;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private Map<String,Object> addtionalMetrics;
}

