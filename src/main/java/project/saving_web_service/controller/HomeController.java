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
import project.saving_web_service.service.RedisService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;
    private final MemberService memberService;
    private final RedisService redisService;

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
        model.addAttribute("login_id", login_id);
        return "myPage/myPage";
    }

    @GetMapping ("/myPage/correction")
    public String editPage(Model model, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);
        model.addAttribute("member", member);
        model.addAttribute("login_id", login_id);
        return "myPage/correction";

    }
    @PostMapping("/myPage/correction")
    public String correction(@ModelAttribute Member updatedMember, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        member.setPassword(updatedMember.getPassword());
        member.setStatus(updatedMember.getStatus());
        member.setPreferredCondition(updatedMember.getPreferredCondition());
        member.setAmount(updatedMember.getAmount());
        member.setPeriod(updatedMember.getPeriod());
        member.setImportant(updatedMember.getImportant());
        member.setAge(updatedMember.getAge());
        member.setSex(updatedMember.getSex());


        memberService.join(member);

        session.setAttribute("member", member);

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션 무효화: 세션에 저장된 모든 정보를 삭제하여 로그아웃 처리

        session.invalidate();
        // 로그아웃 후 홈페이지("/")로 리다이렉트
        return "redirect:/";
    }

}