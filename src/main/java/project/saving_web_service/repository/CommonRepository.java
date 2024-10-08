package project.saving_web_service.repository;

import java.util.List;

public interface CommonRepository<T> {

	List<T> findBy금리(String period, String amount, String field);
	List<T> findby평판(String period, String amount, String field);
	List<T> findby우대조건(String period, String amount, String field, List<String> l);
}
