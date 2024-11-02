package project.saving_web_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.saving_web_service.domain.FavoriteDeposit;
import project.saving_web_service.domain.FavoriteInstall;

@Repository
public interface FavoriteDepositRepository extends JpaRepository<FavoriteDeposit,Long> {

	@Query("SELECT f FROM FavoriteDeposit f WHERE f.member.id = :memberId AND f.deposit.예금id = :depositId")
	Optional<FavoriteInstall> findByMemberIdAndInstallId(@Param("memberId") Long memberId, @Param("depositId") Long installId);

}
