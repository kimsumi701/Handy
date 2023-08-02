package com.Portfolio.Handy.entity;

import com.Portfolio.Handy.dto.ShortCutDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shortcut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId;

    @Column(name = "key1")
    private String key1;

    @Column(name = "key2")
    private String key2;

    @Column(name = "key3")
    private String key3;

    @Column(name = "key4")
    private String key4;

    @Column(name = "explain")
    private String explain;

    @Column(name = "createddate")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "modifieddate")
    @UpdateTimestamp
    private Timestamp modifiedDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Shortcutgroup shortcutgroup;

    public static Shortcut createShortCut(ShortCutDto dto, Shortcutgroup shortcutgroup) {
        if (dto.getId() != null)
            throw new IllegalArgumentException("단축키 생성을 실패 하였습니다. 단축키의 ID가 없어야 합니다.");

        if (dto.getGroupId() != shortcutgroup.getId())
            throw new IllegalArgumentException("단축키 생성에 실패 하였습니다. 그룹 ID가 잘못되었습니다.");

        return new Shortcut(
                dto.getId(), dto.getGroupId(), dto.getKey1(), dto.getKey2(), dto.getKey3(), dto.getKey4(),
                dto.getExplain(), null, null, shortcutgroup
        );
    }


    public void patch(ShortCutDto shortCutDto) {
        if (this.id != shortCutDto.getId()) {
            throw  new IllegalArgumentException("단축키 수정을 실패했습니다. 해당 단축키가 존재하지 않습니다.");
        }

        this.key1 = shortCutDto.getKey1();
        this.key2 = shortCutDto.getKey2();
        this.key3 = shortCutDto.getKey3();
        this.key4 = shortCutDto.getKey4();
        this.explain = shortCutDto.getExplain();
    }
}
