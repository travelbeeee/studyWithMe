package study.signUp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.signUp.domain.Member;
import study.signUp.form.LoginForm;
import study.signUp.form.ModifyForm;
import study.signUp.form.SignUpForm;
import study.signUp.service.MemberService;
import study.signUp.util.Sha256Util;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final Sha256Util sha256Util;
    private final JavaMailSender javaGmailSender;

    @GetMapping("/member/signUp")
    public String signUpForm(){

        return "/member/signUpForm";
    }

    @PostMapping("/member/signUp")
    public String signUp(SignUpForm signUpForm) throws NoSuchAlgorithmException {
        Member member = new Member();
        String salt = sha256Util.makeSalt();
        member.setUsername(signUpForm.getUsername());
        member.setUserpwd(sha256Util.sha256(signUpForm.getUserpwd(), salt));
        member.setSalt(salt);
        member.setEmail(signUpForm.getEmail());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(){

        return "/member/loginForm";
    }

    @PostMapping("/member/login")
    public String login(LoginForm loginForm, HttpSession session) throws NoSuchAlgorithmException {
        Member member = memberService.login(loginForm.getUsername(), loginForm.getUserpwd());

        if (member == null)
            return "redirect:/";

        session.setAttribute("id", member.getId());

        if (member.getAuth().equals("UNAUTHORIZATION"))
            return "member/authMailSending";

        return "/member/myPage";
    }

    @GetMapping("/member/modify")
    public String modifyForm(HttpSession session, Model model){
        if(session.getAttribute("id") == null)
            return "redirect:/";

        Member member = memberService.getMemberById((Long) session.getAttribute("id"));
        model.addAttribute("username", member.getUsername());
        model.addAttribute("email", member.getEmail());

        return "/member/modifyForm";
    }

    @PostMapping("/member/modify")
    public String modify(ModifyForm modifyForm, HttpSession session) throws NoSuchAlgorithmException{
        if(session.getAttribute("id") == null)
            return "redirect:/";

        Member member = new Member();
        member.setId((Long) session.getAttribute("id"));
        member.setUsername(modifyForm.getUsername());
        member.setEmail(modifyForm.getEmail());
        member.setUserpwd(modifyForm.getUserpwd());

        memberService.modify(member);
        return "/member/myPage";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        if(session.getAttribute("id") == null)
            return "redirect:/";

        // 세션날려주고 redirect 시켜주자!
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/member/delete")
    public String delete(HttpSession session){
        if(session.getAttribute("id") == null)
            return "redirect:/";

        // 세션날려주고 회원 탈퇴하고 redirect 시켜주자
        memberService.delete((Long)session.getAttribute("id"));
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/member/authMailSending")
    public String sendingGMail(HttpSession session){
        if(session.getAttribute("id") == null)
            return "redirect:/";

        String fromEmail = "sochun2528@gmail.com";
        Member member = memberService.getMemberById((Long) session.getAttribute("id"));
        String toEmail = member.getEmail();
        String title = "회원가입인증메일입니다.";
        Random r = new Random();
        int code = r.nextInt(4589362) + 49311;

        session.setAttribute("authCode", Integer.toString(code));

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
        return "/member/mailAuth";
    }

    @PostMapping("/member/authCode")
    public String AuthCode(HttpServletRequest request, HttpSession session){
        if(session.getAttribute("id") == null || session.getAttribute("authCode") == null)
            return "redirect:/";

        String sessionCode = (String)session.getAttribute("authCode");
        String code = request.getParameter("authCode");
        if(code.equals(sessionCode)){
            memberService.auth((Long) session.getAttribute("id"));
            session.removeAttribute("authCode");
            return "redirect:/member/login";
        }else{
            session.invalidate();;
            return "redirect:/";
        }
    }
}
