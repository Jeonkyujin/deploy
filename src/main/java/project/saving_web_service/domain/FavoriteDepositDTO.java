package project.saving_web_service.domain;

import java.util.List;

public class FavoriteDepositDTO {

	private List<Long> id;

	private Long memberId;
	private List<Long> depositId;

	public List<Long> getId() {
		return id;
	}

	public void setId(List<Long> id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public List<Long> getDepositId() {
		return depositId;
	}

	public void setDepositId(List<Long> depositId) {
		this.depositId = depositId;
	}
}
