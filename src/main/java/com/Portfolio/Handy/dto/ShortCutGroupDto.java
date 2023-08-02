package com.Portfolio.Handy.dto;

import com.Portfolio.Handy.entity.Shortcutgroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShortCutGroupDto {
    private Long id;

    @JsonProperty("programId")
    private Long programId;

    private String name;

    @JsonProperty("isFavorite")
    private Boolean isFavorite;

    private Timestamp modifiedDate;

    @JsonProperty("shortCut")
    private List<ShortCutDto> shortCutDtoList;

    public static ShortCutGroupDto createShortCutGroupDto(Shortcutgroup shortCutGroup) {
        return new ShortCutGroupDto(
                shortCutGroup.getId(),
                shortCutGroup.getProgram().getId(),
                shortCutGroup.getName(),
                shortCutGroup.getIsFavorite(),
                shortCutGroup.getModifiedDate(),
                null
        );
    }
}
