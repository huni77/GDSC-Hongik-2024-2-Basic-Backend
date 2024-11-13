package com.example.todo_api.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {
    private Long memberId;
    private String content;
}
