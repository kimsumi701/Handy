package com.Portfolio.Handy.dto;

import com.Portfolio.Handy.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("passWord")
    private String password;

    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("email")
    private String email;

    private Timestamp createdDate;

    private String role;

    public MemberDto(String userId, String pwd, String role) {
        this.userId = userId;
        this.password = pwd;
        this.role = role;
    }

    public Member toEntity() {
        return new Member(id, userId, password, nickName, email, null, null, "ROLE_USER", null);
    }

    public static MemberDto createMemberDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUserId(),
                member.getPassword(),
                member.getNickName(),
                member.getEmail(),
                member.getCreatedDate(),
                member.getRole()
        );
    }
}
