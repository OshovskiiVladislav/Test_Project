package com.test.spring.boot.controllers;

import com.test.spring.boot.model.FileDescriptor;
import com.test.spring.boot.model.Lesson;
import com.test.spring.boot.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Lesson lesson = lessonService.findById(id).orElseThrow(() -> new RuntimeException("Lesson with id: " + id + " doesn't exists (for edit)"));
        model.addAttribute("lesson", lesson);
        return "lessons_edit";
    }

    @GetMapping("/edit/{id}/upload")
    public String showFileUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", id);
        return "file_upload";
    }

    @PostMapping("/edit")
    public String showEditForm(@ModelAttribute Lesson lesson) {
        lessonService.save(lesson);
        return "redirect:/lessons";
    }
}
