package project.saving_web_service.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.RedisService;

@RestController
@RequiredArgsConstructor
public class RedisRestController {
	private final MemberService memberService;
	private final RedisService redisService;
	@GetMapping("/polling")
	public String polling(HttpSession httpSession){
		String login_id = (String) httpSession.getAttribute("login_id");
		Member member = memberService.findMember(login_id);
		Set<String> strings = redisService.viewedData(member.getAge(), member.getSex(), 5);
		String data =strings.toString();
		return data;
	}
}
