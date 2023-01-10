package ru.hogwarts.shool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.shool.model.Faculty;
import ru.hogwarts.shool.model.Student;
import ru.hogwarts.shool.repository.FacultyRepository;
import ru.hogwarts.shool.repository.StudentRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        logger.debug("Calling constructor FacultyService");
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Calling method createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("Calling method findFaculty (facultyId = {})", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Calling method editFaculty (facultyId = {})", faculty.getId());
        if (facultyRepository.findById(faculty.getId()).orElse(null) == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.debug("Calling method deleteFaculty (facultyId = {})", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.debug("Calling method getFacultiesByColor (color = {})", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findFacultiesByNameOrColor(String searchStr) {
          logger.debug("Calling method findFacultiesByNameOrColor (searchStr = {})", searchStr);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(searchStr, searchStr);
    }

    public Collection<Student> getFacultyStudents(long id) {
        logger.debug("Calling method getFacultyStudents (facultyId = {})", id);
        Faculty faculty = findFaculty(id);
        if (faculty == null) {
            return null;
        }
        return studentRepository.findByFacultyId(faculty.getId());
    }
}
