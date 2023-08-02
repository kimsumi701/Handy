package com.Portfolio.Handy.repository;

import com.Portfolio.Handy.entity.Shortcutgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortCutGroupRepository extends JpaRepository<Shortcutgroup, Long> {
    List<Shortcutgroup> findByProgramId(Long programId);

    @Query(value = "SELECT a.* "+
            "FROM ShortCutGroup AS a " +
            "INNER JOIN Program AS b ON b.id = a.program_id " +
            "WHERE b.member_id = :memberId " +
            "ORDER BY a.isFavorite DESC, a.modifiedDate", nativeQuery = true)
    List<Shortcutgroup> orderByFavorite(@Param("memberId") Long Id);

    @Query(value = "SELECT a.* " +
            "FROM ShortCutGroup AS a " +
            "INNER JOIN Program as b ON a.program_id = b.id " +
            "INNER JOIN Member as c ON b.member_id = c.id " +
            "WHERE c.id = :memberId AND a.name Like %:serachText%", nativeQuery = true)
    List<Shortcutgroup> serachByText(@Param("memberId") Long memberId, @Param("serachText") String serachText);

    @Query(value = "SELECT TOP(6) a.id, a.program_id, a.name, a.isFavorite, a.createdDate, " +
            "modifiedDate = (SELECT TOP(1) modifiedDate FROM ShortCut WHERE group_id = a.id ORDER BY modifiedDate DESC) " +
            "FROM ShortCutGroup AS a " +
            "INNER JOIN Program AS b ON a.program_id = b.id " +
            "INNER JOIN Member AS c ON b.member_id = c.id " +
            "WHERE c.id = :memberId " +
            "ORDER BY modifiedDate DESC", nativeQuery = true)
    List<Shortcutgroup> recentModifiedGroup(@Param("memberId") Long memberId);
}
