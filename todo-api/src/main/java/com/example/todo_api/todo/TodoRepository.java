package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {

    @PersistenceContext
    private EntityManager em;

    // Create
    // 데이터 생성
    public void save(Todo todo) {
        em.persist(todo); // todo 객체를 영속성 컨텍스트에 등록
    }

    // Read
    // 조회
    // 단건 조회 한개의 데이터를 조회
    public Todo findById(Long todoId) {
        return em.find(Todo.class, todoId);
    }

    // 다건 조회, 여러개의 데이터 조회
    public List<Todo> findAll() {
        return em.createQuery("select t from Todo as t", Todo.class).getResultList();
    }

    // 조건 조회, 여러개의 데이터 중 조건에 맞는 데이터만 조회
    public List<Todo> findAllByMember(Member member) {
        return  em.createQuery("select t from Todo as t where t.member = :todo_member", Todo.class).setParameter("todo_member",member).getResultList();
    }


    // Update
    // 수정
    // 엔티티 클래스의 필드를 수정하는 메서드를 작성해서 수정하자


    // Delete
    // 삭제
    public void deleteById(Long todoId) {
        Todo todo =  findById(todoId);
        em.remove(todo);
    }

    // 테스트 용도 로만 사용
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
