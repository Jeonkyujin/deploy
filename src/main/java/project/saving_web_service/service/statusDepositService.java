package project.saving_web_service.service;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.repository.statusDepositRepository;

@Service
@RequiredArgsConstructor
public class statusDepositService extends statusBaseService<Deposit> {

	private final statusDepositRepository SDR;

	@Override
	public List<Deposit> findStatus(String status) {
		return SDR.findByStatus(status);
	}
}
