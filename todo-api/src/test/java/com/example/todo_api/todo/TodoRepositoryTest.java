package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.sound.midi.MetaMessage;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void  todoSaveTest() {
        // 트랜잭션의 시작
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);

        // 트랜잭션 종료 => 커밋
        // 에러가 발생했을 때 자동으로 롤백
    }

    @Test
    @Transactional
    void todoFindOneByIdTest() {
        // given: 기본 테스트 환경
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);

        todoRepository.flushAndClear();

        // when: 테스트 하고 싶은 행위
        Todo findTodo = todoRepository.findById(todo.getId());

        // then: 행위에 따른 결과
        Assertions.assertEquals(todo.getId(),findTodo.getId());
    }

    @Test
    @Transactional
    void todoFindAllTest(){
        Todo todo1 = new Todo("todo content1", false, null);
        Todo todo2 = new Todo("todo content2", false, null);
        Todo todo3 = new Todo("todo content3", false, null);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> todoList =  todoRepository.findAll();

        org.assertj.core.api.Assertions.assertThat(todoList).hasSize(3);
    }

    @Test
    @Transactional
    void todoFindAllByMemeberTest(){
        Member member1 = new Member("mem1");
        Member member2 = new Member("mem2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Todo todo1 = new Todo("todo content1", false, member1);
        Todo todo2 = new Todo("todo content2", false, member1);
        Todo todo3 = new Todo("todo content3", false, member2);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> member1TodoList =  todoRepository.findAllByMember(member1);
        List<Todo> member2TodoList =  todoRepository.findAllByMember(member2);

        org.assertj.core.api.Assertions.assertThat(member1TodoList).hasSize(2);
        org.assertj.core.api.Assertions.assertThat(member2TodoList).hasSize(1);

    }
    @Test
    @Transactional
    @Rollback(value = false)
    void todoUpdateTest() {
        Todo todo1 = new Todo("todo content1", false, null);
        todoRepository.save(todo1);

        todoRepository.flushAndClear();

        Todo findTodo1 =  todoRepository.findById(todo1.getId());
        findTodo1.updateContent("new Content");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void todoDeleteTest() {
        Todo todo1 = new Todo("todo content1", false, null);
        Todo todo2 = new Todo("todo content2", false, null);
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        todoRepository.flushAndClear();

        todoRepository.deleteById(todo1.getId());
    }


    // in memory database
    // 모든 테스트가 끝났을 때 호출
    @AfterAll
    public static void doNotFinish() {
        System.out.println("test finished");
        while (true){}
    }
}
