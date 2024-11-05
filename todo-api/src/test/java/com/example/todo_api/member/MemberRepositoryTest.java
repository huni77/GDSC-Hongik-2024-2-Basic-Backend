package com.example.todo_api.member;

import com.example.todo_api.todo.Todo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void memberSaveTest() {
        Member member = new Member("mem1");
        memberRepository.save(member);

        org.assertj.core.api.Assertions.assertThat(member.getId()).isNotNull();
    }

    @Test
    @Transactional
    void memberFindByIdTest() {
        Member member = new Member("mem1");
        memberRepository.save(member);
        memberRepository.flushAndClear();

        Member findMember = memberRepository.findById(member.getId());
        Assertions.assertEquals(member.getId(), findMember.getId());
    }

    @Test
    @Transactional
    void memberFindAllTest() {
        Member member1 = new Member("mem1");
        Member member2 = new Member("mem2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> memberList = memberRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(memberList).hasSize(2);
    }

    @Test
    @Transactional
    @Rollback(false)
    void memberDeleteTest() {
        Member member = new Member("mem1");
        memberRepository.save(member);
        memberRepository.flushAndClear();

        memberRepository.deleteById(member.getId());
        Member deletedMember = memberRepository.findById(member.getId());
        Assertions.assertNull(deletedMember);
    }

    // Optional: 테스트가 끝날 때까지 애플리케이션을 유지하는 메서드
    @AfterAll
    public static void doNotFinish() {
        System.out.println("test finished");
        while (true) {}
    }
}
