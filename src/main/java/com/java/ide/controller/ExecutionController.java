package com.java.ide.controller;

import com.java.ide.model.ExecutionResult;
import com.java.ide.service.JavaExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/execute")
@RequiredArgsConstructor
public class ExecutionController {
    private final JavaExecutionService executionService;

    @PostMapping
    public ExecutionResult execute(@RequestBody Map<String, String> request) {
        return executionService.executeJavaCode(request.get("code"));
    }
}