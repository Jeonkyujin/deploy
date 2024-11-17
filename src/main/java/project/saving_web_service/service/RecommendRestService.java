package project.saving_web_service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.FavoriteDeposit;
import project.saving_web_service.domain.FavoriteInstall;
import project.saving_web_service.domain.Install;
import project.saving_web_service.domain.Member;

@Service
@RequiredArgsConstructor
public class RecommendRestService {

	private final MemberService memberService;

	public Map<String,Object> recommend(Member member){
		int[][] listData = {
			{0, 0, 0},
			{0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0},
			{0, 0}
		};

		Map<String, int[]> importantMapping = new HashMap<>();
		importantMapping.put("높은 금리", new int[]{0, 0});
		importantMapping.put("해당 금융권에 대한 평판", new int[]{0, 1});
		importantMapping.put("우대조건", new int[]{0, 2});


		Map<String, int[]> purposeMapping = new HashMap<>();
		purposeMapping.put("생활비 자금", new int[]{1, 0});
		purposeMapping.put("결혼 자금", new int[]{1, 1});
		purposeMapping.put("여행 자금", new int[]{1, 2});
		purposeMapping.put("투자 자금", new int[]{1, 3});
		purposeMapping.put("취미활동을 위한 자금", new int[]{1, 4});
		purposeMapping.put("원하는 물건 구매 자금", new int[]{1, 5});

		Map<String, int[]> statusMapping = new HashMap<>();
		statusMapping.put("환경", new int[]{2, 0});
		statusMapping.put("직장인", new int[]{2, 1});
		statusMapping.put("청년", new int[]{2, 2});
		statusMapping.put("퇴직", new int[]{2, 3});
		statusMapping.put("스포츠", new int[]{2, 4});
		statusMapping.put("사회적 약자", new int[]{2, 5});
		statusMapping.put("군인", new int[]{2, 6});
		statusMapping.put("가족", new int[]{2, 7});
		statusMapping.put("대학생", new int[]{2, 8});
		statusMapping.put("학생", new int[]{2, 9});

		Map<String, int[]> ageMapping = new HashMap<>();
		ageMapping.put("20대", new int[]{3, 0});
		ageMapping.put("30대", new int[]{3, 1});
		ageMapping.put("40대이상", new int[]{3, 2});

		Map<String, int[]> genderMapping = new HashMap<>();
		genderMapping.put("남성", new int[]{4, 0});
		genderMapping.put("여성", new int[]{4, 1});

		if (importantMapping.containsKey(member.getImportant())){
			int [] position = importantMapping.get(member.getImportant());
			listData[position[0]][position[1]] = 1;
		}

		if (purposeMapping.containsKey(member.getPurpose())){
			int [] position = purposeMapping.get(member.getPurpose());
			listData[position[0]][position[1]] = 1;
		}

		if (statusMapping.containsKey(member.getStatus())){
			int [] position = statusMapping.get(member.getStatus());
			listData[position[0]][position[1]] = 1;
		}

		if (ageMapping.containsKey(member.getAge())){
			int [] position = ageMapping.get(member.getAge());
			listData[position[0]][position[1]] = 1;
		}

		if (genderMapping.containsKey(member.getSex())){
			int [] position = genderMapping.get(member.getSex());
			listData[position[0]][position[1]] = 1;
		}

		List<Double> vectorA = convertRowToList(listData, 0);
		List<Double> vectorB = convertRowToList(listData, 1);
		List<Double> vectorC = convertRowToList(listData, 2);
		List<Double> vectorD = convertRowToList(listData, 3);
		List<Double> vectorE = convertRowToList(listData, 4);


		List<Member> memberList = memberService.findAll();
		double maxCosine = 0.0;
		String id = "abc";

		for (Member member1 : memberList) {

			int[][] testData = {
				{0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0},
				{0, 0}
			};
			if(member1.getId().equals(member.getId())){
				continue;
			}
			if (importantMapping.containsKey(member1.getImportant())){
				int [] position = importantMapping.get(member1.getImportant());
				testData[position[0]][position[1]] = 1;
			}

			if (purposeMapping.containsKey(member1.getPurpose())){
				int [] position = purposeMapping.get(member1.getPurpose());
				testData[position[0]][position[1]] = 1;
			}

			if (statusMapping.containsKey(member1.getStatus())){
				int [] position = statusMapping.get(member1.getStatus());
				testData[position[0]][position[1]] = 1;
			}

			if (ageMapping.containsKey(member1.getAge())){
				int [] position = ageMapping.get(member1.getAge());
				testData[position[0]][position[1]] = 1;
			}

			if (genderMapping.containsKey(member1.getSex())){
				int [] position = genderMapping.get(member1.getSex());
				testData[position[0]][position[1]] = 1;
			}

			List<Double> memberA = convertRowToList(testData, 0);
			List<Double> memberB = convertRowToList(testData, 1);
			List<Double> memberC = convertRowToList(testData, 2);
			List<Double> memberD = convertRowToList(testData, 3);
			List<Double> memberE = convertRowToList(testData, 4);

			double a = cosineSimilarity(vectorA,memberA) + cosineSimilarity(vectorB,memberB)
				+ cosineSimilarity(vectorC, memberC) + cosineSimilarity(vectorD, memberD)
				+ cosineSimilarity(vectorE, memberE);

			if (a / 5.0 > maxCosine){
				maxCosine = a / 5.0;
				id = member1.getLogin_id();
			}

		}
		Member similarMember = memberService.findMember(id);
		List<FavoriteInstall> favoriteInstalls = similarMember.getFavoriteInstalls();
		List<FavoriteDeposit> favoriteDeposits = similarMember.getFavoriteDeposits();

		List<Install> installs = new ArrayList<>();
		for (FavoriteInstall favoriteInstall : favoriteInstalls) {
			Install install = favoriteInstall.getInstall();
			installs.add(install);
		}
		List<Deposit> deposits = new ArrayList<>();
		for (FavoriteDeposit favoriteDeposit : favoriteDeposits) {
			Deposit deposit = favoriteDeposit.getDeposit();
			deposits.add(deposit);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("favoriteInstalls", installs);
		response.put("favoriteDeposits", deposits);

		return response;




	}

	public static double cosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
		if (vectorA.size() != vectorB.size()) {
			throw new IllegalArgumentException("두 벡터의 크기는 같아야 합니다.");
		}

		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (int i = 0; i < vectorA.size(); i++) {
			dotProduct += vectorA.get(i) * vectorB.get(i); // 벡터 내적
			normA += Math.pow(vectorA.get(i), 2); // 벡터 A의 길이 제곱
			normB += Math.pow(vectorB.get(i), 2); // 벡터 B의 길이 제곱
		}

		if (normA == 0 || normB == 0) {
			return 0.0; // 벡터의 길이가 0인 경우 유사도는 0
		}

		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)); // 코사인 유사도 계산
	}

	public static List<Double> convertRowToList(int[][] data, int rowIndex) {
		List<Double> list = new ArrayList<>();
		for (int value : data[rowIndex]) {
			list.add((double) value); // int 값을 Double로 변환하여 리스트에 추가
		}
		return list;
	}
}
