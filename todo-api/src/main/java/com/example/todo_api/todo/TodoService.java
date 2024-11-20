package com.example.todo_api.todo;

import com.example.todo_api.common.exeption.BadRequestException;
import com.example.todo_api.common.message.ErrorMessage;
import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    // Create
    // 할 일 생성
    @Transactional
    public Long createTodo(String content, Long memberId) throws Exception{
        Member member = memberRepository.findById(memberId);

        // 예외처리
        if (member == null){
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXISTS);
        }

        Todo todo = new Todo(content, member);
        todoRepository.save(todo);
        return todo.getId();
    }

    // Read
    @Transactional(readOnly = true) // 읽기 전용
    public List<Todo> getTodoList(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);

        // 예외처리
        if (member == null){
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXISTS);
        }

        return todoRepository.findAllByMember(member);
    }

    // Update
    @Transactional
    public void updateTodo(Long todoId,String newContent, Long memberId) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        Member member = memberRepository.findById(memberId);

        // 예외처리
        if (todo == null){
            throw new BadRequestException(ErrorMessage.TODO_NOT_EXISTS);
        }

        if (member == null){
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXISTS);
        }

        if (todo.getMember() != member) {
            throw new BadRequestException(ErrorMessage.CANNOT_UPDATE_OTHERS_TODO);
        }

        todo.updateContent(newContent);
    }
    // Delete
    @Transactional
    public void deleteTodo(Long todoId) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        if (todo == null) {
            throw new BadRequestException(ErrorMessage.TODO_NOT_EXISTS);
        }
        todoRepository.deleteById(todoId);
    }
}
