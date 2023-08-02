package com.Portfolio.Handy.repository;

import com.Portfolio.Handy.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmail(String email);
    @Modifying
    @Query(value = "UPDATE Member " +
            "SET nickName = :nickName, " +
            "email = :email " +
            "WHERE id = :id AND passWord = :passWord", nativeQuery = true)
    void updateMember(@Param("id") Long id, @Param("nickName") String nickName,
                      @Param("email") String email, @Param("passWord") String password);


}