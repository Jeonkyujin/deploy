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

        if (member.getImportant().equals("높은금리")) {
            List<Install> example = iRs.interest_rate(member.getPeriod());
            if (!example.isEmpty()) {
                model.addAttribute("high_Value", example);
            }
            return "highestRate";

        }
        else if(member.getImportant().equals("해당 금융사에 대한 평판")){
            List<Install> example = iRs.reputation(member.getPeriod());
            if (!example.isEmpty()) {
                model.addAttribute("high_Value", example);
            }
        }
        else if(member.getImportant().equals("편의성")){
            return "convenience/convenience";
        }
        return "savings";
    }
    @PostMapping("/installment/bank")
    public String bank(@RequestParam("nearest_bank") String nearest_bank, Model model, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        List<Install> nearest = iRs.bank(nearest_bank, member.getPeriod());
        if(!nearest.isEmpty()){
            model.addAttribute("high_Value", nearest);
        }
        return "savings";
    }

    @PostMapping("/installment")
    public String newRate(@RequestParam("desiredRate") double desiredRate, Model model, HttpSession session){
        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        List<Install> newRate = iRs.Rate(member.getPeriod(), desiredRate);
        if(!newRate.isEmpty()){
            model.addAttribute("high_Value", newRate);
        }
        return "highestRate";
    }


}
