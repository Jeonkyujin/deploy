package project.saving_web_service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InstallmentRepository {

    @PersistenceContext
    private final EntityManager em;

    public void put(Install install) {
        em.persist(install);
    }

    public List<Install> findBy금리(String period, double v) {
        try {
            // '개월'을 제거하고 숫자만 추출
            String cleanedPeriod;
            try {
                cleanedPeriod = period.replaceAll("[^0-9]", ""); // 숫자만 추출
            } catch (NumberFormatException e) {
                // 문자열에 숫자가 없거나 변환에 실패한 경우 처리
                throw new IllegalArgumentException("Invalid period format: " + period, e);
            }

            // 숫자 부분을 정수로 변환
            Integer periodValue = Integer.parseInt(cleanedPeriod);

            // Native Query
            String query = "SELECT * FROM installment " +
                    "WHERE (" +
                    "   CAST(SUBSTRING_INDEX(REPLACE(금리, '%', ''), '~', 1) AS DECIMAL(10,2)) >= :minRate " +
                    "   AND (" +
                    "       REPLACE(가입기간, '개월', '') = :periodValue " +
                    "       OR (" +
                    "           CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', 1) AS UNSIGNED) <= :periodValue " +
                    "           AND CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', -1) AS UNSIGNED) >= :periodValue " +
                    "       )" +
                    "   )" +
                    ") " +
                    "ORDER BY CAST(SUBSTRING_INDEX(REPLACE(금리, '%', ''), '~', 1) AS DECIMAL(10,2)) DESC";


            return em.createNativeQuery(query, Install.class)
                    .setParameter("minRate", v)
                    .setParameter("periodValue", periodValue)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (IllegalArgumentException e) {
            // 적절한 예외 처리
            throw e;
        }
    }


    public List<Install> findby평판(String period) {
        try {
            // '개월'을 제거하고 숫자만 추출
            String cleanedPeriod = period.replaceAll("[^0-9]", "");
            Integer periodValue = Integer.parseInt(cleanedPeriod);

            // Native Query
            String query = "SELECT * FROM installment " +
                    "WHERE (" +
                    "   REPLACE(가입기간, '개월', '') = :periodValue " +
                    "   OR " +
                    "   (CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', 1) AS UNSIGNED) <= :periodValue " +
                    "   AND CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', -1) AS UNSIGNED) >= :periodValue) " +
                    ") " +
                    "AND 금융회사명 IN ('신한은행', '하나은행', 'SBI저축은행', '웰컴저축은행') " +
                    "ORDER BY CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', 1) AS UNSIGNED) DESC";


            return em.createNativeQuery(query, Install.class)
                    .setParameter("periodValue", periodValue)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid period format: " + period, e);
        }
    }

    public List<Install> findby편의성(String s, String p) {
        try {
            // '개월'을 제거하고 숫자만 추출
            String cleanedPeriod;
            try {
                cleanedPeriod = p.replaceAll("[^0-9]", ""); // 숫자만 추출
            } catch (NumberFormatException e) {
                // 문자열에 숫자가 없거나 변환에 실패한 경우 처리
                throw new IllegalArgumentException("Invalid period format: " + p, e);
            }

            // 숫자 부분을 정수로 변환
            Integer periodValue = Integer.parseInt(cleanedPeriod);

            String query = "SELECT * FROM installment " +
                    "WHERE (" +
                    "   REPLACE(가입기간, '개월', '') = :periodValue " +
                    "   OR " +
                    "   (CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', 1) AS UNSIGNED) <= :periodValue " +
                    "   AND CAST(SUBSTRING_INDEX(REPLACE(가입기간, '개월', ''), '~', -1) AS UNSIGNED) >= :periodValue) " +
                    ") " +
                    "AND 금융회사명 = :bank";

            return em.createNativeQuery(query, Install.class)
                    .setParameter("bank", s)
                    .setParameter("periodValue", p)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid period format: " + p, e);
        }

    }

}

