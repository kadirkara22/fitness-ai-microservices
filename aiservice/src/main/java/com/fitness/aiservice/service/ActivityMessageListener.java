package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityMessageListener {

    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

    public ActivityMessageListener(ActivityAIService aiService, RecommendationRepository recommendationRepository) {
        this.aiService = aiService;
        this.recommendationRepository = recommendationRepository;
    }
    @Value("${rabbitmq.queue.name}")
    private  String queueName;

    @RabbitListener(queues ="activity.queue" )
    public void processActivity(Activity activity){
        log.info("Received activity for processing: {}", activity.getId());
        //log.info("Generated Recommendation: {}", aiService.generateRecommendation(activity));

        Recommendation recommendation =aiService.generateRecommendation(activity);

        recommendationRepository.save(recommendation);
    }

}
