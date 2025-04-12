package com.java.ide.controller;

import com.java.ide.model.ExecutionResult;
import com.java.ide.service.JavaExecutionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/execute")
public class ExecutionController {
    private final JavaExecutionService executionService;

    public ExecutionController(JavaExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping
    public ExecutionResult execute(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String input = request.get("input");
        return executionService.executeJavaCode(code, input);
    }
}