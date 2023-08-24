# Spring Boot
## MVC Pattern
View - Controller - Model

## Rest API
### WWW 문제점
   - 각자 다른 프로토콜과 데이터 형식이 달라 클라이언트-서버간 결합도가 높음
   - 자원의 표현과 상태 관리가 어려움
     - e.g.) 회원정보생성 예시 :  1) /new-user 2) /new/user 등 표준이 부재 -> 높은 복잡도
     - 기존 웹은 정적자원 전송용으로, 한 페이지 안의 정보가 많아지고 복잡해져서, 자원관리가 어려워지다보니 작은 자원이 변경되더라도 페이지 전체를 다시 요청해야하는 문제 존재
   => 기존 웹을 손상시키지 않으면서(HTTP 호환성을 유지하면서) 웹을 보다 잘 사용할 수 있는 방법을 고민하여 REST by Roy fielding

### REpresentational State Transfer
- definition : 분산 하이퍼미디어 시스템을 위한 아키텍처 스타일
  - 대표적인 분산 하이퍼미디어 시스템 : 웹
  - 아키텍쳐 스타일은 제약조건의 집합을 의미
  => REST는 웹을 위한 제약조건의 집합
  
1. URI를 통해 자원을 지정(이름으로 구분)
    - Uniform Resource Identifiers
      - REST에서 자원을 구분하고 처리하기 위해 사용
      - URI를 잘 네이밍할할 수록 API가 직관적이고 사용하기 쉽다
      - e.g. /books, /customers
    - Singleton and Collection Resources
      - URI는 singlton이나 collection 표현
      - /customer (singleton resource) //단수
      - /customers (collection resource) //복수
    - Collection and Sub-collection Resources
      - URI는 서브 컬렉션을 포함할 수 있음
      - 특정 고객의 계좌를 찾는 API를 찾는다면 아래와 같이 표현 가능
      - e.g. /customers/{customerId}/acounts
    - URI 네이밍 규칙, 개발자들의 공용어처럼 사용하기 위해
      - 명사를 사용해서 자원들 표현 e.g. /crews : 크루들의 정보
        - 예외적으로 동사 허용하는 경우(controller) 
          - e.g. /game/play에 접근시 게임이 시작된다면, 게임의 시작 여부를 컨트롤하는 URI이므로 동사 play로 표현
      - 자원 간 계층관게 표현을 위해 /(슬래시) 사용 e.g. /crews/frontend, /products/5
        - URI 경로 마지막에는 / (슬래시)를 붙이지 않는단.
    - -(하이픈) 기호를 사용하여 URI의 가독성을 향상 시킬 수 있음
      - e.g. /profilemanagement vs /profile-management
    - URI에는 가급적 밑줄을 사용하지 않는다.
      - 일부 브라우저나 화면에서 글꼴에 따라 (_) 문자가 가려지거나 숨겨질 수 있음
        - /frontend_crews (X), /crews/frontend (O)
    - URI에는 소문자를 사용한다
      - /CREWS (x), /PLAYERS (x)
      - /crews (o), /players (o)
    - URI에 파일 확장자를 표시하지 않는다. e.g. /index.do (X)
    - URI에 CRUD 함수의 이름을 사용하지 않는다.
      - /create/crews (X)
      - /add/crews (X)
      - /read/crewss (X)
      - /update/crews (X)
      - /updateCrews (X)
    - 자원의 필터링을 위해 새로운 API를 만들지 않는다.
      - 필터링을 위해 새로운 API를 만들지 않고 Query String을 이용
        - Query String e.g. address?attribute=value&attribute2=value&...
      - 특정 주소로 접근할 대 페이지에 대한 옵션으로 활용
      - 프론트엔트 크루를 이름, 오름차순으로 보고 싶다면 아래와 같이 활용
        - /crews/frontendAndSortByNameAscending (X)
        - /crews?type=frontend&sort=name,asc (O)
      
2. HTTP 메서드 : 자원에 대한 행위 표현 (자원의 상태를 주고 받는다.)
    - HTTP 메서드 <-> HTTP 상태코드
      - e.g. GET, POST, PUT, PATCH, DELETE, ... + data <-> 200, 201, 404, ... + data
