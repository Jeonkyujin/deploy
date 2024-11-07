package project.saving_web_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;

@Controller
@RequiredArgsConstructor
public class FavoriteCommodityController {

	private final MemberService memberService;

	@GetMapping("/favoriteCommodity")
	public String show(Model model, HttpSession session){
		String login_id = (String) session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		model.addAttribute("member", member);
		model.addAttribute("login_id",login_id);

		return "Favorite/favoriteCommodity";
	}
}
