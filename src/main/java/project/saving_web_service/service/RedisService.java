package project.saving_web_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.saving_web_service.domain.Deposit;
import project.saving_web_service.domain.Install;
import project.saving_web_service.repository.DepositRepository;
import project.saving_web_service.repository.InstallmentRepository;

@Service
@RequiredArgsConstructor
public class RedisService {

	private static final String CATEGORY_VIEW_ITEMS = "%s:%s:view_items";
	private static final long SECONDS_IN_A_Day = 24 * 60 * 60;

	private final RedisTemplate<String, String> redisTemplate;

	private final InstallmentRepository installmentRepository;
	private final DepositRepository depositRepository;
	public void addItemRecentlySaved(String age, String sex, Long InstallId){
		String key = age + ":" + sex;
		Install install = installmentRepository.findbyId(InstallId);
		String InstallName = install.get금융회사명() + "-" + install.get상품명();
		redisTemplate.opsForZSet().incrementScore(key,InstallName,1);
	}
	public void addItemRecentlySaved(String age, String sex, Long DepositId, String a){
		String key = age + ":" + sex;
		Deposit deposit = depositRepository.findbyId(DepositId);
		String DepositName = deposit.get금융회사명() + "-" + deposit.get상품명();
		redisTemplate.opsForZSet().incrementScore(key,DepositName,1);
	}


	public String viewedKey(String age, String sex){
		return String.format(CATEGORY_VIEW_ITEMS, age, sex);
	}

	public Set<String> viewedData(String age, String sex, int count){
		String key = age + ":" + sex;
		return redisTemplate.opsForZSet().reverseRange(key,0,count-1);
	}

	public Set<String> viewedData(String age, String sex){
		String key = age + ":" + sex;
		return redisTemplate.opsForZSet().reverseRange(key,0,0);
	}

	public boolean isRedisAvailable() {
		try {
			return redisTemplate.getConnectionFactory().getConnection().ping().equals("PONG");
		} catch (Exception e) {
			return false;
		}
	}


}
