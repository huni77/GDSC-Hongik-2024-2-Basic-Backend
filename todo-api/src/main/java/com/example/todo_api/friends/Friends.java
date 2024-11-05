package com.example.todo_api.friends;

import com.example.todo_api.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Friends {

    @Id // Private Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 생성 시 마다 id 값 1씩 증가
    @Column(name = "friend_id",columnDefinition = "bigint")
    private Long id;

    // 외래키 생성
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Friends(Member member) { this.member = member; }

}
