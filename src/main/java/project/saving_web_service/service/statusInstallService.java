package project.saving_web_service.service;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Install;
import project.saving_web_service.repository.statusInstallRepository;

@Service
@RequiredArgsConstructor
public class statusInstallService extends statusBaseService<Install> {

	private final statusInstallRepository sIR;

	@Override
	public List<Install> findStatus(String status) {
		return sIR.findByStatus(status);
	}
}
