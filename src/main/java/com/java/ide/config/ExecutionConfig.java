package com.java.ide.config;

import com.java.ide.service.JavaExecutionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionConfig {
    @Bean
    public JavaExecutionService javaExecutionService() {
        return new JavaExecutionService();
    }
}