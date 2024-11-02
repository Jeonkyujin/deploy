package project.saving_web_service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Install;

@Repository
@RequiredArgsConstructor
public class statusDepositRepository extends statusBaseRepository<Deposit> {

	@PersistenceContext
	private final EntityManager em;

	@Override
	public List<Deposit> findByStatus(String status) {
		List<String> a = new ArrayList<>();
		if(status.contains(",")){
			a = Arrays.asList(status.split(","));
		}
		else{
			a = Collections.singletonList(status);
		}
		StringBuilder jpql = new StringBuilder("Select d From Deposit d where ");
		for (int i = 0; i < a.size(); i++) {
			if (i > 0) {
				jpql.append(" or ");
			}
			jpql.append("d.분야 = :status").append(i);
		}

		TypedQuery<Deposit> query = em.createQuery(jpql.toString(), Deposit.class);

		for (int i = 0; i < a.size(); i++) {
			query.setParameter("status" + i, a.get(i));
		}

		return query.getResultList();

	}
}
