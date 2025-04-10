package com.java.ide.repository;

import com.java.ide.model.CodeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<CodeFile, Long> {
}