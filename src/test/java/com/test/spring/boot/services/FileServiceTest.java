package com.test.spring.boot.services;

import com.test.spring.boot.model.FileDescriptor;
import com.test.spring.boot.model.Lesson;
import com.test.spring.boot.repositories.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = FileService.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    @MockBean
    private LessonService lessonService;

    private static final String FILE_DESCRIPTOR_NAME_1 = "Lesson 1";
    private static final String FILE_DESCRIPTOR_NAME_2 = "Lesson 2";
    private static final String FILE_DESCRIPTOR_FILE_NAME = "hello.txt";
    private static final Long FILE_DESCRIPTOR_ID_1 = 1L;
    private static final Long FILE_DESCRIPTOR_ID_2 = 2L;
    private static final Long LESSON_ID_1 = 1L;
    private static final String FILE_DESCRIPTOR_PATH_1 = "C:/qwerty";
    private static final String FILE_DESCRIPTOR_PATH_2 = "files/hello.txt";
    private static final String LESSON_NAME = "Lesson name";
    private static final String LESSON_DESCRIPTION = "Hi hi hi!!!";



    @Test
    public void testFindAllTest(){
        FileDescriptor demoFileDescriptor1 = new FileDescriptor();
        demoFileDescriptor1.setId(FILE_DESCRIPTOR_ID_1);
        demoFileDescriptor1.setName(FILE_DESCRIPTOR_NAME_1);

        FileDescriptor demoFileDescriptor2 = new FileDescriptor();
        demoFileDescriptor2.setId(FILE_DESCRIPTOR_ID_2);
        demoFileDescriptor2.setName(FILE_DESCRIPTOR_NAME_2);

        List<FileDescriptor> targetList = new ArrayList<>();
        targetList.add(demoFileDescriptor1);
        targetList.add(demoFileDescriptor2);

        List<FileDescriptor> demoList = new ArrayList<>();
        demoList.add(demoFileDescriptor1);
        demoList.add(demoFileDescriptor2);

        Mockito
                .doReturn(demoList)
                .when(fileRepository)
                .findAll();

        Assertions.assertEquals(targetList, fileService.findAll());
        Assertions.assertEquals(targetList.size(), fileService.findAll().size());
        Mockito.verify(fileRepository, Mockito.times(2)).findAll();
    }

    @Test
    public void findByIdTest(){
        FileDescriptor demoFileDescriptor = new FileDescriptor();
        demoFileDescriptor.setId(FILE_DESCRIPTOR_ID_1);
        demoFileDescriptor.setName(FILE_DESCRIPTOR_NAME_1);
        demoFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_1);

        FileDescriptor targetFileDescriptor = new FileDescriptor();
        targetFileDescriptor.setId(FILE_DESCRIPTOR_ID_1);
        targetFileDescriptor.setName(FILE_DESCRIPTOR_NAME_1);
        targetFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_1);

        Mockito
                .doReturn(Optional.of(demoFileDescriptor))
                .when(fileRepository)
                .findById(FILE_DESCRIPTOR_ID_1);

        Assertions.assertEquals(targetFileDescriptor.getId(), fileService.findById(FILE_DESCRIPTOR_ID_1).get().getId());
        Assertions.assertEquals(targetFileDescriptor.getName(), fileService.findById(FILE_DESCRIPTOR_ID_1).get().getName());
        Assertions.assertEquals(targetFileDescriptor.getPath(), fileService.findById(FILE_DESCRIPTOR_ID_1).get().getPath());
    }

    @Test
    public void saveTest(){
        FileDescriptor demoFileDescriptor = new FileDescriptor();
        demoFileDescriptor.setName(FILE_DESCRIPTOR_NAME_1);
        demoFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_1);

        FileDescriptor returnFileDescriptor = new FileDescriptor();
        returnFileDescriptor.setId(FILE_DESCRIPTOR_ID_1);
        returnFileDescriptor.setName(FILE_DESCRIPTOR_NAME_1);
        returnFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_1);

        FileDescriptor targetFileDescriptor = new FileDescriptor();
        targetFileDescriptor.setId(FILE_DESCRIPTOR_ID_1);
        targetFileDescriptor.setName(FILE_DESCRIPTOR_NAME_1);
        targetFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_1);

        Mockito
                .doReturn(returnFileDescriptor)
                .when(fileRepository)
                .save(demoFileDescriptor);

        Assertions.assertEquals(targetFileDescriptor, fileService.save(demoFileDescriptor));
        Assertions.assertEquals(targetFileDescriptor.getId(), fileService.save(demoFileDescriptor).getId());
        Assertions.assertEquals(targetFileDescriptor.getName(), fileService.save(demoFileDescriptor).getName());
        Assertions.assertEquals(targetFileDescriptor.getPath(), fileService.save(demoFileDescriptor).getPath());
        Mockito.verify(fileRepository, Mockito.times(4)).save(ArgumentMatchers.eq(demoFileDescriptor));
    }

    @Test
    public void uploadFileTest_thenNewFileDescriptor(){
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                FILE_DESCRIPTOR_FILE_NAME,
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        Lesson demoLesson = new Lesson();
        demoLesson.setId(LESSON_ID_1);
        demoLesson.setDescription(LESSON_DESCRIPTION);
        demoLesson.setName(LESSON_NAME);
        demoLesson.setFileDescriptor(new ArrayList<>());

        Mockito
                .doReturn(Optional.of(demoLesson))
                .when(lessonService)
                .findById(LESSON_ID_1);

        FileDescriptor returnFileDescriptor = new FileDescriptor();
        returnFileDescriptor.setId(FILE_DESCRIPTOR_ID_1);
        returnFileDescriptor.setName(FILE_DESCRIPTOR_FILE_NAME);
        returnFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_2);

        Mockito
                .doReturn(returnFileDescriptor)
                .when(fileRepository)
                .save(any(FileDescriptor.class));

        Mockito
                .doReturn(demoLesson)
                .when(lessonService)
                .save(demoLesson);

        FileDescriptor targetFileDescriptor = new FileDescriptor();
        targetFileDescriptor.setId(FILE_DESCRIPTOR_ID_1);
        targetFileDescriptor.setName(FILE_DESCRIPTOR_FILE_NAME);
        targetFileDescriptor.setPath(FILE_DESCRIPTOR_PATH_2);


        Assertions.assertEquals(targetFileDescriptor.getName(), fileService.uploadFile(LESSON_ID_1, mockMultipartFile).getName());
        Assertions.assertEquals(targetFileDescriptor.getPath(), fileService.uploadFile(LESSON_ID_1, mockMultipartFile).getPath());
        Mockito.verify(fileRepository, Mockito.times(2)).save(any(FileDescriptor.class));



    }
}
