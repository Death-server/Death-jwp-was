# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
- Spring 구조대로 제작하기
  - [x] RequestHandler를 DispatcherServlet으로 변경
  - [x] DispatcherServlet에서 HandlerMapping을 통해 URL에 맞는 controller 탐색<br> 
        ※ HandlerAdapter은 Bean이 없어 한줄로 끝남.. Dispatcher Servlet에서 담당함.
    - [x] 컨트롤러 인터페이스 만들고 해당 컨트롤러 만들기
    - [x] 컨트롤러들을 싱글톤으로 관리하는 HandlerMapping 만들기
    - [x] DispatcherServlet에서 Controller를 매칭하고 실행
  - [ ] Controller에서 ModelAndView로 생성
    - [x] HttpRequest와 HttpResponse로 제작하고 이후 리팩토링할 때 진행할 예정
  - [x] 해당 내용을 반환하기
    - [x] ResponseSender 객체를 만들어 HttpResponse의 정보대로 응답 반환
  - [ ] 리팩토링
    - [x] response header 분리
    - [ ] 예외 처리
    - [ ] request Body에서 데이터 읽어 오는 담당 클래스 분리
  - [ ] 테스트 코드 작성

### 요구사항 2 - get 방식으로 회원가입
- [x] Forward controller 만들어서 html 파일 그대로 반환 해주기
- [x] url에서 쿼리 추출
  - [x] Query Map 추가
- [x] 회원가입 기능 구현
  - [x] signupController에서 쿼리 기반으로 User 생성
  - [x] signupService에서 이를 검증하고 DB에 저장
- [ ] 리팩토링
  - [x] Parser 클래스 합치기
  - [ ] 예외 처리
  - [x] Forward controller 생성
- [ ] 테스트 코드 작성

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 