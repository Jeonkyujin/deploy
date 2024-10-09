package project.saving_web_service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import project.saving_web_service.Abstract.AbstractDepositFilter;
import project.saving_web_service.Abstract.AbstractInstallFilter;
import project.saving_web_service.DepositImplements.depositCondition;
import project.saving_web_service.DepositImplements.depositHighestRate;
import project.saving_web_service.DepositImplements.depositReputation;
import project.saving_web_service.InstallImplements.installCondition;
import project.saving_web_service.InstallImplements.installHighestRate;
import project.saving_web_service.InstallImplements.installReputation;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Install;

@Repository
public class DepositRepository implements CommonRepository<Deposit>{

	@PersistenceContext
	private final EntityManager em;

	public DepositRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Deposit> findBy금리(String period, String amount, String field) {
		// 넘겨받은 field 변수 안에 있는 값을 리스트로 만들어서 넣기
		List<String> fieldList;
		if (field == null || field.isEmpty()) {
			fieldList = new ArrayList<>();
		} else {
			fieldList = Arrays.asList(field.split(","));
		}
		//1차필터링: 분야에 값이 있다면 적합한 상품들로만 거르기
		String baseQuery = "Select i From Deposit i where i.분야 IN :fieldList";
		// 분야에 값이 없다면 전부 다 가져오기
		String baseQuery2 = "Select i From Deposit i";

		TypedQuery<Deposit> query;
		if (!fieldList.isEmpty()) {
			query = em.createQuery(baseQuery, Deposit.class);
			query.setParameter("fieldList", fieldList);
		} else {
			query = em.createQuery(baseQuery2, Deposit.class);
		}

		List<Deposit> allDeposit = query.getResultList();
		AbstractDepositFilter ADF = new depositHighestRate();
		return ADF.mainFilter(period, amount, allDeposit);
	}

	@Override
	public List<Deposit> findby평판(String period, String amount, String field) {
		// 넘겨받은 field 변수 안에 있는 값을 리스트로 만들어서 넣기
		List<String> fieldList;
		if (field == null || field.isEmpty()) {
			fieldList = new ArrayList<>();
		} else {
			fieldList = Arrays.asList(field.split(","));
		}
		//1차필터링: 분야에 값이 있다면 적합한 상품들로만 거르기
		String baseQuery = "Select i From Deposit i where i.분야 IN :fieldList AND i.금융회사명 IN (:banks)";
		// 분야에 값이 없다면 전부 다 가져오기
		String baseQuery2 = "Select i From Deposit i where i.금융회사명 IN (:banks)";

		TypedQuery<Deposit> query;
		if (!fieldList.isEmpty()) {
			query = em.createQuery(baseQuery, Deposit.class);
			query.setParameter("fieldList", fieldList);
		} else {
			query = em.createQuery(baseQuery2, Deposit.class);
		}

		List<String> banks = Arrays.asList("SBI저축은행", "웰컴저축은행", "하나은행", "신한은행");
		query.setParameter("banks", banks);
		List<Deposit> FilterByBank = query.getResultList();
		AbstractDepositFilter ADF = new depositReputation();
		return ADF.mainFilter(period, amount, FilterByBank);
	}

	@Override
	public List<Deposit> findby우대조건(String period, String amount, String field, List<String> l) {
		List<String> fieldList;
		if (field == null || field.isEmpty()) {
			fieldList = new ArrayList<>();
		} else {
			fieldList = Arrays.asList(field.split(","));
		}

		StringBuilder queryBuilder = new StringBuilder("Select i From Deposit i where ");

		if(!field.isEmpty()){
			queryBuilder.append("i.분야 IN (:fieldList) AND (");
		}
		else{
			queryBuilder.append("(");
		}

		for (int i = 0; i < l.size(); i++) {
			if (i == l.size() - 1){
				queryBuilder.append("i.우대조건 LIKE CONCAT('%', :condition").append(i).append(", '%'))");
			}
			else{
				queryBuilder.append("i.우대조건 LIKE CONCAT('%', :condition").append(i).append(", '%') OR ");
			}
		}

		TypedQuery<Deposit> query = em.createQuery(queryBuilder.toString(), Deposit.class);


		if(!field.isEmpty()){
			query.setParameter("fieldList", fieldList);
		}
		for (int i = 0; i < l.size(); i++) {
			query.setParameter("condition" + i, l.get(i));
		}

		List<Deposit> FilterByCondition  = query.getResultList();
		AbstractDepositFilter ADF = new depositCondition();
		return ADF.mainFilter(period,amount, FilterByCondition);
	}


}

