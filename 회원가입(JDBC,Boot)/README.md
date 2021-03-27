# 프로젝트 : JDBC를 이용한 회원가입예제

`springboot 2.4.2`

`java 11`

`spring-boot-starter-mail 2.4.2`

`spring-boot-starter-thymeleaf 2.4.2`

`spring-boot-starter-web 2.4.2`

`ojdbc 8.19.8.0.0`

`lombok 1.18.16`

<br>

### 요구사항

- 회원가입을 할 수 있어야한다. ( O )
- 로그인을 할 수 있어야한다. ( O )
- 로그인 후 회원 정보를 수정할 수 있어야한다. ( O )
- 로그인 후 회원 탈퇴를 할 수 있어야한다. ( O )
- 로그인을 해야 서비스를 이용할 수 있다. ( O ) 
- 아이디 중복체크를 해준다. ( O )
- 비밀번호는 암호화해서 저장해야한다. ( O )
- 아이디는 영어소문자, 대문자, 숫자로만 이루어져야하며 7~15자 사이여야한다.
- 비밀번호는 영어소문자, 대문자, 숫자로만 이루어여쟈하며 7~15자 사이여야한다.
- 회원가입 후 이메일인증해야 서비스 이용. ( O )

<br>

### HTTP API 설계

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

<br>

### 구현



<br>

### 에러사항 및 고충

**1) Thymeleaf**

템플릿엔진 없이 사용하려니까 templates 폴더로 경로설정을 위해 다음과 같은 작업을 추가로 해야했다.

```java
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/");
    }
}

```

그러나 Controller에서 @PostMapping으로 로그인, 회원가입을 처리하고 html 파일경로를 return해도 405 에러가 발생함.....

```java
@PostMapping("/member/login")
public String login(LoginForm loginForm, HttpSession httpSession) throws NoSuchAlgorithmException{
    Member member = memberService.login(loginForm.getUsername(), loginForm.getUserpwd());

    return "/member/myPage";
}
```

--> Thymeleaf 를 추가로 build해서 일단은 문제를 해결...!

--> 흠...뭐가문제지.....

<br>

**2) 암호화**

해시함수를 이용해서 암호화를 진행함. 따라서 Salt 작업이 필요하고 이를 위해서는 회원마다 Salt 값을 Member table에서 가지고 있어야한다. 그래야 로그인, 회원정보수정 등에서 Salt 값을 불러와서 입력된 패스워드를 암호화해서 진행할 수 있음.

--> 일단은 구현은 다 한 것 같은데 MemberRepository 클래스에서 생각보다 많은 메소드를 구현한 것 같다.

--> 조금 더 단순화시킬 방법이 있을까...

<br>

**3) 아이디, 비밀번호, 메일 양식체크**

자바 정규표현식을 이용하면 할 수 있는건가?? 자바 정규표현식을 공부해보자...!

<br>

##### 4) 이메일 인증

스프링에서 지원해주는 JavaMailSender 로 구현.

( https://github.com/travelbeeee/Spring_Basic/blob/master/sub_05_SendingMail.md )