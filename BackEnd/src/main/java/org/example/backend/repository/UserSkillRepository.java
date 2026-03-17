package org.example.backend.repository;

import org.example.backend.entity.User;
import org.example.backend.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUser(User user);

    @Query("SELECT us.skill.skillName, COUNT(us) as skillCount " +
            "FROM UserSkill us " +
            "WHERE us.type = 'LEARN' " +
            "GROUP BY us.skill.skillName " +
            "ORDER BY skillCount DESC")
    List<Object[]> findTrendingSkills();
}