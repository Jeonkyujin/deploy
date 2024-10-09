package project.saving_web_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.saving_web_service.domain.Quiz;
import project.saving_web_service.repository.QuizRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;


    public List<Quiz> getRandomQuizzes() {
        return quizRepository.findRandomQuizzes(5);
    }


    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }


    public String checkAnswer(Long quizId, int selectedOption) {
        Quiz quiz = findQuizById(quizId);
        if (quiz == null) {
            throw new IllegalArgumentException("Invalid quiz ID");
        }
        boolean isCorrect = quiz.getCorrectOption() == selectedOption;
        return isCorrect ? "정답입니다!" : "오답입니다. " + quiz.getExplanation();
    }
}

