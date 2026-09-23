package com.fitness.aiservice.service;


import org.springframework.stereotype.Service;
import java.util.List;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {
	
	private final RecommendationRepository rr;

	public List<Recommendation> getRecommendationsUSER(String userId) {
		return rr.findByUserId(userId);
	}

	public Recommendation getRecommendationACTIVITY(String activityId) {
		return rr.findByActivityId(activityId).orElseThrow(()->new RuntimeException("Recommendation not found for activity id " + activityId));
	}
	
	

}
