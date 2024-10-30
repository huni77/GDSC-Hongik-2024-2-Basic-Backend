package com.example.todo_api.member;

import com.example.todo_api.todo.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    // Create
    public void save(Member member){
        em.persist(member);
    }

    // Read
    // 조회
    // 단건 조회 한개의 데이터를 조회
    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    // 다건 조회, 여러개의 데이터 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }

    // Delete
    public void deleteById(Long memberId) {
        Member member =  findById(memberId);
        em.remove(member);
    }

    // 테스트 용도 로만 사용
    public void flushAndClear() {
        em.flush();
        em.clear();
    }

}
