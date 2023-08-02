package com.Portfolio.Handy.repository;

import com.Portfolio.Handy.entity.Shortcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortCutRepository extends JpaRepository<Shortcut, Long> {
    List<Shortcut> findByGroupId(Long groupId);
}
