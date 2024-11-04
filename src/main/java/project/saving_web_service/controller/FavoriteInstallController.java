package project.saving_web_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.FavoriteInstallDTO;
import project.saving_web_service.domain.FavoritesDeleteRequest;
import project.saving_web_service.service.FavoriteDepositService;
import project.saving_web_service.service.FavoriteInstallService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteInstallController {

	private final FavoriteInstallService favoriteInstallService;
	private final FavoriteDepositService favoriteDepositService;

	@PostMapping
	public ResponseEntity<String> addFavorite(@RequestBody FavoriteInstallDTO favoriteInstallDTO) {
		return favoriteInstallService.addFavorite(favoriteInstallDTO);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteFavorites(@RequestBody Map<String, List<Long>> requestBody) {
		List<Long> favoriteInstallIds = requestBody.get("favoriteInstallIds");
		List<Long> favoriteDepositIds = requestBody.get("favoriteDepositIds");

		favoriteInstallService.deleteFavorites(favoriteInstallIds);
		favoriteDepositService.deleteFavorites(favoriteDepositIds);

		return ResponseEntity.ok().build();
	}

}
