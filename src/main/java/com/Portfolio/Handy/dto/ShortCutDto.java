package com.Portfolio.Handy.dto;

import com.Portfolio.Handy.entity.Shortcut;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShortCutDto {
    private Long id;

    @JsonProperty("groupId")
    private Long groupId;

    private String key1;

    private String key2;

    private String key3;

    private String key4;

    private String explain;

    public static ShortCutDto createShortCutDto(Shortcut shortCut) {
        return new ShortCutDto(
                shortCut.getId(),
                shortCut.getShortcutgroup().getId(),
                shortCut.getKey1(),
                shortCut.getKey2(),
                shortCut.getKey3(),
                shortCut.getKey4(),
                shortCut.getExplain()
        );
    }
}
