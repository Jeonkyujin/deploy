package project.saving_web_service.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.DetailedDepositRecommendService;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.depositRecommendService;

@Controller
@RequiredArgsConstructor
public class DetailedDepositRecommendController {
	private final MemberService memberService;
	private final DetailedDepositRecommendService DDRS;
	@GetMapping("/deposit/detailed")
	public String detailed(@RequestParam ("category") String category, HttpSession session, Model model){
		String login_id = (String)session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		List<Deposit> categoryDeposit = DDRS.category(category);
        model.addAttribute("deposit", categoryDeposit);
		return "Recommend/deposit";
	}

}
