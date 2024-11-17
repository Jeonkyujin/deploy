package project.saving_web_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;
import project.saving_web_service.service.NewsService;  // 뉴스 서비스 추가
import project.saving_web_service.service.RedisService;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final NewsService newsService;
    private final RedisService redisService;

    @GetMapping("/login")
    public String login(HttpSession httpSession, Model model) throws JsonProcessingException {
        String login_id = (String)httpSession.getAttribute("login_id");

        Member member = memberService.findMember(login_id);

        ObjectMapper objectMapper = new ObjectMapper();




        Set<String> strings = redisService.viewedData(member.getAge(), member.getSex(), 5);

        Set<String> currentTopProduct = redisService.viewedData(member.getAge(), member.getSex());

        Set<String> previousTopProduct = (Set<String>)httpSession.getAttribute("previousTopProduct");


        model.addAttribute("string", strings);
        model.addAttribute("member", member);
        model.addAttribute("login_id", login_id);
        List<Map<String, String>> newsList = newsService.getLatestNews();
        model.addAttribute("newsList", newsList);
        if (httpSession.getAttribute("firstLogin") == null) {
            httpSession.setAttribute("firstLogin", true);

        } else {
            if (!currentTopProduct.equals(previousTopProduct)) {
                System.out.println("---------------");
                model.addAttribute("showAlert", true);
                String alertMessage = "1위 상품이 변경되었습니다.<br>" + currentTopProduct;
                model.addAttribute("alertMessage", alertMessage);
                model.addAttribute("currentTopProduct", currentTopProduct);
                httpSession.setAttribute("previousTopProduct", currentTopProduct);
                model.addAttribute("notebookResult", null);

            }

        }
        return "login/login.html";
    }

}
