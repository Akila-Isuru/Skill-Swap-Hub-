package org.example.backend.repository;

import org.example.backend.entity.User;
import org.example.backend.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUser(User user);
}