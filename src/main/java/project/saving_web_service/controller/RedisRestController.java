package project.saving_web_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		String data = strings.toString();
		return data;
	}
	@GetMapping("/sse")
	public SseEmitter sse(HttpSession httpSession) {
		SseEmitter emitter = new SseEmitter();
		String login_id = (String) httpSession.getAttribute("login_id");
		Member member = memberService.findMember(login_id);

		// AtomicReference<Set<String>> previousTopProduct = new AtomicReference<>(
		// 	(Set<String>) httpSession.getAttribute("previousTopProduct")
		// );
		//
		// AtomicReference<Set<String>> currentTopProduct = new AtomicReference<>(
		// 	(Set<String>) httpSession.getAttribute("currentTopProduct")
		// );

		ObjectMapper objectMapper = new ObjectMapper();


		new Thread(() -> {
			try {
				while (true) {
					// 현재 1위 상품 가져오기

					Set<String> a  = (Set<String>) httpSession.getAttribute("previousTopProduct");
					// 1위 상품이 변경되었는지 확인
					if (a != null ) {
						// JSON 형식의 데이터 생성
						Set<String> b =  (Set<String>) httpSession.getAttribute("currentTopProduct");
						Map<String, Object> data = new HashMap<>();
						data.put("message", "1위 상품이 변경되었습니다");
						data.put("productNames", b); // Set을 그대로 JSON에 넣기

						// JSON 문자열로 변환
						String json = objectMapper.writeValueAsString(data);

						// JSON 데이터 전송
						emitter.send(SseEmitter.event().name("topRankingUpdate").data(json));
						httpSession.setAttribute("previousTopProduct", null);
					}

					// 1초마다 체크 (필요에 따라 조정 가능)
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				emitter.completeWithError(e);
			}
		}).start();

		return emitter;
	}
}
