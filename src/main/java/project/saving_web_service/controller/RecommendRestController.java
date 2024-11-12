package project.saving_web_service.controller;



import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.RecommendRestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendRestController {

	private final MemberService memberService;
	private final RecommendRestService recommendRestService;
	@GetMapping("/recommend")
	public ResponseEntity<Map<String,Object>> recommend(HttpSession session){
		String login_id = (String)session.getAttribute("login_id");
		Member member = memberService.findMember(login_id);

		Map<String, Object> response = recommendRestService.recommend(member);




		return ResponseEntity.ok(response);
	}
}
