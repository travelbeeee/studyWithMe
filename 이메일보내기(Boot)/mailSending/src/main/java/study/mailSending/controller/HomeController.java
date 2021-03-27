package study.mailSending.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    JavaMailSender javaGmailSender;

    @Autowired
    JavaMailSender javaNmailSender;

    @PostMapping("GmailSending")
    public String sendingGMail(HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();

        String fromEmail = "sochun2528@gmail.com";
        String toEmail = httpServletRequest.getParameter("email");
        String title = "회원가입인증메일입니다.";
        Random r = new Random();
        int code = r.nextInt(4589362) + 49311;

        // 세션에 인증코드 추가
        httpSession.setAttribute("auth", code);

        String content = System.getProperty("line.separator")+ //한줄씩 줄간격을 두기위해 작성
                System.getProperty("line.separator")+
                "안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다"
                +System.getProperty("line.separator")+
                System.getProperty("line.separator")+
                " 인증번호는 " + code + " 입니다. "
                +System.getProperty("line.separator")+
                System.getProperty("line.separator")+
                "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용

        try {
            MimeMessage message = javaGmailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    true, "UTF-8");

            messageHelper.setFrom(fromEmail); // 보내는사람 생략하면 정상작동을 안함
            messageHelper.setTo(toEmail); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
            messageHelper.setText(content); // 메일 내용

            javaGmailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/";
    }

    @PostMapping("NmailSending")
    public String sendingNMail(HttpServletRequest httpServletRequest){
        String fromEmail = "sochun1518@naver.com";
        String toEmail = httpServletRequest.getParameter("email");
        String title = "회원가입인증메일입니다.";
        Random r = new Random();
        int code = r.nextInt(4589362) + 49311;

        String content = System.getProperty("line.separator")+ //한줄씩 줄간격을 두기위해 작성
                System.getProperty("line.separator")+
                "안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다"
                +System.getProperty("line.separator")+
                System.getProperty("line.separator")+
                " 인증번호는 " + code + " 입니다. "
                +System.getProperty("line.separator")+
                System.getProperty("line.separator")+
                "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용

        try {
            MimeMessage message = javaNmailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    true, "UTF-8");

            messageHelper.setFrom(fromEmail); // 보내는사람 생략하면 정상작동을 안함
            messageHelper.setTo(toEmail); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
            messageHelper.setText(content); // 메일 내용

            javaNmailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/";
    }
}
