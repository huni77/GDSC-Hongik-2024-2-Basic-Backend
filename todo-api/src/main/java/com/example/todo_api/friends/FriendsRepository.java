package com.example.todo_api.friends;

import com.example.todo_api.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FriendsRepository {

    @PersistenceContext
    private EntityManager em;

    // Create
    @Transactional
    public void save(Friends friends) {
        em.persist(friends);
    }

    // Read by ID
    public Friends findById(Long friendId) {
        return em.find(Friends.class, friendId);
    }

    // Read all
    public List<Friends> findAll() {
        return em.createQuery("select f from Friends as f", Friends.class).getResultList();
    }

    // Find by Member
    public List<Friends> findAllByMember(Member member) {
        return em.createQuery("select f from Friends as f where f.member = :member", Friends.class)
                .setParameter("member", member)
                .getResultList();
    }

    // Delete
    @Transactional
    public void deleteById(Long friendId) {
        Friends friends = findById(friendId);
        if (friends != null) {
            em.remove(friends);
        }
    }

    // 테스트 용도 로만 사용
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
