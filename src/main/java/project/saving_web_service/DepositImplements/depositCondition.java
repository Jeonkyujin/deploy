package project.saving_web_service.DepositImplements;

import java.util.List;

import project.saving_web_service.Abstract.AbstractDepositFilter;
import project.saving_web_service.domain.Deposit;

public class depositCondition extends AbstractDepositFilter {
	@Override
	protected List<Deposit> filterByCriteria(List<Deposit> deposit) {



		return deposit;
	}
}
