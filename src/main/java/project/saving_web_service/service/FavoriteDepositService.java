package project.saving_web_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.FavoriteDeposit;
import project.saving_web_service.domain.FavoriteDepositDTO;
import project.saving_web_service.domain.FavoriteInstall;
import project.saving_web_service.domain.FavoriteInstallDTO;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.DepositRepository;
import project.saving_web_service.repository.FavoriteDepositRepository;
import project.saving_web_service.repository.FavoriteInstallRepository;
import project.saving_web_service.repository.InstallmentRepository;
import project.saving_web_service.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class FavoriteDepositService {

	private final MemberRepository memberRepository;
	private final DepositRepository depositRepository;
	private final FavoriteDepositRepository favoritedepositRepository;
	private final RedisService redisService;
	public ResponseEntity<String> addFavorite(FavoriteDepositDTO favoriteDepositDTO, HttpSession session) {
		Member member = memberRepository.findByRealId(favoriteDepositDTO.getMemberId());

		List<Long> depositIds = favoriteDepositDTO.getDepositId();
		List<Long> conflicts = new ArrayList<>();
		List<Long> addedSuccessfully = new ArrayList<>();
		String a = "abc";

		for (Long depositId : depositIds) {
			if (favoritedepositRepository.findByMemberIdAndDepositId(favoriteDepositDTO.getMemberId(), depositId).isPresent()) {
				conflicts.add(depositId); // 중복된 상품 추가
				continue; // 이미 추가된 경우 다음으로 진행
			}

			Deposit deposit = depositRepository.findbyId(depositId);
			FavoriteDeposit favoriteDeposit = new FavoriteDeposit(member, deposit);

			Set<String> strings1 = redisService.viewedData(member.getAge(), member.getSex());
			redisService.addItemRecentlySaved(member.getAge(), member.getSex(), depositId,a);
			Set<String> strings2 = redisService.viewedData(member.getAge(), member.getSex());
			if(!strings1.equals(strings2)){
				session.setAttribute("currentTopRanking",strings2);
			}
			// 관심 목록에 추가
			favoritedepositRepository.save(favoriteDeposit);
			addedSuccessfully.add(depositId); // 성공적으로 추가된 상품 ID 기록
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

	public void deleteFavorites(List<Long> favoriteDepositIds) {
		for (Long favoriteDepositId : favoriteDepositIds) {
			favoritedepositRepository.deleteById(favoriteDepositId);
		}
	}
}
