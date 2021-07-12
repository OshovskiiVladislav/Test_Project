package com.test.spring.boot.services;

import com.test.spring.boot.model.FileDescriptor;
import com.test.spring.boot.model.Lesson;
import com.test.spring.boot.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final String filesDirectory = "files";
    private final LessonService lessonService;

    public List<FileDescriptor> findAll() {
        return fileRepository.findAll();
    }

    public Optional<FileDescriptor> findById(Long id) {
        return fileRepository.findById(id);
    }

    public FileDescriptor save(FileDescriptor fileDescriptor) {
        return fileRepository.save(fileDescriptor);
    }

    public FileDescriptor save(Long lessonId, MultipartFile file) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        Lesson lesson = lessonService.findById(lessonId).orElseThrow();
        try {
            Files.copy(file.getInputStream(), Paths.get(filesDirectory, file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            fileDescriptor.setName(file.getOriginalFilename());
            fileDescriptor.setPath(filesDirectory + "/" + file.getOriginalFilename());
            fileRepository.save(fileDescriptor);
            lesson.getFileDescriptor().add(fileDescriptor);
            lessonService.save(lesson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileDescriptor;
    }

}
