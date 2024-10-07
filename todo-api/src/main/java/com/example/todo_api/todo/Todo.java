package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Todo {

    @Id
    // 생성할 때 마다 id 값 1씩 증가
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "todo_content",columnDefinition = "varchar(200)")
    private String content;

    @Column(name = "todo_is_checked",columnDefinition = "tinyint(1)")
    private  boolean isChecked;

    // 외래키 생성하기
    // 외래키임을 명시
    @JoinColumn(name = "member_id")
    // 다 대 일 연관관계임을 명시
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 생성자 함수
    public Todo(String content, boolean isChecked, Member member) {
        this.content = content;
        this.isChecked = isChecked;
        this.member = member;
    }
}
