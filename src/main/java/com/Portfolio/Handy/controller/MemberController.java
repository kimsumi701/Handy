package com.Portfolio.Handy.controller;

import com.Portfolio.Handy.dto.MemberDto;
import com.Portfolio.Handy.dto.ProgramDto;
import com.Portfolio.Handy.dto.ShortCutGroupDto;
import com.Portfolio.Handy.entity.Member;
import com.Portfolio.Handy.service.MemberService;
import com.Portfolio.Handy.service.ProgramService;
import com.Portfolio.Handy.service.ShortCutGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    @Autowired
    private final MemberService memberService;

    @Autowired
    private final ShortCutGroupService shortCutGroupService;

    @Autowired
    private final ProgramService programService;

    //  마이페이지 이동
    @GetMapping("/mypage")
    public String myPage(Model model) {
//      최근 활동 내역 조회
        List<ShortCutGroupDto> shortCutGroupDtoList =
                shortCutGroupService.recentModifiedGroup(memberService.currentMember().getId());

        // 최근 활동 내역 6개
        List<ProgramDto> programDtoList = programService.recentProgram(shortCutGroupDtoList);
        model.addAttribute("programDtoList", programDtoList);

        // 나머지 공간
        List<String> blank = shortCutGroupService.blankBox(programDtoList);
        model.addAttribute("blank", blank);

        // 로그인 유저 정보
        model.addAttribute("currentMember", memberService.currentMember());

        return "member/myPage";
    }

    //  유저 정보 수정 페이지 이동
    @GetMapping("/mypage/member")
    public String memberPage(Model model) {
        Member member = memberService.currentMember();
        model.addAttribute("currentMember", member);
        return "member/memberUpdate";
    }

    //  유저 정보 수정
    @PutMapping("/mypage/member/update")
    public String memberUpdate(MemberDto memberDto, Model model) throws Exception {
        try {
            Member member = memberService.updateMember(memberDto);

        } catch (IllegalStateException e) {
            model.addAttribute("currentMember", memberService.currentMember());
            model.addAttribute("errorMessage", e.getMessage());

            return "member/memberUpdate";
        } catch (IllegalArgumentException e) {
            model.addAttribute("currentMember", memberService.currentMember());
            model.addAttribute("errorMessage", e.getMessage());

            return "member/memberUpdate";
        }

        return "redirect:/mypage";
    }
}
