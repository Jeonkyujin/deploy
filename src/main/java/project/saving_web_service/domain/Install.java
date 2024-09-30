package project.saving_web_service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.font.NumericShaper;

@Table(name = "installment")
@Entity
@Getter
@Setter
public class Install {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "적금id")
    private Long 적금id;

    private String 금융회사명;
    private String 상품명;
    private String 가입기간;
    private String 가입금액;
    private String 가입방법;
    private String 우대조건;
    private String 분야;
    private String 금리;


}