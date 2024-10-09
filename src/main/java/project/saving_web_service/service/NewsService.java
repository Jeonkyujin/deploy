package project.saving_web_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.text.StringEscapeUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final RestTemplate restTemplate;

    @Value("${naver.api.url}")
    private String apiUrl;

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;


    public List<Map<String, String>> getLatestNews() {
        log.info("Fetching latest news from Naver API");

        try {
            String query = URLEncoder.encode("금융", StandardCharsets.UTF_8.toString());
            URI uri = URI.create(apiUrl + "?query=" + query + "&display=5&sort=sim");  // "date" or "sim"

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);

            log.info("Requesting news from: {}", uri);
            log.info("Using Client ID: {}", clientId);
            log.info("Using Client Secret: {}", clientSecret);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Map.class);

            log.info("Response Status Code: {}", response.getStatusCode());

            List<Map<String, String>> newsList = new ArrayList<>();
            if (response.getBody() != null) {
                List<Map<String, String>> items = (List<Map<String, String>>) response.getBody().get("items");
                for (Map<String, String> item : items) {
                    Map<String, String> newsItem = new HashMap<>();
                    String title = item.get("title").replaceAll("<[^>]*>", "");
                    title = StringEscapeUtils.unescapeHtml4(title);
                    newsItem.put("title", title);
                    newsItem.put("link", item.get("link"));
                    log.info("News Title: {}", newsItem.get("title"));
                    newsList.add(newsItem);
                }
            }

            log.info("Successfully fetched {} news items", newsList.size());
            return newsList;

        } catch (Exception e) {
            log.error("Failed to fetch news", e);
            return new ArrayList<>();
        }
    }
}
