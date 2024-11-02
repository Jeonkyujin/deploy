package project.saving_web_service.repository;

import java.lang.reflect.Member;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;


public abstract class statusBaseRepository<T> {

	public abstract List<T> findByStatus(String status);
}
