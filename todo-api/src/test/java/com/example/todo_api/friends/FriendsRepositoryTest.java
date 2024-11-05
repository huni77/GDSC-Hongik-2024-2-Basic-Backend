package com.example.todo_api.friends;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FriendsRepositoryTest {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void friendsSaveTest() {
        Member member1 = new Member("mem1");
        Member member2 = new Member("mem2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.flushAndClear();

        Friends friends = new Friends(member2);
        friendsRepository.save(friends);
    }

    @Test
    @Transactional
    void friendsFindByIdTest() {
        Member member = new Member("mem1");
        memberRepository.save(member);
        memberRepository.flushAndClear();

        Friends friends = new Friends(member);
        friendsRepository.save(friends);
        friendsRepository.flushAndClear();

        Friends foundFriends = friendsRepository.findById(friends.getId());
        Assertions.assertEquals(friends.getId(), foundFriends.getId());
    }

    @Test
    @Transactional
    void friendsFindAllTest() {
        Member member1 = new Member("mem1");
        Member member2 = new Member("mem2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Friends friends1 = new Friends(member1);
        Friends friends2 = new Friends(member2);
        friendsRepository.save(friends1);
        friendsRepository.save(friends2);
        friendsRepository.flushAndClear();

        List<Friends> friendsList = friendsRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(friendsList).hasSize(2);
    }

    @Test
    @Transactional
    void friendsFindAllByMemberTest() {
        Member member1 = new Member("mem1");
        Member member2 = new Member("mem2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Friends friends1 = new Friends(member1);
        Friends friends2 = new Friends(member1);
        Friends friends3 = new Friends(member2);
        friendsRepository.save(friends1);
        friendsRepository.save(friends2);
        friendsRepository.save(friends3);
        friendsRepository.flushAndClear();

        List<Friends> member1Friends = friendsRepository.findAllByMember(member1);
        List<Friends> member2Friends = friendsRepository.findAllByMember(member2);

        org.assertj.core.api.Assertions.assertThat(member1Friends).hasSize(2);
        org.assertj.core.api.Assertions.assertThat(member2Friends).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback(false)
    void friendsDeleteTest() {
        Member member = new Member("mem1");
        memberRepository.save(member);
        memberRepository.flushAndClear();

        Friends friends = new Friends(member);
        friendsRepository.save(friends);
        friendsRepository.flushAndClear();

        friendsRepository.deleteById(friends.getId());
        Friends deletedFriends = friendsRepository.findById(friends.getId());
        Assertions.assertNull(deletedFriends);
    }

    // Optional: 테스트가 끝날 때까지 애플리케이션을 유지하는 메서드
    @AfterAll
    public static void doNotFinish() {
        System.out.println("test finished");
        while (true) {}
    }
}
