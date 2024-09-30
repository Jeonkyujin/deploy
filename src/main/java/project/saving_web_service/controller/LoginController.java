package project.saving_web_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    @GetMapping("/login")
    public String login(HttpSession httpSession, Model model){
        String login_id = (String) httpSession.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        model.addAttribute("member", member);
        return "login/login.html";
    }

}
