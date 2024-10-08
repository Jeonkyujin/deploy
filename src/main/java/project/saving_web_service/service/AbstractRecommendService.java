package project.saving_web_service.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Member;

@RequiredArgsConstructor
public abstract class AbstractRecommendService<T> {

	public abstract List<T> highestRate(Member member);
	public abstract List<T> reputation(Member member);
	public abstract List<T> condition(Member member);

	public List<T> recommend(Member member) {
		if (member.getImportant().equals("높은 금리")) {
			return highestRate(member);
		} else if (member.getImportant().equals("해당 금융권에 대한 평판")) {
			return reputation(member);
		} else if (member.getImportant().equals("우대조건")) {
			return condition(member);
		}
		return null;
	}


}
