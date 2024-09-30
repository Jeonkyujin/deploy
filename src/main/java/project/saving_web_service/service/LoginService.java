package project.saving_web_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public String loginCheck(String login_id, String password){
        Member member = memberRepository.findById(login_id);

        if(member == null || !member.getPassword().equals(password)){
            return null;
        }
        else {
            return member.getLogin_id();
        }
    }

}