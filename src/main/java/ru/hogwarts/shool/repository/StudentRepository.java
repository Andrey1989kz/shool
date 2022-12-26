package ru.hogwarts.shool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.shool.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Collection<Student> findByAge(int age);

    public Collection<Student> findByAgeBetween(int min, int max);

    public Collection<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT (*) FROM Student", nativeQuery = true)
    Long getAmountOfAllStudents();

    @Query(value = "SELECT AVG (age) FROM Student", nativeQuery = true)
    Double getAverageAgeOfAllStudents();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}

