package project.saving_web_service.InstallImplements;

import java.util.List;

import project.saving_web_service.Abstract.AbstractInstallFilter;
import project.saving_web_service.domain.Install;

public class installCondition extends AbstractInstallFilter {
	@Override
	protected List<Install> filterByCriteria(List<Install> installments) {
		if (installments.size() > 10) {
			return installments.subList(0,10);
		}
		return installments;
	}
}
