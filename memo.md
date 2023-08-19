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
