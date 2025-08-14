package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    public ActivityService(ActivityRepository activityRepository, UserValidationService userValidationService) {
        this.activityRepository = activityRepository;
        this.userValidationService = userValidationService;
    }


    public ActivityResponse trackActivity(ActivityRequest request) {
       boolean isValidUser= userValidationService.validateUser(request.getUserId());
       if(!isValidUser){
           throw new RuntimeException("Invalid User: "+request.getUserId());
       }
        Activity activity= Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity saveActivity=activityRepository.save(activity);
        return mapToResponse(saveActivity);


    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities=activityRepository.findAByUserId(userId);

        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }


    private ActivityResponse mapToResponse(Activity activity){
         return ActivityResponse.builder()
                .id(activity.getId())
                 .userId(activity.getUserId())
                 .type(activity.getType())
                 .duration(activity.getDuration())
                 .caloriesBurned(activity.getCaloriesBurned())
                 .startTime(activity.getStartTime())
                 .additionalMetrics(activity.getAdditionalMetrics())
                 .createdAt(activity.getCreatedAt())
                 .updatedAt(activity.getUpdatedAt())
                 .build();

    }


    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(()-> new RuntimeException("Activity not found with id: "+activityId));
    }
}
