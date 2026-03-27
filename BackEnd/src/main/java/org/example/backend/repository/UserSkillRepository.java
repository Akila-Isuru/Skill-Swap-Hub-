package org.example.backend.repository;

import org.example.backend.entity.User;
import org.example.backend.entity.UserSkill;
import org.example.backend.entity.enums.SkillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUser(User user);

    @Query("SELECT us.skill.skillName, COUNT(us) as skillCount " +
            "FROM UserSkill us " +
            "WHERE us.type = 'LEARN' " +
            "GROUP BY us.skill.skillName " +
            "ORDER BY skillCount DESC")
    List<Object[]> findTrendingSkills();


    @Query("SELECT us FROM UserSkill us WHERE us.skill.id IN :skillIds AND us.type = :type AND us.user.id != :userId")
    List<UserSkill> findMatches(@Param("skillIds") List<Long> skillIds, @Param("type") SkillType type, @Param("userId") Long userId);
}