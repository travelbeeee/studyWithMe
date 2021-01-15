# 싱글톤 컨테이너

웹 어플리케이션은 보통 여러 고객이 동시에 요청

--> 그럼 매번 객체를 새롭게 만들어서 할당할것이냐??

![20210114_123446](C:\Users\HyunSeok\Desktop\studyWithMe\gitHub\img\20210114_123446.png)

고객 트래픽이 초당 100이 나오면 초당 100개의 객체가 생성되고 소멸된다!

--> 메모리 낭비가 심하다!

--> 객체를 1개만 생성하고 공유하도록 설계하자 --> 싱글톤 패턴!!!



### 1) 싱글톤 패턴

싱글톤 패턴은 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴으로 싱글톤 패턴을 구현하는 방법은 여러가지! 

가장 간단한 방법으로 구현해보고 익혀보자

```java
package hello.core.singleton;

// 싱글톤 패턴으로 만드는 클래스!
public class SingletonService {

    // static 영역으로 instance를 미리 하나 생성해서 올려둔다.
    private static final SingletonService instance = new SingletonService();

    // 이미 static으로 만들어진 객체를 참조하는 것 말고는 사용 불가능!
    public static  SingletonService getInstance(){
        return instance;
    }

    // 생성자를 private으로 막아버린다. --> 새롭게 객체 생성 불가능!
    private SingletonService(){ }
}
```

private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막고, static 영역으로 클래스를 미리 생성해서 올려놓으면 된다.

다음과 같이 사용할 수 있다.

```java
SingletonService singletonService1 = SingletonService.getInstance();
```

<br>

### 2) 싱글톤 패턴 문제점

- 싱글톤 패턴을 구현하는 코드가 많이 추가된다. 내가 원하는건 Service 클래스에는 Service 관련된 로직만 들어갔으면 좋겠는데, 일단 싱글톤 패턴을 위한 코드가 들어가야됨!
- 의존관계상 클라이언트가 구체 클래스에 의존해서 DIP, OCP 원칙을 위반할 가능성이 높다.
- private 생성자로 자식 클래스를 만들기가 어렵고, 내부 속성을 변경하거나 초기화 하기 어렵다.

==> 유연성이 떨어진다!!!

==> 스프링을 이용하자

<br>

### 3)  싱글톤 컨테이너

스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서, 싱글톤으로 객체를 관리해줘서 싱글톤 컨테이너라고 불리기도 합니다.

즉, 스프링 컨테이너는 싱글톤 패턴으로 객체를 관리해주면서 위에서 우리가 직접 구현해서 사용했을 때 나오는 문제점을 다 해결해준다!! ( 역시 스프링 갓... )

![20210114_124038](C:\Users\HyunSeok\Desktop\studyWithMe\gitHub\img\20210114_124038.png)

> 참고 : 스프링의 기본은 싱글톤방식이지만, 요청할 때마다 새로운 객체를 생성해서 반환하도록 설정할 수도 있다.

<br>

### 4) 싱글톤 방식 주의점

싱글톤 패턴을 이용하면 객체 인스턴스를 하나만 생성해서 공유하게 된다. 따라서 **싱글톤 객체는 상태를 유지 (stateful) 하게 설계하면 안되고 항상 무상태(stateless)로 설계**해야한다!!!

- 특정 클라이언트에 의존적인 필드가 있으면 안된다.
- 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
- 가급적 읽기만 가능하게! 변경 X



그럼 stateful 하게 설계하면 어떤 문제가 발생할까?? 간단하게 알아보자.

```java
public class StatefulService {
    private int price; //상태를 유지하는 필드
    
    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; //여기가 문제!
    }
    
    public int getPrice() {
        return price;
    }
}
```

order 메소드에서 필드 값을 주입하는 부분이 있다. 즉, 클라이언트가 값을 변경할 수 있다.

```java
ApplicationContext ctx = new
AnnotationConfigApplicationContext(TestConfig.class);

StatefulService statefulService1 = ac.getBean("statefulService",
StatefulService.class);

StatefulService statefulService2 = ac.getBean("statefulService",
StatefulService.class);

//ThreadA: A사용자 10000원 주문
statefulService1.order("userA", 10000);

//ThreadB: B사용자 20000원 주문
statefulService2.order("userB", 20000);
```

사용자 A와 사용자 B가 Service를 이용해 order 메소드를 이용했다고 해보자.

먼저, 사용자 A가 price 값에 10000을 저장하지만 사용자 B가 price 값에 20000을 저장하면서 사용자 A가 저장한 값이 사라지게 된다.

이게 만약 실제 결제와 관련된 부분이라고 생각하면 사용자 A는 10000원을 결제 요청했지만, 사용자 B가 20000원을 바로 결제 요청을 하면서 결제 값이 20000원으로 바뀌게 된다. 즉, A는 20000원을 결제하게되는 말도 안되는 상황이 발생한다!!

**--> 항상 항상 stateless 하게 설계하자!**

<br>

### 5) @Configuration

```java
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

}
```

@Configuration, @Bean 어노테이션을 이용해서 스프링 컨테이너에 빈을 등록할 수 있다고 했다. 그런데 위의 코드를 살펴보면 MemberService, OrderServie 객체를 만들 때 모두 memberRepository() 메소드를 호출하게 되고, 결국 new MemoryMemberRepository(); 가 두 번 실행되게 된다.

??? 그럼 객체가 2개가 아닌가...?? 어떻게 싱글톤을 유지해주는거지??

#### 스프링 컨테이너는 싱글톤 레지스트리다. 따라서 스프링 빈이 싱글톤이 되도록 보장해주는데 이를 위해 CGLIB 라는 기술을 사용!

![20210114_124921](C:\Users\HyunSeok\Desktop\studyWithMe\gitHub\img\20210114_124921.png)

스프링 컨테이너에 @Configuration 어노테이션을 통해 AppConfig.class 를 등록하게 되면 AppConfig 클래스를 그대로 상속하면서 싱글톤이 보장되도록 확장된 AppConfig@CGLIB 클래스가 새롭게 만들어지고 스프링에서는 AppConfig 클래스가 아니라 AppConfig@CGLIB 클래스를 이용하게 된다.

이를 통해 우리가 AppConfig.class 에서 @Configuration, @Bean 어노테이션만 사용한다면 싱글톤이 보장이 되는 것이다.

<br>

#### 그럼 @Bean만 이용하면 어떻게 될까??

@Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다. 즉,  MemberService, OrderServie 객체를 만들 때 모두 새로운 memberRepository 가 만들어진다.

