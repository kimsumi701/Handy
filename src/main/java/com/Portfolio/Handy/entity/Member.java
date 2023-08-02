package com.Portfolio.Handy.entity;

import com.Portfolio.Handy.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "userId")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "createddate")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "modifieddate")
    @UpdateTimestamp
    private Timestamp modifiedDate;

    private String role;
    @OneToMany(mappedBy = "member")

    private List<Program> programs = new ArrayList<>();

    public static Member createMember(MemberDto memberDto) {

        return new Member(
                memberDto.getId(), memberDto.getUserId(), memberDto.getPassword(),
                memberDto.getNickName(), memberDto.getEmail(), null, null,
                memberDto.getRole(), null

        );
    }

    public void patch(MemberDto memberDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (this.id != memberDto.getId()) {
            throw new IllegalArgumentException("잘못된 접근입니다. 새로고침 후 다시 시도해주세요.");
        }

        if (!encoder.matches(memberDto.getPassword(), this.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
        }

        if (memberDto.getNickName() != null) {
            this.nickName = memberDto.getNickName();
        }

        if (memberDto.getEmail() != null) {
            this.email = memberDto.getEmail();
        }
    }
}
