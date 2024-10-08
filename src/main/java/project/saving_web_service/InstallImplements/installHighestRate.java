package project.saving_web_service.InstallImplements;

import java.util.List;

import project.saving_web_service.Abstract.AbstractInstallFilter;
import project.saving_web_service.domain.Install;

public class installHighestRate extends AbstractInstallFilter {
	@Override
	protected List<Install> filterByCriteria(List<Install> installments) {
		installments.sort((install1, install2) -> {
			// 첫 번째 Install 객체에서 금리 추출
			double maxRate1 = getMaxRate(install1.get금리());

			// 두 번째 Install 객체에서 금리 추출
			double maxRate2 = getMaxRate(install2.get금리());

			// 금리가 높은 순으로 정렬 (내림차순)
			return Double.compare(maxRate2, maxRate1);
		});

		// 정렬된 결과 반환
		if (installments.size() > 10) {
			return installments.subList(0,10);
		}
		return installments;
	}

	private double getMaxRate(String 금리) {
		if (금리.contains("~")) {
			// "~"로 구분된 금리 범위에서 최대값 추출
			String[] 금리범위 = 금리.split("~");

			if (금리범위.length >= 2) {
				String firstRateStr = 금리범위[0].trim();
				String secondRateStr = 금리범위[1].trim();

				// 첫 번째 금리가 비어있지 않으면 두 값을 비교, 그렇지 않으면 두 번째 값 반환
				if (!firstRateStr.isEmpty()) {
					double firstRate = Double.parseDouble(firstRateStr);
					double secondRate = Double.parseDouble(secondRateStr);
					return Math.max(firstRate, secondRate);  // 더 큰 값 반환
				} else {
					return Double.parseDouble(secondRateStr);  // 첫 번째 값이 비어있으면 두 번째 값 반환
				}
			} else {
				// 금리범위가 하나만 있을 때
				return Double.parseDouble(금리범위[0].trim());
			}
		} else {
			// "~"가 없는 경우 단일 금리 값을 반환
			return Double.parseDouble(금리.trim());
		}
	}
}

