package com.test.spring.boot.services;

import com.test.spring.boot.model.Lesson;
import com.test.spring.boot.repositories.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = LessonService.class)
public class LessonServiceTest {
    @Autowired
    private LessonService lessonService;

    @MockBean
    private LessonRepository lessonRepository;

    private static final Long LESSON_ID_1 = 1L;
    private static final Long LESSON_ID_2 = 2L;
    private static final String LESSON_NAME_1 = "Lesson Spring";
    private static final String LESSON_NAME_2 = "Lesson Java";
    private static final String LESSON_DESCRIPTION_1 = "Hi hi hi!!!";
    private static final String LESSON_DESCRIPTION_2 = "Hi hi hi!!!";

    @Test
    public void whenFindAll_thenGetListLessons() {
        Lesson demoLesson1 = new Lesson();
        demoLesson1.setId(LESSON_ID_1);
        demoLesson1.setDescription(LESSON_DESCRIPTION_1);
        demoLesson1.setName(LESSON_NAME_1);
        demoLesson1.setFileDescriptor(new ArrayList<>());

        Lesson demoLesson2 = new Lesson();
        demoLesson2.setId(LESSON_ID_2);
        demoLesson2.setDescription(LESSON_DESCRIPTION_2);
        demoLesson2.setName(LESSON_NAME_2);
        demoLesson2.setFileDescriptor(new ArrayList<>());

        List<Lesson> demoList = new ArrayList<>();
        demoList.add(demoLesson1);
        demoList.add(demoLesson2);

        List<Lesson> targetList = new ArrayList<>();
        targetList.add(demoLesson1);
        targetList.add(demoLesson2);

        Mockito
                .doReturn(demoList)
                .when(lessonRepository)
                .findAll();

        Assertions.assertEquals(targetList, lessonService.findAll());
        Assertions.assertEquals(targetList.size(), lessonService.findAll().size());
        Mockito.verify(lessonRepository, Mockito.times(2)).findAll();
    }

    @Test
    public void whenFinaById_thenGetLessonById() {
        Lesson demoLesson = new Lesson();
        demoLesson.setId(LESSON_ID_1);
        demoLesson.setDescription(LESSON_DESCRIPTION_1);
        demoLesson.setName(LESSON_NAME_1);
        demoLesson.setFileDescriptor(new ArrayList<>());

        Lesson targetLesson = new Lesson();
        targetLesson.setId(LESSON_ID_1);
        targetLesson.setDescription(LESSON_DESCRIPTION_1);
        targetLesson.setName(LESSON_NAME_1);
        targetLesson.setFileDescriptor(new ArrayList<>());

        Mockito
                .doReturn(Optional.of(demoLesson))
                .when(lessonRepository)
                .findById(LESSON_ID_1);

        Assertions.assertEquals(targetLesson.getId(), lessonService.findById(LESSON_ID_1).get().getId());
        Assertions.assertEquals(targetLesson.getDescription(), lessonService.findById(LESSON_ID_1).get().getDescription());
        Assertions.assertEquals(targetLesson.getName(), lessonService.findById(LESSON_ID_1).get().getName());
        Mockito.verify(lessonRepository, Mockito.times(3)).findById(ArgumentMatchers.eq(LESSON_ID_1));
    }

    @Test
    public void whenSaveNewLesson_thenGetSavedLesson() {
        Lesson demoLesson = new Lesson();
        demoLesson.setId(LESSON_ID_1);
        demoLesson.setDescription(LESSON_DESCRIPTION_1);
        demoLesson.setName(LESSON_NAME_1);
        demoLesson.setFileDescriptor(new ArrayList<>());

        Lesson targetLesson = new Lesson();
        targetLesson.setId(LESSON_ID_1);
        targetLesson.setDescription(LESSON_DESCRIPTION_1);
        targetLesson.setName(LESSON_NAME_1);
        targetLesson.setFileDescriptor(new ArrayList<>());

        Lesson returnLesson = new Lesson();
        returnLesson.setId(LESSON_ID_1);
        returnLesson.setDescription(LESSON_DESCRIPTION_1);
        returnLesson.setName(LESSON_NAME_1);
        returnLesson.setFileDescriptor(new ArrayList<>());

        Mockito
                .doReturn(returnLesson)
                .when(lessonRepository)
                .save(demoLesson);

        Assertions.assertEquals(targetLesson, lessonService.save(demoLesson));
        Assertions.assertEquals(targetLesson.getId(), lessonService.save(demoLesson).getId());
        Assertions.assertEquals(targetLesson.getName(), lessonService.save(demoLesson).getName());
        Assertions.assertEquals(targetLesson.getDescription(), lessonService.save(demoLesson).getDescription());

    }
}
