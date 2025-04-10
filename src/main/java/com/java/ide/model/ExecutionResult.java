package com.java.ide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResult {
    private boolean success;
    private String output;
    private String error;
    private long executionTime;
}