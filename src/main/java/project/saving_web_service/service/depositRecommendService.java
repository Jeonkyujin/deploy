package project.saving_web_service.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.DepositRepository;

@Service
@RequiredArgsConstructor
public class depositRecommendService extends AbstractRecommendService<Deposit> {

	private final DepositRepository depositRepository;
	@Override
	public List<Deposit> highestRate(Member member) {
		return depositRepository.findBy금리(member.getPeriod(), member.getAmount(), member.getField());
	}

	@Override
	public List<Deposit> reputation(Member member) {
		return depositRepository.findby평판(member.getPeriod(), member.getAmount(), member.getField());
	}


	@Override
	public List<Deposit> condition(Member member) {
		String a = member.getPreferredCondition();
		List<String> L;
		if (a.contains(",")){
			L = Arrays.asList(a.split(","));
		}
		else{
			L = Collections.singletonList(a);
		}

		return depositRepository.findby우대조건(member.getPeriod(), member.getAmount(), member.getField(), L);
	}
}
