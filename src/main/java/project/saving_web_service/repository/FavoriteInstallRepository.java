package project.saving_web_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.saving_web_service.domain.FavoriteInstall;


public interface FavoriteInstallRepository extends JpaRepository<FavoriteInstall, Long> {
	@Query("SELECT f FROM FavoriteInstall f WHERE f.member.id = :memberId AND f.install.적금id = :installId")
	Optional<FavoriteInstall> findByMemberIdAndInstallId(@Param("memberId") Long memberId, @Param("installId") Long installId);

}
