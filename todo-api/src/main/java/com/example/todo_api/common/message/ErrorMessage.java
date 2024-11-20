package com.example.todo_api.common.message;

public class ErrorMessage {
    public static final String MEMBER_NOT_EXISTS = "존재하지 않는 멤버입니다.";
    public static final String TODO_NOT_EXISTS = "존재하지 않는 할 일 입니다.";
    public static final String MEMBER_ID_MUST_BE_NOT_NULL = "멤버 ID 는 반드시 작성해야 합니다.";
    public static final String CONTENTS_LENGTH_EXCEEDED = "content의 길이는 200자를 넘을 수 없습니다.";
    public static final String CANNOT_UPDATE_OTHERS_TODO = "다른 유저의 할 일을 수정할 수 없습니다.";
}
