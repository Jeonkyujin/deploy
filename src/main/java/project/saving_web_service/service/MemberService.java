package project.saving_web_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.saving_web_service.domain.Member;
import project.saving_web_service.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }
    public Member findMember(String login_id){
      return memberRepository.findById(login_id);
    };
    @Transactional
    public void updateMember(Member member) {
        // findByLoginId로 기존 엔티티를 가져온 후에 업데이트하는 방식
        Member existingMember = memberRepository.findById(member.getLogin_id());
        if (existingMember != null) {
            existingMember.setPassword(member.getPassword());
            existingMember.setStatus(member.getStatus());
            existingMember.setInterest(member.getInterest());
            existingMember.setCommodity_existence(member.getCommodity_existence());
            existingMember.setCommodity(member.getCommodity());
            existingMember.setPeriod(member.getPeriod());
            existingMember.setImportant(member.getImportant());

            memberRepository.save(existingMember); // 업데이트된 엔티티 저장
        } else {
            throw new RuntimeException("Member not found with login_id: " + member.getLogin_id());
        }
    }

}