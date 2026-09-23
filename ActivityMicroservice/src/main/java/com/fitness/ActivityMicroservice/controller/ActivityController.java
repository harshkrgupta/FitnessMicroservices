package com.fitness.ActivityMicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.ActivityMicroservice.dto.ActivityRequest;
import com.fitness.ActivityMicroservice.dto.ActivityResponse;
import com.fitness.ActivityMicroservice.service.ActivityService;

@RestController
@RequestMapping("/activity")
public class ActivityController {
	
	
	@Autowired
	private ActivityService activityService;
	
	@PostMapping()
	public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request){
		
		return ResponseEntity.ok(activityService.trackActivity(request));
	}
	
	@GetMapping()
	public ResponseEntity<List<ActivityResponse>> getActivities(@RequestHeader("X-User-ID") String userid){
		return ResponseEntity.ok(activityService.getActivities(userid));
	}
	
	
	@GetMapping("/{acitivityId}")
	public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String acitivityId){
		return ResponseEntity.ok(activityService.getActivityById(acitivityId));
	}
	
}
