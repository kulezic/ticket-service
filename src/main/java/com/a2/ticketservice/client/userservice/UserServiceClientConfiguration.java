package com.a2.ticketservice.client.userservice;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public class UserServiceClientConfiguration {
    @Bean
    public RestTemplate userServiceRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        //TODO Address of the user service
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/api"));
        return restTemplate;
    }
}
