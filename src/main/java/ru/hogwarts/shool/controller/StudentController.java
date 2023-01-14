package ru.hogwarts.shool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.shool.model.Faculty;
import ru.hogwarts.shool.model.Student;
import ru.hogwarts.shool.service.AvatarService;
import ru.hogwarts.shool.service.StudentService;

import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findByAgeBetween(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentFaculty(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAllStudentsNumber() {
        return ResponseEntity.ok(studentService.getAllStudentsNumber());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/average-age-with-stream")
    public ResponseEntity<Double> getAverageAgeWithStream() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/last-students")
    public ResponseEntity<List<Student>> getLastStudentsById(@RequestParam Integer limit) {
        return ResponseEntity.ok(studentService.getLastStudentsById(limit));
    }

    @GetMapping("/name-starts-with")
    public ResponseEntity<List<String>> getStudentsByNameStartsWith(@RequestParam String letter) {
        return ResponseEntity.ok(studentService.getStudentsByNameStartsWith(letter));
    }

    @GetMapping("/names")
    public void echoAllStudentNames() {
        studentService.echoAllStudentNames();
    }

    @GetMapping("/names-sync")
    public void echoAllStudentNamesSync() {
        studentService.echoAllStudentNamesSync();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}