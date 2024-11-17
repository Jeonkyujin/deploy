package project.saving_web_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	@CrossOrigin(origins = "http://43.200.6.191:8080")
	@GetMapping("/sse")
	public SseEmitter sse(HttpSession httpSession, Model model) {
		SseEmitter emitter = new SseEmitter(300_000L);
		String login_id = (String) httpSession.getAttribute("login_id");
		Member member = memberService.findMember(login_id);

		// 초기 세션 데이터를 Redis에서 가져와 복사본 생성
		Set<String> copiedRanking = new HashSet<>(redisService.viewedData(member.getAge(), member.getAge()));

		ObjectMapper objectMapper = new ObjectMapper();

		new Thread(() -> {
			try {
				while (true) {
					// 현재 Redis에서 1위 상품 가져오기
					Set<String> currentRanking = redisService.viewedData(member.getAge(), member.getSex());

					// 이전 랭킹과 현재 랭킹 비교
					Set<String> normalizedPreviousRanking = copiedRanking.stream()
						.map(String::trim)
						.map(String::toLowerCase)
						.collect(Collectors.toSet());

					Set<String> normalizedCurrentRanking = currentRanking.stream()
						.map(String::trim)
						.map(String::toLowerCase)
						.collect(Collectors.toSet());

					// 랭킹이 변경되었는지 확인
					if (!normalizedPreviousRanking.equals(normalizedCurrentRanking)) {
						// JSON 형식 데이터 생성 및 SSE 전송
						Map<String, Object> data = new HashMap<>();
						data.put("message", "1위 상품이 변경되었습니다");
						data.put("productNames", currentRanking);

						String json = objectMapper.writeValueAsString(data);
						emitter.send(SseEmitter.event().name("topRankingUpdate").data(json));

						// copiedRanking을 최신 값으로 업데이트
						copiedRanking.clear();
						copiedRanking.addAll(currentRanking);
					}

					// 1초 대기
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				emitter.completeWithError(e);
			}
		}).start();

		return emitter;
	}
}
