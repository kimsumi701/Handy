package com.Portfolio.Handy.config;

import com.Portfolio.Handy.exception.LoginFailHandler;
import com.Portfolio.Handy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MemberService memberService;

    //Http Security를 설정한다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()//REST API에서 csrf 보안이 필요없기 때문에 비활성화,
                .authorizeRequests()// 요청에 대한 사용 권한을 체크
                .requestMatchers("/main/**").authenticated()
                .requestMatchers("/mypage/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/auth")
                .defaultSuccessUrl("/main")
                .usernameParameter("userId")
                .passwordParameter("password")
                .failureHandler(loginFailHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .build();
    }
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }
}
