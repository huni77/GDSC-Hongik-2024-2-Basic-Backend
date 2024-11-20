# 유효성검사를 하는 이유

-500에러는 문제의 원인이 클라이언트가 아닌 서버에 있다는 뜻을 나타내기도 한다.
따라서 이것만 봐서는 문제가 클라이언트에 있는지 서버에 있는지 명확하게 알기 어렵다.

- 클라이언트가 원인인 경우에는 4xx 상태코드를 명시적으로 응답한다.
- 따라서 이런 경우에는 400과 같은 상태코드를 응답하는 것이 더 좋다.

- 또한 정책을 위반한 데이터라는 것은 어플리케이션에서도 알 수 있음에도,
  `데이터베이스까지 요청을 보내서 예외를 확인` 하므로 시간, 자원의 낭비가
  발생한다.

# 유효성 검사

요청으로 들어오는 데이터가 올바른 형식인지 검사하는 것
스프링에서는 데이터를 받아들이는 DTO에서 유효성을 검사한다.
(유효성 검사는 ‘형식’ 만 검사한다. 존재하지 않는 멤버 아이디와 같은 경우는 유효성 검사로 체크할
수 없다.)

# Global Exception Handler

- 스프링은 예외 종류에 따라 응답할 response를 설정할 수 있는
  Global Exception Handler를 제공한다.
- 이름 그대로, 스프링 어플리케이션 전역에서 발생하는
  모든 에러에 대해 어떻게 처리할 지 결정한다.

# AOP

Aspect-Oriented Programming (관점 지향 프로그래밍)
객체지향 프로그래밍을 보완하는 개념

> Aspect : 여러 클래스에서 공통적으로 갖는 관심사
> Joint Point : 프로그램의 실행 중 발생하는 교차점
> Advice : 특정 joint point 에서 실행하는 action