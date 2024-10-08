package project.saving_web_service.domain;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.font.NumericShaper;

@Table(name = "deposit")
@Entity
@Getter
@Setter
public class Deposit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "예금id")
	private Long 예금id;

	private String 금융회사명;
	private String 상품명;
	private String 가입기간;
	private String 가입금액;
	private String 가입방법;
	private String 우대조건;
	private String 분야;
	private String 금리;


}
