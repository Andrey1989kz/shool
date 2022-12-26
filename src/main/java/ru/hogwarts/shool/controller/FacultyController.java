package ru.hogwarts.shool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.shool.model.Faculty;
import ru.hogwarts.shool.model.Student;
import ru.hogwarts.shool.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping(value = "/color", params = "color")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
    }

    @GetMapping(value = "/color", params = "searchStr")
    public ResponseEntity<Collection<Faculty>> findFacultiesByNameOrColor(@RequestParam(required = false) String searchStr) {
        Collection<Faculty> faculties = facultyService.findFacultiesByNameOrColor(searchStr);
        if (faculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyStudents(id));
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.editFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}



