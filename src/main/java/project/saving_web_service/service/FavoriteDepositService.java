package project.saving_web_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public void addFavorite(FavoriteDepositDTO favoriteDepositDTO) {
		Member member = memberRepository.findByRealId(favoriteDepositDTO.getMemberId());

		List<Long> depositIds = favoriteDepositDTO.getDepositId();
		for (Long depositId : depositIds) {
			Deposit deposit = depositRepository.findbyId(depositId);
			FavoriteDeposit favoritedeposit = new FavoriteDeposit(member, deposit);
			favoritedepositRepository.save(favoritedeposit);
		}

	}

	public void deleteFavorites(List<Long> favoriteDepositIds) {
		for (Long favoriteDepositId : favoriteDepositIds) {
			favoritedepositRepository.deleteById(favoriteDepositId);
		}
	}
}
