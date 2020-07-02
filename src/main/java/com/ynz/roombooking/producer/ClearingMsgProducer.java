package com.ynz.roombooking.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClearingMsgProducer implements CommandLineRunner {
    private static String url = "http://localhost:8000/api/rooms";

    @Value("${amqp.queue.name}")
    private String queueName;

    private RestTemplate restTemplate;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    private ApplicationContext applicationContext;

    public ClearingMsgProducer(RestTemplate restTemplate, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper,
                               ApplicationContext applicationContext) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }

    public void run(String... args) throws Exception {
        List<Room> rooms = Arrays.asList(this.restTemplate.getForObject(url, Room[].class));
        rooms.forEach(room -> {
            log.info(room.toString());

            try {
                String jsonString = objectMapper.writeValueAsString(room);
                rabbitTemplate.convertAndSend(queueName, jsonString);
            } catch (JsonProcessingException e) {
                log.error("json parring error: ", e);
            }
        });

        System.exit(SpringApplication.exit(applicationContext));

    }
}
