package project.saving_web_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.DetailedDepositRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailedDepositRecommendService {
	private final DetailedDepositRepository DDR;
	public List<Deposit> category(String category, Member member) {
		return DDR.findByCategory(category, member);
	}
}
