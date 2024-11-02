package project.saving_web_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "favorite_deposit")
@Getter@Setter
public class FavoriteDeposit {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "예금id", nullable = false)
	private Deposit deposit;

	public FavoriteDeposit() {
	}

	public FavoriteDeposit(Member member, Deposit deposit) {
		this.member = member;
		this.deposit = deposit;
	}
}
