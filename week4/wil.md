# 3주차 (JPA, DB 설계)

# DB 설계(ER 모델, ERD 다이어그램)

## 개념 & 용어

우리가 일상생활에서 만나는 데이터베이스 문제 상황들은 크게 `개체` 와 그 개체들 사이의 `관계` 로 나타낼 수 있다.
문제 상황을 개체와 관계로 표현하는 모델을 ER(Entity-Relationship) 모델 이라고 한다. 이 모델을 다이어 그램으로 나타낸 것이 ERD 이다.

- 개체(Entity) : 문제 상황을 구성하는 요소
- 관계(Relationship) : 개체와 개체 사이의 관계
- 속성(Attribute) : 개체와 관계가 가지는 세부적인 속성(특성)
- PK(Primary Key) : 하나의 개체를 식별할 수 있는 속성

## 개체 사이의 관계들

### 다-대-일(Many-to-one) (N : 1)

### 일-대-다(one-to-many) (1 : N)

todo-api 관점에서 1명의 유저는 여러개의 할 일을 생성할 수 있다.
따라서 유저 - 할일 관계는 일-대-다 관계이다
보통 일-대-다 관계는 외래키로 표현한다.

### 일-대-일(one-to-one) (1 : 1)

### 다-대-다(many-to-many) (N : M)

유저는 여러 동아리에 속할 수 있다.
또한 동아리도 여러 유저를 가질 수 있다.
따라서 유저 - 동아리 관계는 다-대-다 이다.

보통 다-대-다 관계는 테이블로 구현한다.
유저-동아리 쌍을 저장하는 테이블을 만들면 된다.

## 식별, 비 식별 관계

관계를 설정할 때는 보통 비-식별 관계를 선택한다.
• 식별 관계 : 관계 대상의 PK를 자신의 PK로도 사용하는 것
• 비-식별 관계 : 관계 대상의 PK를 자신의 FK(외래키)로만 사용하는 것

# JPA 사용해보기

DB를 준비하고 JPA 로 테이블을 만들어보자
DB는 간단하게 사용할 수 있는 `H2 DB` 를 사용한다.

## JPA 의존성 추가

build.gradle에 JPA와 H2 데이터베이스 의존성을 추가한다.
의존성 정보가 바뀌면 반드시 gradle을 다시 로드하자

```java
dependencies {
    //=========================================================================
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    //=========================================================================
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

    //=========================================================================
	// H2 Datatbase
	implementation 'com.h2database:h2'
    //=========================================================================
}
```

## JPA DB 연결 정보 추가

resources/application.properties 파일에 DB 접속 정보를 작성한다.

1. 편의성을 위해 파일 확장자를 yml 로 바꾼다.

2. 다음과 같이 작성한다

```
spring:
  application:
    name: todo-api

  // 관리자 콘솔에 접속할 url, 설정하지 않으면 매번 랜덤 url 생성
  datasource:
    url: jdbc:h2:mem:todo;MODE=MYSQL

  // 관리자 콘솔 활성화, default : false
  h2:
    console:
      enabled: true

  jpa:
    // JPA가 생성한 SQL을 표시하고 보기 좋게 들여쓰기 사용
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect // sql 생성 시 MySQL 8 문법 사용
```

## 엔티티 클래스 만들기

패키지를 만들고 안에 클래스 파일 생성
엔티티 클래스는 테이블을, 클래스 필드는 컬럼을 나타낸다.

@Entity 어노테이션으로 이 클래스가 엔티티라는 것을 명시
@Id 어노테이션으로 PK 필드에 이 필드가 PK라는 것을 명시

id 값은 보통 데이터를 생성할 때마다 자동으로 1씩 늘어난다.
@GeneratedValue를 사용하면 id 값을 자동으로 생성한다.
이때 strategy는 IDENTITY로 설정한다. (키 값 결정을 DB에 위임)

ERD에서 설계했던 Column 이름과 타입을 맞추기 위해,
필드에 @Column 으로 이름과 타입을 명시한다

```java
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

}
```

## 엔티티 연관관계

### 외래키 만들기

외래키 컬럼을 나타낼 때는 Long 타입의 외래키 필드 대신,
해당 엔티티 타입의 엔티티 객체를 필드로 가지도록 설계한다.

외래키 필드에는 2가지 어노테이션을 지정해주어야 한다.

1. @JoinColumn

- FK 컬럼 정보를 명시하는 어노테이션 (컬럼 이름 등)
- 연관관계 종류를 나타내는 어노테이션에는 fetch 속성이 있다. 이 속성으로 연결된 엔티티를 언제 가져올지 명시할 수 있다.
- fetch type에는 EAGER, LAZY 2가지가 있는데, LAZY를 사용하자.
  - EAGER : 즉시 로딩, Todo 객체 정보를 가져올 때 연결된 User 객체의 모든 정보를 함께 한번에 가져온다.
  - LAZY : 지연 로딩, Todo 객체 정보를 가져올 때 연결된 User 객체의 정보는 필요할 때 가져온다

2. @ManyToOne @OneToOne @OneToMany @ManyToMany

- 해당 외래키로 생기는 연관관계 종류를 나타내는 어노테이션
- @ManyToMany 는 N : M 관계를 나타낸다. N : M 관계는 외래키대신 테이블로 구현하므로 사용하지 않는다.
- @OneToMany 는 1 : N 관계를 나타낸다. 1에 해당하는 엔티티에 N에 대한 연관관계를 명시하는 양방향 매핑에 사용된다. (이번 스터디에서는 단방향 매핑만 사용한다.)

```java
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Todo {

    .
    .
    .


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
```

### 만약 외래키를 직접 저장한다면?

만약 외래키를 직접 저장한다면 연관된 데이터가 필요할 때,
외래키로 데이터를 조회하는 코드를 직접 작성해야 한다.

엔티티로 저장하면 테이블을 만들 때 외래키를 만들어주고,
연관된 데이터가 필요할 때, 자동으로 join 쿼리가 실행되면서
연관된 데이터를 얻는다.

### 엔티티 생성자

엔티티 객체를 생성할 생성자를 만든다.
alt+insert키를 눌러 id를 제외한 필드에 대한 생성자를 추가한다.
(id 값은 자동으로 생성된다.)

엔티티 생성자는 보통 id 필드를 제외하고 만든다.
JPA는 엔티티 객체를 다룰 때 public 또는 protected의 인자 없는 생성자가 필요하다
@NoArgsConstructor를 사용하여 인자 없는 생성자를 만든다.

- 이때 access 속성을 통해 접근 제한자를 protected로 설정한다.
- 추가로 엔티티 객체에 @Getter를 추가해 모든 필드에 getter를
  만든다
