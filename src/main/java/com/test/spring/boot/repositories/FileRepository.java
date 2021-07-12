package com.test.spring.boot.repositories;

import com.test.spring.boot.model.FileDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDescriptor, Long> {
}