#### HTTP METHOD : 자원의 상태를 주고 받기 위해 사용하는 메서드
- GET : 자원을 검색할 때 사용 (READ)
        - /crews (O)  /getCrews (x)
        - /crews/5 (O) /getCrewId5 (x)
- POST : 자원을 생성할 때 사용 (CREATE)
        - register_crew (x)
        - /crews  body:{"name":"이름","type":"종류"}
- PUT  : 자원을 업데이트할 때 사용, 보내지 않은 정보는 null값으로 업데이트 (UPDATE)
        - /crews/1 body:{"type","종류"}  => name : null, type : 종류 
- PATCH : 자원을 업데이트할 때 사용, 보내지 않은 데이터는 기존 데이터를 유지 (UPDATE)
        - /crews/1 body:{"type":"종류2"} => name : null, type : 종류2
- DELETE : 자원을 삭제할 때 사용 
        - -/crews/1

#### HTTP 상태코드
- 1xx : 조건부 응답
- 2xx : 성공
- 3xx : 리다이렉션
- 4xx : 클라이언트 오류
- 5xx : 서버 오류

### API 설계의 방향성
#### REST API여야 하는가?
- Uniform Interface 제약조건은 비효율적
- 애플리케이션에 필요한 정보가 아니라, 표준화된 형식으로 데이터 전달
- 상황에 따라 최적이 아닐 수 있음

1. Real REST API (aka. RESTful API)
   - (읽어볼 것1) 그런 REST API로 괜찮은가 - 이용준 개발자 youtube 보기
   - (읽어볼 것2) Roy fielding, Architectural Styles and the Design of Network-based Software Architectures - Chapter 5, 6 읽기
     - INDEX: https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm
     - CHAPTER 5: https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm
     - CHAPTER 6: https://www.ics.uci.edu/~fielding/pubs/dissertation/evaluation.htm
   - (읽어볼 것3) RESTful web API design from MICROSOFT
   - (읽어볼 것4) Spring HATEOAS, https://spring.io/projects/spring-hateoas
2. REST style API (aka. HTTP API)
   - REST 제약조건 부분적으로 준수, e.g. URI을 통해 자원 식별
   - HTTP 스펙 적절하게 활용
   - API 설계에 관한 컨벤션 참고
   - (읽어볼 것1) https://github.com/Microsoft/api-guidelines/blob/master/Guidelines.md
3. 다른 API 표준 선택하기(e.g., GraphQL API)
   - URI를 통한 자원 식별 제약 조건 위배
   - /graphql
   - (읽어볼 것) https://spring.io/projects/spring-graphql
   - (읽어볼 것) https://graphql.org/

### RESTful API, REST의 제약조건 6가지 - Restful 하게 자원을 명시하고 주고 받는 방법
1. 클라이언트 - 서버 구조
    - 클라이언트는 요청을 발생시키고, 서버는 요청에 대해 반응함, 
      - 다시 말하면 서버는 데이터만 전달하면 끝이며, 클라이언트는 데이터를 받아 원하는 UI로 구성
      - 클라이언트 -> UI의 이식성 집중, 서버 -> 확장성(scalability)에만 집중
2. 무상태성
    - 요청은 상태를 가지지 않는다는 제약조건, 즉 각각의 요청은 독립적이고 필요한 모든 정보를 제공해야함
      - 쉽게 말해, 서버는 클라이언트가 이전에 무슨 요청을 보냈는지 모름
        - 1) (Client) ID : HONG의 장바구니를 줘 -> (Server) OK, {1번:고기, 2번:피자...}
        - 2) (Client) 장바구니 정보 줘 -> (Server) 누구의 정보를?
3. 캐시가능성
    - 서버는 자원이 캐시 가능한지 명시해야 한다는 제약 조건
      - 1) (Client) ID : HONG의 장바구니를 줘 -> (Server) OK, {1번:고기, 2번:피자...} 그리고 캐시 가능
      - 2) (Client) 장바구니 정보 줘 / 캐시가 되어있어서 요청하지 않아도 됨.
