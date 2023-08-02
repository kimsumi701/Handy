package com.Portfolio.Handy.entity;

import com.Portfolio.Handy.dto.ProgramDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    @Column(name = "name")
    private String name;

    @Column(name = "createddate")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "modifieddate")
    @UpdateTimestamp
    private Timestamp modifiedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "program")
    private List<Shortcutgroup> shortcutgroups = new ArrayList<>();

    public static Program createProgram(ProgramDto programDto, Member member) {
        if (programDto.getId() != null) {
            throw new IllegalArgumentException("프로그램 추가에 실패했습니다. 새로고침 후 다시 시도해주세요.");
        }

        return new Program(
                programDto.getId(), programDto.getMemberId(), programDto.getName(), null, null, member, null
        );
    }
    public void patch(ProgramDto programDto) {
        if (this.id != programDto.getId()) {
            throw new IllegalArgumentException("프로그램 수정을 실패했습니다. 새로고침 후 다시 시도해주세요.");
        }

        if (programDto.getName() != null) {
            this.name = programDto.getName();
        }
    }
}
