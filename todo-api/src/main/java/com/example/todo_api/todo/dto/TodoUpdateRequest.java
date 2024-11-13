package com.example.todo_api.todo.dto;

import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private Long memberId;
    private String updateContent;
}
