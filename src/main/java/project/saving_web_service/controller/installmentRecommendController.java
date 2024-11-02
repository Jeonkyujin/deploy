package project.saving_web_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.saving_web_service.InstallImplements.installHighestRate;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.installmentRecommendService;
import project.saving_web_service.service.statusInstallService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class installmentRecommendController {

    private final MemberService memberService;
    private final installmentRecommendService iRs;
    private final statusInstallService statusInstallService;

    @GetMapping("/installment")
    public String installment(HttpSession session, Model model) {

        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);

        List<Install> install = iRs.recommend(member);
        List<Install> status = statusInstallService.findStatus(member.getStatus());
        System.out.println(status.size());
        model.addAttribute("status", status);
        model.addAttribute("install", install);
        model.addAttribute("member", member);
        model.addAttribute("statusMessage", member.getStatus() + "추천상품");
        model.addAttribute("commonMessage", "조건 맞춤 추천상품");

        return "Recommend/reputationByInstall";


    }
    @PostMapping("/installment")
    public String newRate(@RequestParam(value = "desiredRate", required = false) Double desiredRate,@RequestParam(value = "preferredBank", required = false) String bankName ,Model model, HttpSession session){

        String login_id = (String) session.getAttribute("login_id");
        Member member = memberService.findMember(login_id);
        List<Install> FirstFilterInstall = new ArrayList<>();

        List<Install> install = iRs.findAllInstall();
        for (Install install1 : install) {
            installHighestRate ihR = new installHighestRate();
            if(install1.get금융회사명().equals(bankName) && ihR.getMaxRate(install1.get금리()) >= desiredRate){
                FirstFilterInstall.add(install1);
            }
        }
        List<Install> SecondFilterInstall = iRs.recommend(member,FirstFilterInstall);
        List<Install> status = statusInstallService.findStatus(member.getStatus());


        model.addAttribute("install", SecondFilterInstall);
        model.addAttribute("member", member);
        model.addAttribute("status", status);
        model.addAttribute("statusMessage", member.getStatus() + "추천상품");
        model.addAttribute("commonMessage", "조건 맞춤 추천상품");

        return "Recommend/reputationByInstall";


    }

}
