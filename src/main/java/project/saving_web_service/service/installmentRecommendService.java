package project.saving_web_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.saving_web_service.domain.Install;
import project.saving_web_service.repository.InstallmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class installmentRecommendService {

    private final InstallmentRepository installmentrepository;
    @Transactional
    public void insert(Install install){
            installmentrepository.put(install);
    }

    public List<Install>interest_rate(String s){
       return installmentrepository.findBy금리(s,4.00);
    }

    public List<Install>reputation(String s){return installmentrepository.findby평판(s);}

    public List<Install>bank(String s, String p){return installmentrepository.findby편의성(s, p);}

    public List<Install>Rate(String s, double d){return installmentrepository.findBy금리(s, d);}
}
