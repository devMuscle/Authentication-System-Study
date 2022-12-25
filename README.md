# :snowflake:스마일게이트 윈터 데브 캠프 개인프로젝트:snowflake:
## 주제 : 인증 시스템:lock:
### 1. Requirment
* 가입, 로그인 페이지
* 유저 관리 페이지
* 인증 서버 API
* RDBMS DB 사용
* Password Encryption
* E-Mail 인증 (option)
* 비밀번호 찾기 (option)
* 캐시 (option)
#### Done
* 가입, 로그인 페이지(Backend)
* 유저 관리 페이지(Backend)
* 인증 서버 API(Backend)
* RDBMS DB 사용(Backend)
* Password Encryption(Backend)
#### Not Done
* Frontend
* E-Mail 인증 (option)
* 비밀번호 찾기 (option)
* 캐시 (option)

### 2. 구현방법
* jwt를 이용한 인증 토큰 발급
* HandlerInterceptor를 구현하여 Token 검증
* 어노테이션 인터페이스를 이용한 인가 (유저 전체 조회, 유저 강제 탈퇴는 ADMIN만 허용)
* MySQL 사용
* 로그인시 'SHA-256'알고리즘을 이용해 비밀번호 암호화

### 3. 코드 리뷰
#### 리뷰 받고 싶은 부분
* JwtInterceptor에서 Token관련 Exception마다 catch 를 해서 log를 남기고 response.sendError(401, "error message")코드를 작성했는데
모든 catch 경우마다 response.sendError(401, ) 코드는 모두 중복되다 보니 이 부분에서 조금 코드를 개선할 수 있을지 피드백을 받고 싶습니다.
* 전체적인 URL 설계가 Restful한지 피드백을 받고 싶습니다.\
(특히 admin이 특정 유저를 강제로 탈퇴시키는 api의 경우 현재 "/user/admin/{userId}", delete method로 설계를 하였는데 이부분이 개인적으로 찜찜한데
어떻게 개선해야할지 감이 잘 잡히지 않습니다.\
그리고 accessToken을 재발급 받는 api의 url이 "authentication/reissue"인데 reissue라는 동사(행위)를 url에 포함시켜도 될지 궁금합니다. 또한
accessToken을 재생성하는 행위기에 post method를 사용하였는데 accessToken을 재발급할때 refreshToken만을 header로 받고 있기 때문에 body를 사용하지 않아서 post method를
사용해도 되는지 의문이 듭니다.)
* accessToken을 재발급해줄때 refreshToken만을 받고 Token의 유효성은 JwtInterceptor에서 검증될 것이라고 판단하여, 비즈니스 로직에서는 refresh 토큰에서 추출한 userId를
가지고 있는 refreshToken과 client에서 받은 refreshToken만 일치하는지 확인을 하고 accessToken을 재발급해주고 있는데, 이렇게만 해도 충분한지 궁금합니다.

#### 궁금했던 부분
* accessToken을 재발급할때 refreshToken도 재발급하는 방법도 있는데, 이 방법이 괜찮은 방법인지 궁금합니다.
* 스프링 기본 오류 URL이 "/error"로 알고있는데 "/error"로 이동할때도 Interceptor를 거치게 되어서 토큰 보유 유무에 관한 오류가 log로 남는것을 확인하였습니다.
그래서 "/error"을 excludePathPatterns에 등록하니 제가 Interceptor에서 sendError로 client에 401번 HttpStatus Code를 보냈음에도 무조건 500번 HttpStatus Code를
client에서 받게되는데 이부분에 대해서 어떻게 해결해야할지 궁금합니다.\
(현재는 일단 원하는 HttpstatusCode를 반환하는것이 중요하다고 생각하여 추가적으로 "/error"로 이동함으로써 발생할때 생기는 토큰 관련 log는 무시하고 있습니다.
사실상 제가 작성한 log만 남는것이고 동작에는 큰 문제가 없다고 확인되었기 떄문입니다.)
* Token을 발급해서 client에 반환해줄때 무조건 Cookie를 사용해야 하는지 궁금합니다.\
(현재는 Token을 담을 Dto를 만들어서 반환하고 있습니다.)
* 현재 refreshToken으로 accessToken을 재발급하는 api를 제공하고 있는데 client에서 특정 api를 호출했는데 acessToken이 만료되었으면 401 HttpStatus Code를 반환하고, client는 HttpStatus Code를 보고 accessToken이 만료되었으면
accessToken을 재발급 받는 api에 요청을 보내고 이 경우에 RefreshToken또한 만료되었다면 401 HttpStatu Code를 반환하여 client가 다시 login을 하게 만드는 흐름으로 설계를 하였는데
이렇게 해도 괜찮은지 피드백을 받고 싶습니다.
* 유저의 권한 변경(관리자 변경 부여, 제거)를 위한 api를 제공하고 있는데, 실제로는 이런 api에 모든 user가 접근할 수 있게 되면 큰 문제가 될것인데,
관리자 계정을 생성할려고 하면 DBMS를 통해 생성해 주어야 하는지 궁금합니다.

### 4. 기술 스택
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MYSQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)
