package project.saving_web_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	private final MemberRepository memberRepository;
	private final InstallmentRepository installmentRepository;
    private final FavoriteInstallRepository favoriteInstallRepository;

	public void addFavorite(FavoriteInstallDTO favoriteInstallDTO) {
		Member member = memberRepository.findByRealId(favoriteInstallDTO.getMemberId());

		List<Long> installids = favoriteInstallDTO.getInstallId();
		for (Long installId : installids) {
			Install install = installmentRepository.findbyId(installId);
			FavoriteInstall favoriteInstall = new FavoriteInstall(member, install);
			favoriteInstallRepository.save(favoriteInstall);
		}
	}

	public void deleteFavorites(List<Long> favoriteInstallIds) {
		for (Long favoriteInstallId : favoriteInstallIds) {
			favoriteInstallRepository.deleteById(favoriteInstallId);
		}
	}
}
