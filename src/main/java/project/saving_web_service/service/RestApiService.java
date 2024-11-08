package project.saving_web_service.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestApiService {
	public String execute(String loginId) {
		String jupyterApiUrl = "http://43.200.6.191:8888/api/contents/Untitled20Folder/lastProject.ipynb";

		// 요청 본문에 사용할 데이터 준비
		String requestPayload = "{\"login_id\":\"" + loginId + "\"}";

		// HTTP 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 본문과 헤더 설정
		HttpEntity<String> requestEntity = new HttpEntity<>(requestPayload, headers);

		// RestTemplate을 사용하여 POST 요청 보내기
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(jupyterApiUrl, requestEntity, String.class);

		return response;
	}
}
