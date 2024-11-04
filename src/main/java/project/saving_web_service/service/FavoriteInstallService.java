package project.saving_web_service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.FavoriteInstall;
import project.saving_web_service.domain.FavoriteInstallDTO;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.FavoriteInstallRepository;
import project.saving_web_service.repository.InstallmentRepository;
import project.saving_web_service.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class FavoriteInstallService {

	private final RedisService redisService;
	private final MemberRepository memberRepository;
	private final InstallmentRepository installmentRepository;
    private final FavoriteInstallRepository favoriteInstallRepository;

	public ResponseEntity<String> addFavorite(FavoriteInstallDTO favoriteInstallDTO) {
		Member member = memberRepository.findByRealId(favoriteInstallDTO.getMemberId());

		List<Long> installIds = favoriteInstallDTO.getInstallId();
		List<Long> conflicts = new ArrayList<>();
		List<Long> addedSuccessfully = new ArrayList<>();

		for (Long installId : installIds) {
			if (favoriteInstallRepository.findByMemberIdAndInstallId(favoriteInstallDTO.getMemberId(), installId).isPresent()) {
				conflicts.add(installId); // 중복된 상품 추가
				continue; // 이미 추가된 경우 다음으로 진행
			}

			Install install = installmentRepository.findbyId(installId);
			FavoriteInstall favoriteInstall = new FavoriteInstall(member, install);

			// Redis 업데이트
			redisService.addItemRecentlySaved(member.getAge(), member.getSex(), installId);

			// 관심 목록에 추가
			favoriteInstallRepository.save(favoriteInstall);
			addedSuccessfully.add(installId); // 성공적으로 추가된 상품 ID 기록
		}

		// 중복된 상품이 있을 경우와 없을 경우 적절히 반환
		if (!conflicts.isEmpty() && !addedSuccessfully.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("일부 상품은 이미 관심 목록에 추가되어 있습니다. 나머지 상품은 정상적으로 추가되었습니다.");
		} else if (conflicts.isEmpty() && !addedSuccessfully.isEmpty()) {
			return ResponseEntity.ok("모든 상품이 관심 목록에 추가되었습니다.");

		} else if (!conflicts.isEmpty() && addedSuccessfully.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("모든 상품이 관심 목록에 추가 되어 있습니다.");
		} else {
			return ResponseEntity.ok("관심 목록에 추가할 상품을 선택해주세요.");
		}
	}
	public void deleteFavorites(List<Long> favoriteInstallIds) {
		for (Long favoriteInstallId : favoriteInstallIds) {
			favoriteInstallRepository.deleteById(favoriteInstallId);
		}
	}
}
