package com.example.codingexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.concurrent.Executor;

@SpringBootApplication
public class CodingExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingExerciseApplication.class, args);
    }
    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }

}
