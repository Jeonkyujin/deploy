package project.saving_web_service.domain;

import java.util.List;

public class FavoritesDeleteRequest {

	private List<Long> favoriteInstallIds;
	private List<Long> favoriteDepositIds;

	public List<Long> getFavoriteInstallIds() {
		return favoriteInstallIds;
	}

	public void setFavoriteInstallIds(List<Long> favoriteInstallIds) {
		this.favoriteInstallIds = favoriteInstallIds;
	}

	public List<Long> getFavoriteDepositIds() {
		return favoriteDepositIds;
	}

	public void setFavoriteDepositIds(List<Long> favoriteDepositIds) {
		this.favoriteDepositIds = favoriteDepositIds;
	}
}
