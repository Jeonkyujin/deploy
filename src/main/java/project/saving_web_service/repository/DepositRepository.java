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
public class DepositRepository extends CommonRepository<Deposit>{

	protected DepositRepository(EntityManager em) {
		super(em);
	}

	@Override
	public List<Deposit> findBy금리(String period, String amount) {
		// 넘겨받은 field 변수 안에 있는 값을 리스트로 만들어서 넣기

		//1차필터링: 분야에 값이 있다면 적합한 상품들로만 거르기

		// 분야에 값이 없다면 전부 다 가져오기
		String baseQuery2 = "Select i From Deposit i";

		TypedQuery<Deposit> query;

		query = em.createQuery(baseQuery2, Deposit.class);


		List<Deposit> allDeposit = query.getResultList();
		AbstractDepositFilter ADF = new depositHighestRate();
		return ADF.mainFilter(period, amount, allDeposit);
	}

	@Override
	public List<Deposit> findby평판(String period, String amount) {
		// 넘겨받은 field 변수 안에 있는 값을 리스트로 만들어서 넣기

		//1차필터링: 분야에 값이 있다면 적합한 상품들로만 거르기

		// 분야에 값이 없다면 전부 다 가져오기
		String baseQuery2 = "Select i From Deposit i where i.금융회사명 IN (:banks)";

		TypedQuery<Deposit> query;

		query = em.createQuery(baseQuery2, Deposit.class);


		List<String> banks = Arrays.asList("국민은행", "우리은행", "하나은행", "신한은행", "SBI저축은행", "웰컴저축은행");
		query.setParameter("banks", banks);
		List<Deposit> FilterByBank = query.getResultList();
		AbstractDepositFilter ADF = new depositReputation();
		return ADF.mainFilter(period, amount, FilterByBank);
	}

	@Override
	public List<Deposit> findby우대조건(String period, String amount, List<String> l) {


		StringBuilder queryBuilder = new StringBuilder("Select d From Deposit d where ");

		queryBuilder.append("(");


		for (int i = 0; i < l.size(); i++) {
			if (i == l.size() - 1){
				queryBuilder.append("d.우대조건 LIKE CONCAT('%', :condition").append(i).append(", '%'))");
			}
			else{
				queryBuilder.append("d.우대조건 LIKE CONCAT('%', :condition").append(i).append(", '%') OR ");
			}
		}

		TypedQuery<Deposit> query = em.createQuery(queryBuilder.toString(), Deposit.class);



		for (int i = 0; i < l.size(); i++) {
			query.setParameter("condition" + i, l.get(i));
		}

		List<Deposit> FilterByCondition  = query.getResultList();
		AbstractDepositFilter ADF = new depositCondition();
		return ADF.mainFilter(period,amount, FilterByCondition);
	}

	public Deposit findbyId(Long id) {
		return em.find(Deposit.class,id);
	}

	public List<Deposit> findAll() {
		return em.createQuery("Select D From Deposit D", Deposit.class)
			.getResultList();
	}
}

