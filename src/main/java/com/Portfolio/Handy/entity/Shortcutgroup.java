package com.Portfolio.Handy.entity;

import com.Portfolio.Handy.dto.ShortCutGroupDto;
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
public class Shortcutgroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "program_id", insertable = false, updatable = false)
    private Long programId;

    @Column(name = "name")
    private String name;

    @Column(name = "isfavorite")
    private Boolean isFavorite;

    @Column(name = "createddate")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "modifieddate")
    @UpdateTimestamp
    private Timestamp modifiedDate;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToMany(mappedBy = "shortcutgroup")
    private List<Shortcut> shortcuts = new ArrayList<>();


    public static Shortcutgroup createGroup(ShortCutGroupDto dto, Program program) {
        if (dto.getId() != null)
            throw new IllegalArgumentException("그룹 생성에 실패 하였습니다. 그룹의 ID가 없어야 합니다.");

        if (dto.getProgramId() != program.getId())
            throw new IllegalArgumentException("그룹 생성에 실패 하였습니다. 프로그램의 ID가 잘못되었습니다.");

        return new Shortcutgroup(
                dto.getId(), dto.getProgramId(), dto.getName(), dto.getIsFavorite(), null, null, program, null
        );
    }

    public void patch(ShortCutGroupDto shortCutGroupDto) {
        if (this.id != shortCutGroupDto.getId()) {
            throw  new IllegalArgumentException("그룹 수정 실패했습니다. 대상이 되는 그룹이 없습니다.");
        }

        if (shortCutGroupDto.getName() != null) {
            this.name = shortCutGroupDto.getName();
        }

        if (shortCutGroupDto.getIsFavorite() != null) {
            this.isFavorite = shortCutGroupDto.getIsFavorite();
        }
    }
}
