package project.saving_web_service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import project.saving_web_service.Abstract.AbstractInstallFilter;
import project.saving_web_service.InstallImplements.installCondition;
import project.saving_web_service.InstallImplements.installHighestRate;
import project.saving_web_service.InstallImplements.installReputation;
import project.saving_web_service.domain.Install;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository

public class InstallmentRepository extends CommonRepository<Install> {


    protected InstallmentRepository(EntityManager em) {
        super(em);
    }

    public List<Install> findBy금리(String period, String amount) {
        // 넘겨받은 field 변수 안에 있는 값을 리스트로 만들어서 넣기

        // 분야에 값이 없다면 전부 다 가져오기
        String baseQuery2 = "Select i From Install i";

        TypedQuery<Install> query;


        query = em.createQuery(baseQuery2, Install.class);


        List<Install> allInstallments = query.getResultList();
        AbstractInstallFilter AIF = new installHighestRate();
        return AIF.mainFilter(period, amount, allInstallments);
    }

    public List<Install> findby평판(String period, String amount) {


        // 분야에 값이 없다면 전부 다 가져오기
        String baseQuery2 = "Select i From Install i where i.금융회사명 IN (:banks)";

        TypedQuery<Install> query;

        query = em.createQuery(baseQuery2, Install.class);

        List<String> banks = Arrays.asList("국민은행", "우리은행", "하나은행", "신한은행", "SBI저축은행", "웰컴저축은행");
        query.setParameter("banks", banks);
        List<Install> FilterByBank = query.getResultList();
        AbstractInstallFilter AIF = new installReputation();
        return AIF.mainFilter(period, amount, FilterByBank);

    }

    public List<Install> findby우대조건(String period, String amount, List<String> l) {

        StringBuilder queryBuilder = new StringBuilder("Select i From Install i where ");

        queryBuilder.append("(");


        for (int i = 0; i < l.size(); i++) {
            if (i == l.size() - 1){
                queryBuilder.append("i.우대조건 LIKE CONCAT('%', :condition").append(i).append(", '%'))");
            }
            else{
            queryBuilder.append("i.우대조건 LIKE CONCAT('%', :condition").append(i).append(", '%') OR ");
            }
        }

        TypedQuery<Install> query = em.createQuery(queryBuilder.toString(), Install.class);



        for (int i = 0; i < l.size(); i++) {
            query.setParameter("condition" + i, l.get(i));
        }

        List<Install> FilterByCondition  = query.getResultList();
        AbstractInstallFilter AIF = new installCondition();
        return AIF.mainFilter(period,amount, FilterByCondition);
    }

    public Install findbyId(Long id) {
        return em.find(Install.class,id);
    }

    public List<Install> findAll() {
        return em.createQuery("Select i From Install i", Install.class)
            .getResultList();
    }
}
