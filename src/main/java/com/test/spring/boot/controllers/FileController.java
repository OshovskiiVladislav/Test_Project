package com.test.spring.boot.controllers;

import com.test.spring.boot.model.FileDescriptor;
import com.test.spring.boot.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("files", fileService.findAll());
        return "files";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FileDescriptor p = fileService.findById(id).orElseThrow(() -> new RuntimeException("File with id: " + id + " doesn't exists (for edit)"));
        model.addAttribute("file", p);
        return "files_edit";
    }

    @PostMapping("/edit")
    public String showEditForm(@ModelAttribute FileDescriptor fileDescriptor) {
        fileService.save(fileDescriptor);
        return "redirect:/files";
    }

    @PostMapping("/uploadFileWithAddtionalData")
    public String submit(@RequestParam MultipartFile file, @RequestParam Long lessonId, ModelMap modelMap) {
        modelMap.addAttribute("id", lessonId);
        fileService.save(lessonId, file);
        return "redirect:/lessons/edit/" + lessonId;
    }
}
