# Spring Boot
## MVC Pattern
View - Controller - Model

## Rest API

- Test Site : jsonplaceholder.typicode.com
- Tool : Talend API TEST를 통해 연습

### client -> server <Request line, header, body>

### server -> client <status line, header, body>

- status code
  - Successful : 200(OK), 201(Created)
  - Client Error : 404(Not found)
  - Server Error : 500(Internal Server Error)

- URL 주소 설계 Client(JSON) <-> Server(RestAPI)
  - GET : 조회 /api/articles, /api/articles/{id}
  - POST : 생성 /api/articles
  - PATCH : 수정 /api/articles/{id}
  - DELETE : 삭제 /api/articles/{id}


#### RestController와 일반 Controller의 차이 = 반환타입
- 일반 Controller는 view page 반환
- Rest Controller는 일반적으로 Data(json, text..) 반환 


## 서비스 계층과 트랜잭션
- JSON/Client : 손님
- Controller : 웨이터
- Service : 주방장
- Repository : 보조 주방장


## TEST QA
1. 예상 시나리오 작성
   - 테스트 케이스는 크게 성공/실패로 나누어 진행
   - 조건에 따라 다양한 경우로 작성
2. 실제 코드결과와 비교하여 검증
   - 성공 시 리팩토링
   - 실패 시 디버깅을 통한 수정

### TDD
Test code -> 통과하는 최소한의 코드로 시작-> 점진적 개선 및 확장
1. Write a test that fails
2. Make the code work
3. Eliminate redundancy

### Article - Comment
#### comment Entity


## DTO(데이터 전달)와 VO(값 표현) ref : [10분테크톡](https://www.youtube.com/watch?v=z5fUkck_RZM&list=WL&index=2)
### DTO(Data Transfer Object): 데이터를 전달하기 위해 사용되는 객체
- 계층 간 데이터를 전달하기 위한 객체 ex) Controller(Web Layer) <- DTO -> Service(Service Layer)
- 오직 getter/setter 메소드만 가짐, 다른 로직을 갖지 않음 => 순수하게 데이터 전달을 위해 사용되므로
- 속성값(value)이 모두 같다고 해서 같은 객체가 아님
- setter 존재 시 가변, 비 존재시 불변

### VO(Value Object): 값 그 자체를 표현하는 객체
- VO는 값 자체를 표현하므로 불변객체, setter 성격의 메소드를 포함하면 안되며 생성자를 통해서만 값을 초기화
- getter/setter외에 로직을 가질 수 있음
- 속성값(value)이 같으면 같은 객체
- VO 비교 방식
  - (참고) Hash(Set, Map, table)의 동등 비교방식은 1) hashCode() 리턴값 비교 2) equals() 리턴 값 비교를 통해 동등객체 확인
  - 완전한 VO로 만들기 위해서는, equals메소드, hashCode 메소드 모두 오버라이딩 해야함 (value 끼리만 비교하도록)