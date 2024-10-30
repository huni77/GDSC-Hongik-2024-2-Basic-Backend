package com.example.todo_api.member;

import com.example.todo_api.todo.Todo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void  memberSaveTest() {
        // 트랜잭션의 시작
        Member member = new Member("mem1");
        memberRepository.save(member);

        // 트랜잭션 종료 => 커밋
        // 에러가 발생했을 때 자동으로 롤백
    }

}
