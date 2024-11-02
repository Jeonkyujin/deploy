package project.saving_web_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.CommonRepository;
import project.saving_web_service.repository.InstallmentRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class installmentRecommendService extends AbstractRecommendService<Install> {

    private final InstallmentRepository installmentrepository;

    @Override
    public List<Install> highestRate(Member member) {
        return installmentrepository.findBy금리(member.getPeriod(), member.getAmount());
    }

    @Override
    public List<Install> reputation(Member member) {
        return installmentrepository.findby평판(member.getPeriod(), member.getAmount());
    }

    public List<Install> condition(Member member){
        String a = member.getPreferredCondition();
        List<String> L;
        if (a.contains(",")){
            L = Arrays.asList(a.split(","));
        }
        else{
           L = Collections.singletonList(a);
        }

        return installmentrepository.findby우대조건(member.getPeriod(), member.getAmount(), L);
    }

    public Install findInstall(Long id){
        return installmentrepository.findbyId(id);
    }

    public List<Install> findAllInstall(){
        return installmentrepository.findAll();
    }

}
