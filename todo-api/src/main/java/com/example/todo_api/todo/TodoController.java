package com.example.todo_api.todo;

import com.example.todo_api.todo.dto.TodoCreateRequest;
import com.example.todo_api.todo.dto.TodoUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    // CREATE
    // 할 일 생성
    @PostMapping
    public ResponseEntity<Void> createTodo(@RequestBody @Valid TodoCreateRequest request) throws Exception {
        Long todoId = todoService.createTodo(request.getContent(),request.getMemberId());

        return ResponseEntity.created(URI.create("/todo/" + todoId)).build(); // 201 상태코드를 갖는 응답 생성, 생성된 데이터의 위치 같이 응답
    }

    // READ
    // 할 일 조회
    @GetMapping("/list")
    // @RequestMapping(method = RequestMethod.GET,path = "/list")
    public ResponseEntity<List<Todo>> getTodoList(@RequestBody Long memberId) throws Exception{
        List<Todo> todoList = todoService.getTodoList(memberId);
        return ResponseEntity.ok().body(todoList);
    }

    // Update
    // 할 일 수정
    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoUpdateRequest request) throws Exception {
        todoService.updateTodo(todoId, request.getUpdateContent(), request.getMemberId());
        return ResponseEntity.ok().build();
    }

    // DELETE
    // 할 일 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todoId") Long todoId) throws Exception{
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build(); // 204 no content 상태코드
    }

}
