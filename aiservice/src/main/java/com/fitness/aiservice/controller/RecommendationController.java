package com.fitness.aiservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {
	private final RecommendationService rs;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Recommendation>> getRecommendationsUSER(@PathVariable String userId){
		return ResponseEntity.ok(rs.getRecommendationsUSER(userId));
	}
	
	@GetMapping("/activity/{activityId}")
	public ResponseEntity<Recommendation> getRecommendationACTIVITY(@PathVariable String activityId){
		return ResponseEntity.ok(rs.getRecommendationACTIVITY(activityId));
	}
}
