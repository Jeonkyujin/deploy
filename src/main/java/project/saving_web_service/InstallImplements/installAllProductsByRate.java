package project.saving_web_service.InstallImplements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.saving_web_service.Abstract.AbstractInstallFilter;
import project.saving_web_service.domain.Install;

public class installAllProductsByRate extends AbstractInstallFilter {
	@Override
	protected List<Install> filterByCriteria(List<Install> installments) {
		return null;
	}

	public List<Install> mainFilter(String period, String amount, List<Install> installment){
		List <Install> firstFilter = FilterByPeriod(installment, period);
		List <Install> secondFilter = FilterByAmount(firstFilter, amount);

		return secondFilter;
	}

	private List<Install> FilterByPeriod(List<Install> installment, String period){
		List<Install> firstFilterRecommendResult = new ArrayList<>();
		for (Install allInstallment : installment) {
			if (allInstallment.get가입기간().contains("~")) {
				List<String> p = Arrays.asList(allInstallment.get가입기간().split("~"));
				if (p.size() >= 2) {
					if (!p.get(0).isEmpty()) {
						int start = Integer.parseInt(p.get(0).trim());
						int end = Integer.parseInt(p.get(1).trim());
						int a = Integer.parseInt(period);
						if (a >= start && a <= end) {
							firstFilterRecommendResult.add(allInstallment);
						}
					} else {
						int start = 0;
						int end = Integer.parseInt(p.get(1).trim());
						int a = Integer.parseInt(period);
						if (a >= start && a <= end) {
							firstFilterRecommendResult.add(allInstallment);
						}
					}

				} else {
					int start = Integer.parseInt(p.get(0).trim());
					int end = 100;
					int a = Integer.parseInt(period);
					if (a >= start && a <= end) {
						firstFilterRecommendResult.add(allInstallment);
					}
				}
			} else if (allInstallment.get가입기간().contains(",")) {
				List<String> p = Arrays.asList(allInstallment.get가입기간().split(","));
				if (p.contains(period)) {
					firstFilterRecommendResult.add(allInstallment);
				}
			} else if (allInstallment.get가입기간().equals("제한없음")) {
				firstFilterRecommendResult.add(allInstallment);

			} else {
				// 단일 값으로 되어 있을 경우 (예: 12, 24 등)
				try {
					// 가입기간 값을 int로 변환
					int singlePeriod = Integer.parseInt(allInstallment.get가입기간().trim());
					int a = Integer.parseInt(period);  // 입력받은 period 값도 int로 변환

					// 가입기간과 입력된 period가 동일한지 확인
					if (a == singlePeriod) {
						firstFilterRecommendResult.add(allInstallment);  // 동일하면 리스트에 추가
					}
				} catch (NumberFormatException e) {
					// 가입기간이 숫자로 변환되지 않는 경우 예외 처리 (잘못된 형식인 경우)
					System.out.println("잘못된 가입기간 형식: " + allInstallment.get가입기간());
				}
			}
		}
		return firstFilterRecommendResult;
	}

	private List<Install> FilterByAmount(List<Install> installment, String amount){
		List<Install> secondFilterRecommendResult = new ArrayList<>();
		for (Install firstFilter : installment) {
			if (firstFilter.get가입금액().contains("~")) {

				int a = Integer.parseInt(amount);
				List<String> price = Arrays.asList(firstFilter.get가입금액().split("~"));
				if (price.size() >= 2) {
					if (!price.get(0).isEmpty()) {
						double start = Double.parseDouble(price.get(0).trim());
						double end = Double.parseDouble(price.get(1).trim());

						if (a >= start && a <= end) {
							secondFilterRecommendResult.add(firstFilter);
						}
					} else {

						double end = Double.parseDouble(price.get(1).trim());
						if (a <= end) {
							secondFilterRecommendResult.add(firstFilter);
						}
					}

				} else {
					double start = Double.parseDouble(price.get(0).trim());
					int end = 10000;
					if (a >= start && a <= end) {
						secondFilterRecommendResult.add(firstFilter);
					}
				}
			} else if (firstFilter.get가입금액().equals("제한없음")) {
				secondFilterRecommendResult.add(firstFilter);
			}
		}
		return secondFilterRecommendResult;
	}

}
