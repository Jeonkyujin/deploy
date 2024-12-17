package project.saving_web_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.FavoriteDepositDTO;
import project.saving_web_service.domain.FavoriteInstallDTO;
import project.saving_web_service.service.FavoriteDepositService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteDepositController {

	private final FavoriteDepositService favoriteDepositService;
	@PostMapping
	public ResponseEntity<String> addFavorite(@RequestBody FavoriteDepositDTO favoritedepositDTO, HttpSession session) {
		return favoriteDepositService.addFavorite(favoritedepositDTO, session);

	}


}
