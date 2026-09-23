	package com.fitness.ActivityMicroservice.model;
	
	import java.time.LocalDateTime;
	import java.util.Map;
	
	import org.springframework.data.annotation.CreatedDate;
	import org.springframework.data.annotation.Id;
	import org.springframework.data.annotation.LastModifiedDate;
	import org.springframework.data.mongodb.core.mapping.Document;
	import org.springframework.data.mongodb.core.mapping.Field;
	
	import lombok.AllArgsConstructor;
	import lombok.Builder;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Document(collection = "activities")
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public class Activity {
		
		@Id
		private String id;
		
		private String userid;
		private ActivityType type;
		private Integer duration;
		private Integer caloriesBurnt;
		private LocalDateTime startedAt;
		
		@CreatedDate
		private LocalDateTime createdAt;
		
		@LastModifiedDate
		private LocalDateTime updatedAt;
		
		@Field("metrics")
		private Map<String,Object> addtionalMetrics;
	}
