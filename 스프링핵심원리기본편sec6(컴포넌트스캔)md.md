# 컴포넌트스캔

앞에서 스프링 빈 등록하는 방법으로 @Configuration, @Bean 애노테이션을 활용한 자바 설정과 XML 에 <bean> 태그를 이용한 설정 방법을 알아봤다.

얘네의 단점!

빈을 등록할 때마다 하나하나 메타정보를 추가해줘야한다!

--> 등록할 빈이 많아지면 귀찮다!

--> 컴포넌트 스캔을 이용해서 자동으로 주입해보자.

<br>

### 1) @ComponentScan

@ComponentScan 애노테이션을 이용하면 이름 그대로 Component 를 찾아서 Scan을 한다. 그리고 찾은 Component 들을 Bean으로 등록해준다. 

Component로 등록하는 방법은 간단하다. @Component 애노테이션을 붙여주면 된다.

즉, 우리는 @Configuration 애노테이션을 설정한 Config 클래스에 @Bean으로 하나하나 등록하지않고, 우리가 등록을 원하는 클래스들에 @Component 애노테이션을 설정해주고 ComponentScan을 이용하면 자동으로 빈을 등록할 수 있다.

```java
@Configuration
@ComponentScan
public class AutoAppConfig {
}
```

다음과 같이 AutoAppConfig 클래스를 만들면 아래와 같이 @Component 애노테이션이 붙은 클래스들을 빈으로 등록해준다.

```java
@Component
public class MemoryMemberRepository implements MemberRepository {
    // ~~ 로직 코드 ~~
}

@Component
public class RateDiscountPolicy implements DiscountPolicy {
    // ~~ 로직 코드 ~~
}
```

그러면 의존관계 주입은 어떻게 되는걸까...?

```java
@Bean
public MemberService memberService(){
    return new MemberServiceImpl(memberRepository());
}
```

위와 같이 @Bean 애노테이션을 이용할 때는 의존관계 주입이 다 명시가 되어있었다.

@ComponentScan을 이용하면 의존관계를 어떻게 스프링에게 알려줄 수 있을까??

@Autowired 애노테이션을 이용하면 된다!

```java
@Component
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
   
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
    	this.memberRepository = memberRepository;
    }
}
```

@Autowired 애노테이션을 이용하면 Component Scan 중에 자동으로 의존관계를 주입해준다!

<br>

정리하면 @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록하는데 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자로 등록한다.

직접 지정하고 싶으면 다음과 같이 하면 된다.

```
@Component("MyRenameComponent")
```

<br>

### 2) @ComponentScan 스캔 대상

@ComponentScan이 붙어있으면 @Component를 찾아서 등록해준다는 것을 알겠다.

그럼 어디부터 어디까지 탐색해서 @Component를 찾아줄까??

모든 자바 클래스를 다 스캔하려면 시간이 오래 걸리므로 항상 지정해줘야한다!

```java
@ComponentScan(
	basePackages = "hello.core",
)
```

위와 같이 basePackages 속성을 통해 스캔 위치를 지정해줄 수 있다. 

위는 hello.core 패키지 하위 자바 클래스를 다 스캔해서 컴포넌트 스캔을 해달라는 뜻이다.

**지정하지않으면, @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.**

![20210114_141806](C:\Users\HyunSeok\Desktop\studyWithMe\gitHub\img\20210114_141806.png)

예를 들어 다음과 같은 AppConfig 클래스에 @ComponentScan 애노테이션을 이용하면 hello.core 패키지 하위 자바 클래스를 다 스캔한다.

```jade
@ComponentScan(
	basePackages = {"hello.core", "hello.core2"},
)
```

위와 같이 여러 개의 스캔 위치를 지정할 수도 있다.

> 참고 : @Configuration 애노테이션은 @Component 애노테이션을 내포하고 있으므로, Config 클래스에 @Configuration 애노테이션만 붙여주면 @ComponentScan의 대상이 된다.

<br>

### 3) @Component

@Component 애노테이션이 있으면 컴포넌트 스캔의 대상이 된다고 했다. 따라서 @Configuration 애노테이션처럼 여러 애노테이션이 @Component 애노테이션을 가지고 있어서 컴포넌트 스캔의 기본 대상이 된다.

- @Component : 컴포넌트 스캔의 대상으로 등록
- @Controller : 스프링 MVC 컨트롤러에서 사용하는 애노테이션으로 @Component 애노테이션을 내포하고 있다.
- @Service : 스프링 비지니스 로직에서 사용하는 애노테이션으로 @Component 애노테이션을 내포하고 있다. ( 특별한 기능은 없지만 핵심 비지니스 로직이 있다는 뜻으로도 사용 )
- @Repository : 스프링 비지니스 로직에서 사용하는 애노테이션으로 @Component 애노테이션을 내포하고 있다. 또, 스프링이 데이터 접근 계층으로 인식하고 데이터 계층의 예외를 스프링 예외로 변환해준다.
- @Configuration : 스프링 설정 정보에서 사용하는 애노테이션으로 @Component 애노테이션을 내포하고 있다. 스프링이 싱글톤을 유지하도록 추가 처리를 한다.

@Component 애노테이션보다는 상황에 맞는 애노테이션을 사용하자!

<br>

### 4) Filter

컴포넌트 스캔에 필터를 줄 수 있다.

- includeFilters : 컴포넌트 스캔 대상을 추가로 지정
- excludeFilters : 컴포넌트 스캔 대상에서 제외

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
```

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
```

위와 같이 2개의 애노테이션을 만들었고, @ComponentScan 의 includeFilters, excludeFilters 속성에 위의 애노테이션을 지정해주겠다.

```java
@Configuration
@ComponentScan(
    includeFilters = @Filter(type = FilterType.ANNOTATION, classes =
    MyIncludeComponent.class),
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
    MyExcludeComponent.class)
)
```

그러면 MyIncludeComponent 애노테이션이 붙은 class는 includeFilters에 의해 빈으로 등록이 되고 MyExcludeComponent 애노테이션이 붙은 class는 excludeFilters 에 의해 빈에서 제외가 된다.

```java
@MyIncludeComponent
public class BeanA {
}
```

```java
@MyExcludeComponent
public class BeanB {
}
```

BeanA는 등록이 되고, BeanB는 등록에서 제외된다.

<br>

FilterType 으로는 5가지 옵션이 있다.

- ANNOTATION : 기본값, 애노테이션을 인식해서 동작
- ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
- ASPECTJ : AspectJ 패턴 사용
- REGEX : 정규 표현식
- CUSTOM : TypeFilter 라는 인터페이스를 구현해서 처리

<br>

### 5) 빈 중복 등록

@ComponentScan 을 활용해서 빈을 등록할 때 빈이 중복되면 어떻게 될까?

당연히 자동으로 빈을 등록할 때 이름이 같은 빈이 중복된다면 스프링은 오류를 발생시킨다.

`ConflictingBeanDefinitionException` 예외가 발생.

그러면 @Bean 태그를 이용해서 수동으로 등록하는 빈과 @Component 애노테이션을 이용해서 자동으로 등록하는 빈이 중독되면 어떻게 될까??

<br>

1) 스프링에서는 수동 빈 등록을 우선으로 해서 등록한다.

2) 스프링부트에서는 예외를 발생시켜준다.

보통 "개발자가 의도적으로 수동 빈 등록을 우선시해주니까, 덮어야지! " 하고 설계에서 진행한다기보다는 여러 설정들이 꼬여서 생기는 상황이 더 많아서 스프링부트에서는 예외처리하기로 했다.

 

