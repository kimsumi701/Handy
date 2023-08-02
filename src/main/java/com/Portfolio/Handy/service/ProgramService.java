package com.Portfolio.Handy.service;

import com.Portfolio.Handy.dto.ProgramDto;
import com.Portfolio.Handy.dto.ShortCutGroupDto;
import com.Portfolio.Handy.entity.Program;
import com.Portfolio.Handy.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgramService {
    @Autowired
    private final ProgramRepository programRepository;
    @Autowired
    private final MemberService memberService;

    public List<ProgramDto> programs(Long memberId) {
        return programRepository.findByMemberId(memberId)
                .stream()
                .map(program -> ProgramDto.createProgramDto(program))
                .collect(Collectors.toList());
    }

    //  최근에 수정된 프로그램(단축키, 그룹) 조회
    public List<ProgramDto> recentProgram(List<ShortCutGroupDto> shortCutGroupDtoList) {
        List<ProgramDto> programDtoList = new ArrayList<>(6);

        shortCutGroupDtoList.forEach(group -> {
            Program program = programRepository.findByGroupId(group.getId());
            ProgramDto programDto = ProgramDto.createProgramDto(program);
            programDto.setShortCutGroupDto(group);
            programDtoList.add(programDto);
        });

        return programDtoList;
    }
    //  프로그램 추가
    public ProgramDto createProgram(ProgramDto programDto) {
        Program create = Program.createProgram(programDto, memberService.currentMember());
        Program save = programRepository.save(create);
        return ProgramDto.createProgramDto(save);
    }

    //  프로그램 수정
    public ProgramDto updateProgram(ProgramDto programDto) {
        Program target = programRepository.findById(programDto.getId())
                .orElseThrow(() -> new IllegalStateException("수정할 프로그램이 없습니다."));

        target.patch(programDto);
        Program update = programRepository.save(target);
        return ProgramDto.createProgramDto(update);
    }

    //  프로그램 삭제
    public void deleteProgram(ProgramDto programDto) {
        Program target = programRepository.findById(programDto.getId())
                .orElseThrow(() -> new IllegalStateException("삭제할 프로그램이 없습니다."));

        programRepository.delete(target);
    }
}
