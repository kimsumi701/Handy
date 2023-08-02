package com.Portfolio.Handy.controller;

import com.Portfolio.Handy.dto.ProgramDto;
import com.Portfolio.Handy.service.MemberService;
import com.Portfolio.Handy.service.ProgramService;
import com.Portfolio.Handy.util.ScriptUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final ProgramService programService;

    //  프로그램 편집 이동
    @GetMapping("/mypage/program")
    public String programEditPage(Model model) {
        List<ProgramDto> programDtoList = programService.programs(memberService.currentMember().getId());
        // 프로그램 리스트
        model.addAttribute("programList", programDtoList);

        // 로그인 유저 정보
        model.addAttribute("currentMember", memberService.currentMember());

        return "member/program";
    }

    //  프로그램 추가
    @RequestMapping(value = "/mypage/program/create", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/html; charset=UTF-8")
    public String programCreate(HttpServletResponse response, ProgramDto programDto) throws Exception {
        try {
            ProgramDto create = programService.createProgram(programDto);
        } catch (IllegalArgumentException e) {
            ScriptUtils.alertAndMovePage(response, e.getMessage(), "/mypage/program");
        }

        return "member/program";
    }

    //  프로그램 수정
    @RequestMapping(value = "/mypage/program/update", method = {RequestMethod.PUT, RequestMethod.GET}, produces = "text/html; charset=UTF-8")
    public String programUpdate(HttpServletResponse response, ProgramDto programDto) throws Exception {
        try {
            ProgramDto update = programService.updateProgram(programDto);
        } catch (IllegalArgumentException e) {
            ScriptUtils.alertAndMovePage(response, e.getMessage(), "/mypage/program");
        }

        return "member/program";
    }

    //  프로그램 삭제
    @DeleteMapping("/mypage/program/delete")
    public String programDelete(ProgramDto programDto, Model model) throws Exception {
        try {
            programService.deleteProgram(programDto);
            return "redirect:/mypage/program";
        } catch (IllegalArgumentException e) {
            model.addAttribute("ErrorMessage", "잘못된 접근입니다. 새로고침 후 다시 시도해주세요.");
            return "redirect:/mypage/program";
        }
    }
}
