# 웹

여러 컴퓨터가 서로 연결되어 정보를 공유하는 공간

# 클라이언트 - 서버 패러다임

클라이언트 : 데이터의 생성/조회/수정/삭제 요청을 전송
서버 : 요청대로 동작을 수행하고 응답을 전송

# 프로토콜과 HTTP

프로토콜 : 네트워크 안에서 요청과 응답을 보내는 규칙
웹에서는 HTTP 라는 프로토콜(규칙)을 사용한다.

## 자주 사용하는 HTTP Method

GET : 데이터를 가져온다. (조회)
POST : 데이터를 게시한다. (생성)
PUT : 데이터를 교체한다. (수정)
PATCH : 데이터를 수정한다. (수정)
DELETE : 데이터를 삭제한다. (삭제)

# URL 구조

## 일반적인 URL

http://www.example.com/user/1/nickname
프로토콜 / 서버 주소(domain) / 서버 내 데이터 위치 로 구성

## Path Parameter

http://www.example.com/user/%7Buser_id%7D/nickname

## Query String

.com/post/search?page=1&keyword=hello

? 이후부터 Query String

# 대표적인 HTTP 상태 코드

200 → 처리 성공 (ok)
201 → 데이터 생성 성공 (created)
400 → 클라이언트 요청 오류 (bad request)
404 → 요청 데이터 없음 (not found)
500 → 서버 에러 (internal server error)

# API(Application Programming Interface)

어플리케이션에서 원하는 기능을 수행하기 위해
어플리케이션과 소통하는 구체적인 방법을 정의한 것

쉽게 말하면 어플리케이션의 사용 설명서

# 과제 - API 명세서

Todo mate API 서버 클론 코딩

## 세부 기능

### 유저 회원가입 / 로그인

- 유저 회원가입
  POST /register
- 유저 로그인
  POST /login

### 로그인한 유저의 할 일 생성 / 조회 / 수정 / 삭제

- 할 일 생성
  POST /todo
- 조회
  GET /todo/list
- 수정
  PATCH /todo/{todo_id}
- 삭제
  DELETE /todo/{todo_id}

### 로그인한 유저의 할 일 체크 / 체크 해제

- 할 일 체크
  POST /todo/{todo_id}/check
- 체크 해제
  POST /todo/{todo_id}/uncheck

### 친구 추가 / 친구 조회 / 친구 삭제

- 추가
  POST /friends/{friend_id}

- 조회
  GET /friends/{friend_id}

- 삭제
  DELETE /friends/{friend_id}

### 특정 친구의 할 일 조회

GET /friends/{friend_id}/todo/list
