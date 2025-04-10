package com.java.ide.service;

import com.java.ide.model.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

@Service
public class JavaExecutionService {
    private static final Logger logger = LoggerFactory.getLogger(JavaExecutionService.class);

    @Value("${javaexec.tempdir:/tmp/javaexec}")
    private String tempDirPath;

    @Value("${javaexec.timeout:5000}")
    private long timeoutMillis;

    public ExecutionResult executeJavaCode(String code) {
        long startTime = System.currentTimeMillis();
        String className = extractClassName(code);

        if (className == null) {
            return new ExecutionResult(false, "", "No public class found in code", 0);
        }

        try {
            Path tempDir = Files.createDirectories(Paths.get(tempDirPath));
            Path javaFile = tempDir.resolve(className + ".java");
            Files.writeString(javaFile, code, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            // Compile
            Process compileProcess = new ProcessBuilder("javac", javaFile.toString())
                    .directory(tempDir.toFile())
                    .redirectErrorStream(true)
                    .start();

            boolean compileSuccess = compileProcess.waitFor(timeoutMillis, TimeUnit.MILLISECONDS);

            if (!compileSuccess || compileProcess.exitValue() != 0) {
                String error = new String(compileProcess.getInputStream().readAllBytes());
                return new ExecutionResult(false, "", "Compilation error:\n" + error,
                        System.currentTimeMillis() - startTime);
            }

            // Execute
            Process runProcess = new ProcessBuilder("java", "-cp", tempDir.toString(), className)
                    .directory(tempDir.toFile())
                    .redirectErrorStream(true)
                    .start();

            boolean runSuccess = runProcess.waitFor(timeoutMillis, TimeUnit.MILLISECONDS);
            String output = new String(runProcess.getInputStream().readAllBytes());

            return new ExecutionResult(
                    runSuccess && runProcess.exitValue() == 0,
                    output,
                    runSuccess && runProcess.exitValue() != 0 ? output : "",
                    System.currentTimeMillis() - startTime
            );

        } catch (Exception e) {
            logger.error("Execution failed", e);
            return new ExecutionResult(false, "", "Execution error: " + e.getMessage(),
                    System.currentTimeMillis() - startTime);
        }
    }

    private String extractClassName(String code) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(code);
        return matcher.find() ? matcher.group(1) : null;
    }
}