package com.example.todo_api.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_login")
    private final String login;

    // 생성자
    public Member(String login) {
        this.login = login;
    }
}
