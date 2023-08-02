package com.Portfolio.Handy.api;

import com.Portfolio.Handy.service.EmailService;
import com.Portfolio.Handy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailApiController {
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final MemberService memberService;

    //  이메일 인증 코드 보내기
    @ResponseBody
    @RequestMapping(value = "/api/join/emailAuth/{email}/{cnt}", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String sendCode(@RequestParam Map<String, Object> param) throws Exception {
        String email = (String) param.get("email");

        try {
            String code = emailService.initEpw();
            emailService.sendSimpleMessage(email);
            return code;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    //  이메일 중복 확인
    @ResponseBody
    @RequestMapping(value = "/api/join/emailEnable", method = RequestMethod.GET)
    public boolean emailEnableCheck(@RequestParam Map<String, Object> param) throws Exception {
        String email = (String) param.get("email");
        boolean result = true;
        try {
            if (!memberService.emailDuplicateCheck(email))
                throw new IllegalArgumentException("사용할 수 없는 이메일입니다.");

            result = true;
            return result;
        } catch (IllegalArgumentException e) {
            result = false;
            return result;
        }
    }
}
