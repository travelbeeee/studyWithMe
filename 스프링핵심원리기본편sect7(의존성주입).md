# 의존관계 자동 주입

앞에서 의존관계를 @Autowired 애노테이션을 이용해 자동 주입 할 수 있다고 했다.

자동주입하는 4가지 방법에 대해서 알아보자.

### 1) 의존관계 자동주입

#### 1-1) 생성자 주입

이름 그대로 생성자를 통해서 의존 관계를 주입 받는 방법.

- 생성자 호출 시점에 딱 1번만 호출되는 것이 보장 --> **불변**이 보장된다.

```java
@Component
public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy
    discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

> @Autowired 가 없어도 생성자가 딱 1개만 있으면 자동으로 주입 된다. ( 스프링 빈에 한해서만 )

<br>

#### 1-2) 수정자 주입

setter 를 이용해서 의존관계를 주입하는 방법

- **선택, 변경** 가능성이 있는 의존관계에 사용

```java
@Component
public class OrderServiceImpl implements OrderService {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;
    
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
    	this.memberRepository = memberRepository;
    }
    @Autowired
    
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    	this.discountPolicy = discountPolicy;
    }
}
```

setter 를 이용해서 의존 관계를 바꿔줄 수도 있으므로 Test 에서는 따로 쓰기에 편하다. 하지만, 변경 가능성이 있으므로 조심...!

<br>

#### 1-3) 필드 주입

이름 그대로 필드에 바로 주입하는 방법

- 코드가 간결하지만 외부에서 변경이 불가능해서 테스트하기 힘들다

  --> 치명적인 단점

- DI 프레임워크가 없으면 아무것도 할 수 없다

```java
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
   	private MemberRepository memberRepository;
    @Autowired
   	private DiscountPolicy discountPolicy;
    
}
```

테스트 코드에서는 사용해도 되지만 애플리케이션의 실제 코드로는 사용하지말자!!

<br>

#### 1-4) 일반 메서드 주입

의존관계를 주입해주는 일반 메서드를 통해서 주입 받는 방법

- 일반적으로 사용 X

```java
@Component
public class OrderServiceImpl implements OrderService {
   	private MemberRepository memberRepository;
   	private DiscountPolicy discountPolicy;
    
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

<br>

### 2) 주입할 빈이 없는 경우

주입할 스프링 빈이 없으면 당연히 에러가 발생한다. 이때, 스프링 빈이 없어도 동작해야 하면 다음과 같은 설정을 이용할 수 있다.

#### 2-1) @Autowired(required=false)

Default 값은 true로 되어있고, false로 변경해주면 자동 주입할 대상이 없으면 호출이 안된다.

```java
public class MemberServiceImpl implements  MemberService{

    private MemberRepository memberRepository;

    @Autowired(required=false)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}

```

<br>

#### 2-2) @Nullable 애노테이션

자동 주입할 대상이 없으면 null 을 주입해준다.

```java
public class MemberServiceImpl implements  MemberService{

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(@Nullable MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}

```

<br>

#### 2-3) Optional<>

자동 주입할 대상이 없으면 Optional.empty 를 주입해준다.

```java
public class MemberServiceImpl implements  MemberService{

    private MemberRepository memberRepository;

    @Autowired(required=false)
    public MemberServiceImpl(Optional<MemberRepository> memberRepository) {
        this.memberRepository = memberRepository;
    }
}

```





