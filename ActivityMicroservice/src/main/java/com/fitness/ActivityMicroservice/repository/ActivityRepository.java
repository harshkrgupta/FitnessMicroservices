package com.fitness.ActivityMicroservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.ActivityMicroservice.dto.ActivityResponse;
import com.fitness.ActivityMicroservice.model.Activity;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String>{

	List<Activity> findByUserid(String userid);

}
