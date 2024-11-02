package project.saving_web_service.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.InstallmentRepository;

@RequiredArgsConstructor
public abstract class AbstractRecommendService<T> {


	public abstract List<T> highestRate(Member member);
	public abstract List<T> reputation(Member member);
	public abstract List<T> condition(Member member);

	public List<T> recommend(Member member) {
		List<T> highestRate = member.getImportant().contains("높은 금리") ? highestRate(member) : Collections.emptyList();
		List<T> reputation = member.getImportant().contains("해당 금융권에 대한 평판") ? reputation(member) : Collections.emptyList();
		List<T> condition = member.getImportant().contains("우대조건") ? condition(member) : Collections.emptyList();


		List<T> intersection = new ArrayList<>();

		if (!highestRate.isEmpty()) {
			intersection.addAll(highestRate);
		} else if (!reputation.isEmpty()) {
			intersection.addAll(reputation);
		} else if (!condition.isEmpty()) {
			intersection.addAll(condition);
		}

		// 각 리스트와의 교집합 계산
		if (member.getImportant().contains("해당 금융권에 대한 평판")) {
			intersection.retainAll(reputation);
		}

		if (member.getImportant().contains("우대조건")) {
			intersection.retainAll(condition);
		}

		return intersection;
	}

	public List<T> recommend(Member member, List<T> firstFilterInstall) {
		List<T> highestRate = member.getImportant().contains("높은 금리") ? highestRate(member) : Collections.emptyList();
		List<T> reputation = member.getImportant().contains("해당 금융권에 대한 평판") ? reputation(member) : Collections.emptyList();
		List<T> condition = member.getImportant().contains("우대조건") ? condition(member) : Collections.emptyList();

		List<T> intersection = new ArrayList<>();

		intersection.addAll(firstFilterInstall);

		if(!condition.isEmpty()){
			intersection.retainAll(condition);
			return intersection;
		}


		return intersection;
	}




}