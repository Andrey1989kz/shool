package ru.hogwarts.shool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.shool.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    Optional<Avatar> findByStudent(Long studentId);
}
