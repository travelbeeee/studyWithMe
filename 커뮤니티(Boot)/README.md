# Community

회원가입 / 글쓰기 기능을 지원하는 간단한 커뮤니티를 만들어보자!!

1) 회원가입

https://github.com/travelbeeee/Spring_Practice/tree/master/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85(Mybatis%2Cboot)

- 이메일 인증을 해야만 Community 서비스를 이용할 수 있음.
- 로그인을 해야만 Community 서비스를 이용할 수 있음.

<br>

2) 글쓰기

- 자기가 쓴 글에 대해서만 수정 / 삭제를 진행할 수 있다.
- 타인이 나의 글을 읽으면 조회수가 올라간다.
- 파일을 업로드 할 수 있다.

<br>

### API 설계

- Index 화면	'/'	GET
- 회원가입 폼	'/member/signUp'	GET
- 회원가입	'/member/signUp'	POST
- 로그인 폼	'/member/login'	GET
- 로그인	'/member/login'	POST
- 로그아웃	'/member/delete'	POST
- 회원정보수정 폼	'/member/modify'	GET
- 회원정보수정	'/member/modify'	POST
- 회원탈퇴	'/member/delete'	POST
- 인증메일보내기	'/member/authMailSending'	POST
- 인증하기	'/member/authCode'	POST
- Main  화면	'/main'	GET
- 글쓰기 폼	'/writing/write' 	GET
- 글쓰기	'/writing/write'	POST
- 글읽기	'/wirting/{id}'	GET
- 글수정하기 폼	'/writing/modify/{id}'	GET
- 글수정하기	'/writing/modify/{id}'	POST
- 글삭제하기	'/writing/delete/{id}'	POST
- 파일다운로드하기	'/uploadFile/download/{id}'	POST

<br>

### 추가해야될것들

- [x] 회원가입 수정 시 같은 아이디(비밀번호 혹은 이메일만 변경하는 상황) 에서는 아이디 중복에 걸려서 회원 정보 수정이 안된다.

  --> 수정사항으로 입력된 아이디를 가진 회원의 번호와 현재 회원 번호를 비교해서 같은 회원인지 체크! 같은 회원이라면 같은 아이디로 Update 시켜버려도 된다.

- [x] 이유는 모르겠으나 비밀번호 수정하면 로그인이 안된다... 우찌되는거누...

  --> 프론트 form에서 input id를 잘못 설정하고 있었음...!

- [ ] 회원정보 수정시 비밀번호를 입력해야되는데 입력안해도 수정이 진행됨 무조건 입력하도록 만들자!

- [ ] 글쓰기 수정  혹은 삭제는 해당 글을 쓴 사람에게만 권한을 줘야된다!!!

  --> thymeleaf 로 th:if 로 처리해야되나...? NoNo Controller에서 처리해야된다!

- [ ] 아이디, 비밀번호, 이메일 모두 입력 + 패턴 잡아주기!

- [x] 자기가 쓴 글을 제외하고 다른 글을 읽으면 조회수를 올려주자.

  --> 매번 DB에 쿼리날리는거말고는 방법이 없을까?? (없을것같긴하다...)

  --> 새로고침해서 조회수 조작하는건 어떻게 막지...???

- [x] 파일... 업로드된 파일을 로컬에서 불러와서 다운로드하게 만들어줘야되는데....

  File클래스 이용해서 파일 삭제, 다운로드, 저장까지 해결!!