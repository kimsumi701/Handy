package com.Portfolio.Handy.controller;

import com.Portfolio.Handy.dto.MemberDto;
import com.Portfolio.Handy.entity.Member;
import com.Portfolio.Handy.exception.HospitalReviewAppException;
import com.Portfolio.Handy.service.EmailService;
import com.Portfolio.Handy.service.MemberService;
import com.Portfolio.Handy.util.ScriptUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    @Autowired
    private final MemberService memberService;

    private final BCryptPasswordEncoder encoder;

    private final EmailService emailService;

    //  회원가입 페이지 이동
    @GetMapping("/join")
    public String NewMember() {
        return "login/join";
    }

    //  로그인 실패
    @GetMapping("/login")
    public String loginMember(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login/login";
    }

    //  회원가입
    @RequestMapping(value = "/join/create", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/html; charset=UTF-8")
    public String JoinMember(HttpServletResponse response, MemberDto memberDto) throws Exception{
        String pwd = memberDto.getPassword();
        memberDto.setPassword(encoder.encode(pwd));
        memberDto.setRole("ROLE_USER");

        try {
            MemberDto join =  memberService.join(memberDto);
        } catch (HospitalReviewAppException e) {
            ScriptUtils.alertAndMovePage(response, "이미 존재하는 아이디입니다.", "/join");
        }

        return "login/login";
    }

    //  아이디 찾기 페이지 이동
    @GetMapping("/login/findId")
    public String findIdPage() {
        return "findAccount/findId";
    }


    //  아이디 찾기 코드 보내기
    @GetMapping("/login/findId/checkEmail")
    public String checkEmail(String email, Model model) throws Exception {
        try {
            //  이메일 발송
            sendEmail("userId", memberService.findByEmail(email).getEmail(), model);
            return "findAccount/matchCode";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "findAccount/findId";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "findAccount/findId";
        }
    }

    //  이메일 인증
    @RequestMapping(value = "/login/matchCode/{type}/{sendCode}", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String matchCode(HttpServletResponse response, @PathVariable String type,
                            @PathVariable String sendCode, String inputCode, String memberValue, Model model) throws Exception {

        try {
            if (sendCode == null) throw new IllegalArgumentException("잘못된 접근입니다.");

            //  코드 인증
            if (!emailService.matchCode(sendCode,inputCode)) {
                model.addAttribute("errorMessage", "인증에 실패했습니다. 다시 시도해주세요.");
                return emailService.matchType(type, false);
            }

            // 타입별 다음 페이지에 넘길 model
            String value = type.equals("password")
                    ? memberService.findByEmail(memberValue).getId().toString()
                    : memberService.findByEmail(memberValue).getUserId();

            model.addAttribute("memberValue", value);

        } catch (IllegalArgumentException e) {
            ScriptUtils.alertAndMovePage(response, e.getMessage(), "/login");
        }
        return emailService.matchType(type, true);
    }

    //  비밀번호 찾기 페이지 이동
    @GetMapping("/login/findPwd")
    public String findPwdPage() {
        return "findAccount/findPwd";
    }

    //  비밀번호 찾기 코드 보내기
    @GetMapping("/login/findPwd/sendCode")
    public String sendCodePwd(String userId, Model model) throws Exception {
        try {
            // 인증 코드 보내기
            sendEmail("password", memberService.findByUserId(userId).getEmail(), model);
            return "findAccount/matchCode";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "findAccount/findPwd";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "findAccount/findPwd";
        }
    }

    //  새로운 비밀번호 설정
    @RequestMapping(value = "/login/findPwd/newPassword", method = { RequestMethod.PUT, RequestMethod.GET })
    public String newPassword(HttpServletResponse response, Long id, String password) throws Exception {
        try {
            if (password == null) throw new IllegalArgumentException("잘못된 접근입니다.");
            Member update = memberService.updatePassword(id, encoder.encode(password));
        } catch (IllegalArgumentException e) {
            ScriptUtils.alertAndMovePage(response, e.getMessage(), "/login");
        } catch (IllegalStateException e) {
            ScriptUtils.alertAndMovePage(response, e.getMessage(), "/login");
        }

        return "login/login";
    }

    // 이메일 발송 및 model 정리
    public void sendEmail(String type, String email, Model model) throws Exception{
        String code = emailService.initEpw();
        emailService.sendSimpleMessage(email);

        model.addAttribute("type", type);
        model.addAttribute("memberValue", email);
        model.addAttribute("sendCode", code);
    }
}