4. 계층형 시스템
    - 계층형 시스템을 적용해야 한다는 제약조건
      - (Client) - A+B+C -> (Server)라고 한다면,
      - (Client) - A+B+C -> (Auth Server) - B+C -> (API Server) - C -> (DB Server)
5. 주문형 코드(optional)
   - 클라이언트가 '필요에 의해' 기능을 확장할 수 있도록 해야한다는 제약조건
     - (Client) - 앞으로 A라는 기능이 필요할 것 같음 -> (Server) - A를 실행할 수 있는 파일(코드) 전달 -> (Client)
     - 이후부터는, Client가 기존에 요청받은 파일(코드)를 실행하기만 하면됨
   - 시스템 전체적으로 적용되었을 때 성능이 안좋아질 수도 있으므로, Optional
6. 균일한 인터페이스
   - 개발을 할 때 클라이언트와 맞닿아 있는 부분(Http Request, Http Response 등)을 쉽고 일반적으로 설계하라는 제약조건
   - 균일한 인터페이스는 또 한번 하위의 4가지 제약조건(Uniform Interface)으로 분류
     1. 자원의 식별
         - 이름을 지닐 수 있는 모든 정보
         - 개념적인 대상 ex) 문서, 이미지, 자원들의 집합, 실존하는 대상 등
         - 자원은 객체이며, 상태는 변화가능하나 언제나 변하지 않는 고유 식별자 필요
         - 고유한 식별자로 URI를 사용해야 한다. ex) /user/1
     2. 표현을 통한 자원의 조작
         - 표현 : 메타데이터 + 데이터이이면서, 특정한 상태의 자원에 대한 표현 
         - 자원은 다양한 방식으로 표현 가능함 ex) 문서, 파일, HTTP 메시지 엔티티 등
         - 자원에 대한 표현을 클라이언트-서버가 전송하는 것, REpresentational State Transfer
     3. 자기 서술적인 메시지
         - 메시지는 스스로에 대해 설명해야함, 클라이언트-서버에 오가는 메시지는 그 자체에 대해 스스로 설명되어 메시지를 처리하기에 충분한 정보가 제공되어야함
         - 요청/응답 메시지들은 컴포넌트/중개자들에게 자신을 어떻게 처리해야하는지 제대로 설명해야함
         - Host header에 도메인명 기재 필요, HTTP 1.1 버전부터는, Host 헤더필드가 없는 요청은 리젝
         - 도메인명 정보의 필요성 : 하나의 IP주소에 복수 도메인명이 존재할 수 있으므로, IP주소만으로는 요청 대상을 찾을 수 없음
         - 캐쉬 관련 헤더를 통한 캐쉬 전략 지정 (HTTP/1.1 Cache-Control, Age, Etag, Vary 등 헤더들을 메시지에 포함시켜 캐쉬 관련 동작 커스터마이징 가능)
     4. HATEOAS(Hyper media as the engine of application state)
         - 클라이언트는 서버와 상호작용하면서 하이퍼링크를 통해 동적으로 모든 다른 리소스에 접근할 수 있어야 한다는 제약 조건
         - HTML의 경우 하이퍼미디어를 통한 앱 상태 변경 : Anchor tag를 활용, a 태그를 통해 다른 리소스에 접근 가능하며 클라이언트가 어플리케이션의 상태를 동적으로 변경할 수 있으므로 HATEOAS.
         - 홈페이지를 시작으로 숨겨진 페이지, 숨겨진 기능이 존재한다면 HATEOAS 위배
         - 일반적으로 front - back end 사이에서 JSON으로 구성하여 주고 받아, 위배 상황이 많으나, JSON에 서버에 보낼 수 있는 요청 정보인 URI 등을 포함하여 클라이언트에 전달하면 위배하지 않는다고 주장
=> 실제로 대부분의 RESTful API라고 불리는 것들이 완전히 REST를 따르지 않는 경우가 많음
=> 엄연히 따지면 HTTP API라고 불러야함
=> REST의 핵심은 클라이언트와 서버 간의 결합도를 낮춰 독립적인 진화를 할 수 있게 하는 것
ref)
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

