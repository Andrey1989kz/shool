package ru.hogwarts.shool.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.shool.model.Avatar;
import ru.hogwarts.shool.model.Student;
import ru.hogwarts.shool.service.AvatarService;
import ru.hogwarts.shool.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")


public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.getStudentsByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {

        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student student1 = studentService.editStudent(student);
        if (student1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findByAgeBetween(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @PostMapping(value = "{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam MultipartFile images) throws IOException {
        if (images.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadImage(id, images);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Collection<Avatar>> getAll(@RequestParam("page") Integer pageNumber,
                                                     @RequestParam("size") Integer pageSize) {
        return avatarService.getAll(pageNumber, pageSize);
    }
}
