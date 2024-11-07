package project.saving_web_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.saving_web_service.domain.Quiz;
import project.saving_web_service.service.QuizService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;


    @GetMapping("/quiz")
    public String showQuizPage(Model model, HttpSession session) {
        String login_id = (String)session.getAttribute("login_id");
        List<Quiz> quizzes = quizService.getRandomQuizzes();
        if (quizzes.isEmpty()) {
            model.addAttribute("errorMessage", "현재 제공할 수 있는 퀴즈가 없습니다.");
            return "quiz-error";
        }
        model.addAttribute("login_id", login_id);
        model.addAttribute("quizzes", quizzes);
        return "quiz";
    }


    @PostMapping("/quiz/submit")
    public String submitQuiz(@RequestParam Map<String, String> quizOptions,
                             @RequestParam("quizIds") Long[] quizIds,
                             Model model, HttpSession session) {
        String login_id = (String)session.getAttribute("login_id");
        int correctCount = 0;
        List<Map<String, String>> results = new ArrayList<>();


        for (Long quizId : quizIds) {
            String key = "quizOption_" + quizId;
            Map<String, String> result = new HashMap<>();
            Quiz quiz = quizService.findQuizById(quizId);


            if (quizOptions.containsKey(key)) {
                int selectedOption = Integer.parseInt(quizOptions.get(key));
                boolean isCorrect = quiz.getCorrectOption() == selectedOption;

                if (isCorrect) {
                    correctCount++;
                    result.put("status", "정답");
                } else {
                    result.put("status", "오답");
                }
                result.put("selectedOption", String.valueOf(selectedOption));
            } else {
                result.put("status", "미답변");
                result.put("selectedOption", "응답하지 않음");
            }

            result.put("question", quiz.getQuestion());
            result.put("correctOption", String.valueOf(quiz.getCorrectOption()));
            result.put("explanation", quiz.getExplanation());
            results.add(result);
        }
        model.addAttribute("login_id", login_id);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalCount", quizIds.length);
        model.addAttribute("results", results);
        return "quiz-result";
    }

}