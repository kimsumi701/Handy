package com.Portfolio.Handy.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("login fail handler");

        String errorMessage;

        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException){
            errorMessage="아이디 또는 비밀번호를 확인해주세요.";
        } else if (e instanceof UsernameNotFoundException){
            errorMessage="존재하지 않는 아이디 입니다.";
        } else{
            errorMessage="알 수 없는 이유로 로그인에 실패했습니다.";
        }

        errorMessage= URLEncoder.encode(errorMessage,"UTF-8");//한글 인코딩 깨지는 문제 방지
        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);

    }
}
