### 조회한 빈이 모두 필요할 때

조회한 빈이 모두 필요하면 List, Map 자료구조를 이용하면 된다.

예를 들어, DiscountPolicy 가 RateDiscountPolicy, FixDiscountPolicy 2개로 나뉘어져 있는 지금 같은 상황에서 상황에 맞춰서 둘 다 이용하려면 다음과 같이 구현하면 된다.

```java
class DiscountService {
    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;
    
    public DiscountService(Map<String, DiscountPolicy> policyMap,
    List<DiscountPolicy> policies) {
        this.policyMap = policyMap;
        this.policies = policies;
    }
    
    public int discount(Member member, int price, String discountCode) {}
        DiscountPolicy discountPolicy = policyMap.get(discountCode);
        
        return discountPolicy.discount(member, price);
    }
}
```

DiscountService 에서 위와 같이 Map, List 를 선언하면 스프링 빈으로 등록된 모든 DiscountPolicy 가 자동 주입 된다. 따라서, 상황에 맞춰서 꺼내서 사용할 수 있다.

