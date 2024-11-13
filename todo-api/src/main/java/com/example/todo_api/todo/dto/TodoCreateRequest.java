package com.example.todo_api.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoCreateRequest {
    @Length(max = 200, message = "content의 길이는 200자를 넘을 수 없습니다.")
    private String content;

    @NotNull(message = "멤버 ID 는 반드시 작성해야 합니다.")
    private Long memberId;
}
