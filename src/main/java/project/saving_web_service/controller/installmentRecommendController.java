package project.saving_web_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.installmentRecommendService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class installmentRecommendController {

    private final MemberService memberService;
    private final installmentRecommendService iRs;

    @GetMapping("/installment")
    public String installment(HttpSession session, Model model) {

        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        List<Install> install = iRs.recommend(member);
        model.addAttribute("install", install);
        return "Recommend/installment";

    }
    @PostMapping("/installment")
    public String newRate(@RequestParam("desiredRate") double desiredRate, Model model, HttpSession session){

        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);
        List<Install> FirstFilterInstall = iRs.recommend(member);
        List<Install> Reinstall = new ArrayList<>();

        for (Install installs : FirstFilterInstall) {
            String [] a;
            if(installs.get금리().contains("~")){
                a = installs.get금리().split("~");
                for (String s: a) {
                    if (s == null || s.isEmpty()){
                        continue;
                    }
                    double b = Double.parseDouble(s);
                    if(b >= desiredRate){
                        Reinstall.add(installs);
                        break;
                    }
                }

            }else {
                a = new String[]{installs.get금리()};
                double b = Double.parseDouble(a[0]);
                if(b >= desiredRate){
                    Reinstall.add(installs);
                    break;
                }

            }
        }
        model.addAttribute("install", Reinstall);
        return "Recommend/installment";
    }

}
