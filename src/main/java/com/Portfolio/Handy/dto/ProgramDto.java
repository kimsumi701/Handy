package com.Portfolio.Handy.dto;

import com.Portfolio.Handy.entity.Program;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramDto {
    private Long id;

    @JsonProperty("mermber_id")
    private Long memberId;

    private String name;

    private ShortCutGroupDto shortCutGroupDto;

    public static ProgramDto createProgramDto(Program program) {
        return new ProgramDto(
                program.getId(),
                program.getMember().getId(),
                program.getName(),
                null
        );
    }
}
