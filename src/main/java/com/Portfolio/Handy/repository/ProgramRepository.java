package com.Portfolio.Handy.repository;

import com.Portfolio.Handy.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByMemberId(Long MemberId);

    @Query(value = "SELECT a.* FROM Program AS a INNER JOIN ShortCutGroup AS b ON a.id = b.program_id WHERE b.id = :groupId", nativeQuery = true)
    Program findByGroupId(@Param("groupId") Long groupId);
}
