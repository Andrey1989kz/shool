package ru.hogwarts.shool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.shool.model.Faculty;
import ru.hogwarts.shool.model.Student;
import ru.hogwarts.shool.repository.StudentRepository;

import java.util.Collection;

@Service

public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        logger.debug("Calling the constructor StudentService");
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Student createStudent(Student student) {
        logger.debug("Calling the method createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("Calling the method findStudent (studentId = {})", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.debug("Calling the method editStudent (studentId = {})", student.getId());
        if (studentRepository.findById(student.getId()).orElse(null) == null) {
            return null;
        }
        return studentRepository.save(student);
    }
    public void deleteStudent(long id) {
        logger.debug("Calling method deleteStudent (studentId = {})", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Calling method findByAgeBetween (minAge = {}, maxAge = {})", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(long id) {
        logger.debug("Calling method getStudentFaculty (studentId = {})", id);
        Student student = findStudent(id);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }
}

