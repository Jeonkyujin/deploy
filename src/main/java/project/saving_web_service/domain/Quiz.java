package project.saving_web_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;  // 퀴즈 질문

    private String option1;   // 선택지 1
    private String option2;   // 선택지 2
    private String option3;   // 선택지 3
    private String option4;   // 선택지 4

    private int correctOption; // 정답 번호 (1~4)

    private String explanation; // 정답 해설
}
