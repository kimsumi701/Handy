package com.Portfolio.Handy.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private String ePw = createKey();

    //  인증키 초기화
    public String initEpw() {
        ePw = createKey();
        return ePw;
    }

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);//보내는 대상
        message.setSubject("Handy 메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:20px; text-align: center;'>";
        msgg+= "<h1> Handy Project </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<div style='width: 70%; margin: 0 auto; border:1px solid black;'>";
        msgg+= "<h3 style='color:blue;'>인증 코드</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("projecthandy2023@gmail.com","Handy"));//보내는 사람

        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) { // 인증코드 4자리
            key.append((rnd.nextInt(10)));
        }

        return key.toString();
    }
    @Async
    public void sendSimpleMessage(String to)throws Exception {
        MimeMessage message = createMessage(to);

        try{
            emailSender.send(message);
        } catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException("코드 송신에 실패했습니다. 이메일 확인 후 다시 시도해주세요.");
        }

    }


    //  인증 코드 확인
    public boolean matchCode(String sendCode, String inputCode) {
        boolean result = true;

        if (!sendCode.equals(inputCode)) result = false;

        return result;
    }

    //  인증 타입별 이동 페이지
    public String matchType(String type, boolean auth) {
        String result;

        if (type.equals("password")) {
            result = auth ? "findAccount/newPassword" : "findAccount/findPwd";
        } else {
            result = auth ? "findAccount/returnId" : "findAccount/findId";
        }

        return result;
    }
}
