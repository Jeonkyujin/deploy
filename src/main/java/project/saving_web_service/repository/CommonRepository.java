package project.saving_web_service.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class CommonRepository<T> {

	@PersistenceContext
	protected final EntityManager em;

	protected CommonRepository(EntityManager em) {
		this.em = em;
	}

	public abstract List<T> findBy금리(String period, String amount);
	public abstract List<T> findby평판(String period, String amount);
	public abstract List<T> findby우대조건(String period, String amount, List<String> l);


}
