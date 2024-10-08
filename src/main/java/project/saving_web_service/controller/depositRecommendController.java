package project.saving_web_service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.depositRecommendService;

@Controller
@RequiredArgsConstructor
public class depositRecommendController {

	private final MemberService memberService;
	private final depositRecommendService dRS;
	@GetMapping("/deposit")
	public String deposit(Model model, HttpSession session){
		String login_id = (String) session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		List<Deposit> deposit = dRS.recommend(member);
		if (deposit.size() < 3){
			return "Recommend/detailedDeposit";
		}
		model.addAttribute("deposit", deposit);
		return "Recommend/deposit";

	}

	@PostMapping("/deposit")
	public String newRate(@RequestParam("desiredRate") double desiredRate, Model model, HttpSession session){

		String login_id = (String) session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		List<Deposit> FirstFilterInstall = dRS.recommend(member);
		List<Deposit> ReDeposit = new ArrayList<>();

		for (Deposit deposit : FirstFilterInstall) {
			String [] a;
			if(deposit.get금리().contains("~")){
				a = deposit.get금리().split("~");
				for (String s: a) {
					if (s == null || s.isEmpty()){
						continue;
					}
					double b = Double.parseDouble(s);
					if(b >= desiredRate){
						ReDeposit.add(deposit);
						break;
					}
				}

			}else {
				a = new String[]{deposit.get금리()};
				double b = Double.parseDouble(a[0]);
				if(b >= desiredRate){
					ReDeposit.add(deposit);
					break;
				}

			}
		}

		model.addAttribute("deposit", ReDeposit);
		return "Recommend/deposit";
	}

}
