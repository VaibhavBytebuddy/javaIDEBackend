package com.java.ide.controller;


import com.java.ide.model.CodeFile;
import com.java.ide.repository.FileRepository;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class EditorController {

    private final FileRepository fileRepository;

    @GetMapping
    public List<CodeFile> getAllFiles()
    {
        return fileRepository.findAll();
    }

    @PostMapping
    public CodeFile saveFile(@RequestBody CodeFile file)
    {
        if (file.getCreatedAt()==null)
        {
            file.setCreatedAt(LocalDateTime.now());
        }
        return fileRepository.save(file);
    }
}
