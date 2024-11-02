package project.saving_web_service.domain;

import java.util.List;

public class FavoriteInstallDTO {

	private List<Long> id;

	private Long memberId;
	private List<Long> installId;

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

	public List<Long> getInstallId() {
		return installId;
	}

	public void setInstallId(List<Long> installId) {
		this.installId = installId;
	}
}
