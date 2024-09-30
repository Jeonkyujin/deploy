package project.saving_web_service.controller;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.LoginService;
import project.saving_web_service.service.MemberService;



@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;
    private final MemberService memberService;

    @RequestMapping("/")
    public String home() {
        log.info("home controller");

        return "home";
    }

    @PostMapping("/")
    public String login(@RequestParam("login_id") String login_id,
                        @RequestParam("password") String password, HttpSession session
    ) {
        String checking_id = loginService.loginCheck(login_id, password);
        if (checking_id != null) {
            session.setAttribute("login_id", login_id);
            session.setAttribute("password", password);
            session.setMaxInactiveInterval(60 * 30);

            return "redirect:/login";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/myPage")
    public String myPage(Model model, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);
        model.addAttribute("member", member);
        return "myPage/myPage";
    }

    @GetMapping ("/myPage/correction")
    public String editpage(Model model, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);
        model.addAttribute("member", member);
        return "myPage/correction";

    }
    @PostMapping("/myPage/correction")
    public String correction(@ModelAttribute Member updatedMember, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        member.setPassword(updatedMember.getPassword());
        member.setStatus(updatedMember.getStatus());
        member.setInterest(updatedMember.getInterest());
        member.setCommodity_existence(updatedMember.getCommodity_existence());
        member.setCommodity(updatedMember.getCommodity());
        member.setPeriod(updatedMember.getPeriod());
        member.setImportant(updatedMember.getImportant());


        memberService.join(member);

        session.setAttribute("member", member);

        return "redirect:/";
    }

}