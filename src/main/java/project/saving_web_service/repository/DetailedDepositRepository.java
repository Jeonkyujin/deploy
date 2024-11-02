package project.saving_web_service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import project.saving_web_service.Abstract.AbstractDepositFilter;
import project.saving_web_service.DepositImplements.depositAllProductsByRate;
import project.saving_web_service.DepositImplements.depositCondition;
import project.saving_web_service.DepositImplements.depositHighestRate;
import project.saving_web_service.DepositImplements.depositReputation;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Member;

@Repository
public class DetailedDepositRepository {

	@PersistenceContext
	private final EntityManager em;

	public DetailedDepositRepository(EntityManager em) {
		this.em = em;
	}

	public List<Deposit> findByCategory(String category, Member member) {

		String baseQuery = "Select d From Deposit d where d.상품명 Like :category";

		List<Deposit> resultList;

		TypedQuery<Deposit> query = em.createQuery(baseQuery, Deposit.class);
		resultList = query.setParameter("category", "%" + category + "%")
			.getResultList();
		return resultList;

	}
}

