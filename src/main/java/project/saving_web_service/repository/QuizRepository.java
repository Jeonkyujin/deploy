package project.saving_web_service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.saving_web_service.domain.Quiz;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class QuizRepository {

    @PersistenceContext
    private final EntityManager em;


    public void save(Quiz quiz) {
        em.persist(quiz);
    }


    public Quiz findById(Long quizId) {
        return em.find(Quiz.class, quizId);
    }


    public List<Quiz> findAllQuizzes() {
        return em.createQuery("select q from Quiz q", Quiz.class)
                .getResultList();
    }


    public List<Quiz> findRandomQuizzes(int count) {
        List<Quiz> quizzes = findAllQuizzes();
        if (quizzes.size() <= count) {
            return quizzes;
        }
        return quizzes.stream()
                .sorted((a, b) -> Math.random() > 0.5 ? 1 : -1)
                .limit(count)
                .toList();
    }
}

