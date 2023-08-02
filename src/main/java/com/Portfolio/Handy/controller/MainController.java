package com.Portfolio.Handy.controller;

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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private final MemberService memberService;

    @Autowired
    private final ProgramService programService;

    @Autowired
    private final ShortCutGroupService shortCutGroupService;


    //  메뉴 조회
    public void Menu(Model model) {
        Member member = memberService.currentMember();
        List<ProgramDto> programDtoList = programService.programs(member.getId());
        model.addAttribute("member", member);
        model.addAttribute("programDtoList", programDtoList);
    }

    //  즐겨찾기 순으로 그룹 조회
    @GetMapping("/main")
    public String mainPage(Model model) {
        Menu(model);
        Member member = memberService.currentMember();
        List<ShortCutGroupDto> shortCutGroupDtoList = shortCutGroupService.groupsOrderFavorite(member.getId());
        model.addAttribute("shortCutGroupDtoList", shortCutGroupDtoList);
        return "main/main";
    }

    //  프로그램 메뉴 클릭(단축키 조회)
    @GetMapping("/main/shortcut/{id}")
    public String shortCutPage(@PathVariable Long id, Model model) {
        Menu(model);
        List<ShortCutGroupDto> shortCutGroupDtoList = shortCutGroupService.shortCutGroups(id);

        model.addAttribute("Id", id);
        model.addAttribute("shortCutGroupDtoList", shortCutGroupDtoList);
        return "main/shortCut";
    }

    //  검색
    @GetMapping("/main/serach")
    public String mainSerach(String serachText, Model model) {
        Menu(model);
        Member member = memberService.currentMember();

        List<ShortCutGroupDto> shortCutGroupDtoList = new ArrayList<>();

        if (serachText == "") {
            shortCutGroupDtoList = shortCutGroupService.groupsOrderFavorite(member.getId());
        } else {
            shortCutGroupDtoList = shortCutGroupService.serachGroup(member.getId(), serachText);
            log.info(shortCutGroupDtoList.toString());
        }

        model.addAttribute("shortCutGroupDtoList", shortCutGroupDtoList);
        return "main/main";
    }
}
