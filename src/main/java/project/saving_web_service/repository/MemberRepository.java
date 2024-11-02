package project.saving_web_service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.saving_web_service.domain.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;


    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(String login_id) {

        try {
            return em.createQuery("select m from Member m where m.login_id = :login_id", Member.class)
                    .setParameter("login_id", login_id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Member findByRealId(Long id) {
        return em.find(Member.class,id);
    }
}