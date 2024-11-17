/*package project.saving_web_service.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.RedisService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TopRankingController {

	private final MemberService memberService;
	private final RedisService redisService;

	@PostMapping("/saveTopProduct")
	public ResponseEntity<Void> saveTopProduct(HttpSession httpSession) {
		// 세션에서 로그인 ID 가져오기
		String loginId = (String) httpSession.getAttribute("login_id");
		if (loginId == null) {
			return ResponseEntity.status(401).build();
		}

		// 로그인 ID로 멤버 정보 조회
		Member member = memberService.findMember(loginId);

		// Redis에서 멤버의 연령 및 성별을 기반으로 현재 1위 상품 목록 가져오기
		Set<String> currentTopProduct = redisService.viewedData(member.getAge(), member.getSex());

		// 세션에 현재 1위 상품 목록 저장
		httpSession.setAttribute("previousTopProduct", currentTopProduct);

		// 성공적으로 저장되었음을 반환
		return ResponseEntity.ok().build();
	}
}*/