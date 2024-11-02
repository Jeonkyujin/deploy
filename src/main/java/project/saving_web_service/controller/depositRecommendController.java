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
import project.saving_web_service.DepositImplements.depositHighestRate;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.DetailedDepositRecommendService;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.depositRecommendService;
import project.saving_web_service.service.statusDepositService;

@Controller
@RequiredArgsConstructor
public class depositRecommendController {

	private final MemberService memberService;
	private final depositRecommendService dRS;
	private final DetailedDepositRecommendService DDRS;
	private final statusDepositService statusDepositService;

	@GetMapping("/deposit")
	public String deposit(Model model, HttpSession session) {
		String login_id = (String)session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		List<Deposit> deposit = dRS.recommend(member);
		List<Deposit> status = statusDepositService.findStatus(member.getStatus());
		System.out.println(status.size());
		model.addAttribute("deposit", deposit);
		model.addAttribute("member", member);
		model.addAttribute("depositStatus", status);
		model.addAttribute("statusMessage", member.getStatus()+"를 위한 추천상품");
		model.addAttribute("commonMessage", "조건 맞춤 추천상품");

		if (deposit.isEmpty()) {
			return "perfectRecommend/deposit";
		} else {
			return "Recommend/reputationByDeposit";
		}
	}

	@PostMapping("/deposit")
	public String newRate(@RequestParam(value = "category", required = false) String category,
		@RequestParam(value = "CategoryBank", required = false) String categoryBank,
		@RequestParam(value = "desiredRate", required = false) Double desiredRate,
		@RequestParam(value = "preferredBank", required = false) String bankName, Model model, HttpSession session) {

		String login_id = (String)session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		List<Deposit> FirstFilterDeposit = new ArrayList<>();

		if (desiredRate != null && bankName != null) {
			List<Deposit> deposit = dRS.findAllDeposit();
			for (Deposit deposit1 : deposit) {
				depositHighestRate dhR = new depositHighestRate();
				if (deposit1.get금융회사명().equals(bankName) && dhR.getMaxRate(deposit1.get금리()) >= desiredRate) {
					FirstFilterDeposit.add(deposit1);
				}
			}
			List<Deposit> SecondFilterDeposit = dRS.recommend(member, FirstFilterDeposit);
			List<Deposit> status = statusDepositService.findStatus(member.getStatus());
			model.addAttribute("deposit", SecondFilterDeposit);
			model.addAttribute("member", member);
			model.addAttribute("depositStatus", status);
			model.addAttribute("statusMessage", member.getStatus()+"를 위한 추천상품");
			model.addAttribute("commonMessage", "조건 맞춤 추천상품");
			return "Recommend/reputationByDeposit";
		} else if (category != null && categoryBank != null) {
			List<Deposit> category1 = DDRS.category(category, member);

			for (Deposit deposit : category1) {
				if (deposit.get금융회사명().equals(categoryBank)) {
					FirstFilterDeposit.add(deposit);
				}
			}
			List<Deposit> status = statusDepositService.findStatus(member.getStatus());
			model.addAttribute("deposit", FirstFilterDeposit);
            model.addAttribute("member",member);
			model.addAttribute("depositStatus", status);
			model.addAttribute("statusMessage", member.getStatus()+"를 위한 추천상품");
			model.addAttribute("commonMessage", "조건 맞춤 추천상품");
			return "perfectRecommend/deposit";
		}
		else {
			return null;
		}
	}
}
