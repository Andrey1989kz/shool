package ru.hogwarts.shool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.shool.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Collection<Student> findByAge(int age);

    public Collection<Student> findByAgeBetween(int min, int max);

    public Collection<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    public Long getAllStudentsNumber();

    @Query(value = "SELECT AVG (age) FROM Student", nativeQuery = true)
    public Double getAverageAgeOfAllStudents();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    public List<Student> getLastStudents(@Param("limit") int limit);
}

