package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    public void createTodoTest() throws Exception {
        // given (테스트를 실행할 때 필요한 기본 환경 세팅)
        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());

        // when (테스트 하려는 동작)
        todoService.createTodo("1972",1L);

        // then (기대 결과)
        verify(todoRepository,times(1)).save(any(Todo.class));

    }

    @Test
    public void createTodoTest_When_MemberIdDoesNotExist() throws Exception {
        //given
        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(null);

        //when & then
        // 멤버가 존재하지 않는다는 에러가 발생하길 기대
        Assertions.assertThatThrownBy(()->{
            todoService.createTodo("content",1234567L);
        }).hasMessageContaining("존재하지 않는 멤버입니다.").isInstanceOf(Exception.class);

    }


}
