### IoC(Inversion of Control) 개념 정리



#### 1. 유저 정보를 관리하는 UserDAO 기본 셋팅

```java
public class User{
	String id;
	String name;
    String password;
    
    // ~~ getter 와 setter 가 있다 ~~ 
}
```

```java
public class UserDao{
	public void add(User user) throws ~~ {
        Class.forName("com.mysql.jdbc.Drvier");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "1234");
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
    public User get(String id) throws ~~ {
        Class.forName("com.mysql.jdbc.Drvier");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "1234");
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        
        rs.close();
        ps.close();
        c.close();
        
        return user;
    }

}
```

유저 정보를 담는 User 클래스와 유저 정보를 DB에 넣고 관리할 수 있는 UserDAO 클래스가 다음과 같이 있다고 해보자.



**UserDAO 는 크게 3가지 관심사항이 있다.**

**1) DB와 연결을 위한 커넥션을 가져오기.**

**2) 필요한 SQL 문장을 담을 Statement를 만들고 실행하기.**

**3) 작업이 끝나면 사용한 리소스 반환하기.**

더 많은 기능을 위해 메소드가 늘어나면 늘어날수록 1) DB 커넥션을 위한 코드가 중복이 될 것이다.

--> 중복 코드를 따로 묶어보자.

***--> 관심이 같은 것끼리 따로 묶어보자.***



#### 2. 중복 코드 메소드 추출

```java
public class UserDao{
    private Connection getConnection() thorws ~~ {
        Class.forName("com.mysql.jdbc.Drvier");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "1234");
        
        return c;
    }
    
	public void add(User user) throws ~~ {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
    public User get(String id) throws ~~ {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        
        rs.close();
        ps.close();
        c.close();
        
        return user;
    }

}
```

관심사가 같은 부분을 따로 메소드로 추출했기 때문에, DB 연결을 위한 커넥션에 수정이 생겨도 getConnection() 메소드만 수정하면 된다. 위에서 한 작업은 기능이 추가되거나 바뀐 것은 없지만 이전보다 훨씬 깔끔해졌고 미래의 변화에 좀 더 쉽게 대응할 수 있다.

--> 공통의 기능을 담당하는 메소드로 중복된 코드를 뽑아내는 것을 **메소드 추출기법(extract method)** 라고 한다.



#### 3. 상속을 통한 확장

UserDao가 인기가 많아져서 네이버와 라인에서 UserDao 를 구매해 사용하고 싶고,

네이버(N)와 라인(L)은 각각 독자적으로 만든 DB 커넥션 가져오는 방법을 사용하고 싶어 한다고 가정해보자.

-->  N사와 L사에서 DB 커넥션을 가져오는 코드를 직접 구현할 수 있게 해주자.

--> 상속을 이용해보자.

UserDao 클래스를 추상클래스로 변경하고, getConnection() 메소드를 추상 메소드로 비워놓자.

```java
public abstract class UserDao{
    public abstract Connection getConnection() thorws ~~ {
    
    }
    
	public void add(User user) throws ~~ {
        Connection c = getConnection();
        // ~~ 실행코드 ~~
        
        return;
    }
    
    public User get(String id) throws ~~ {
        Connection c = getConnection();
        // ~~ 실행코드 ~~
        
        return user;
    }

}

public class NUserDao extends UserDao{
    public Connection getConnection() throws ~~ {
        // N사의 DB Connection 생성 코드
    }
}

public class LUserDao extends UserDao{
    public Connection getConnection() throws ~~ {
        // L사의 DB Connection 생성 코드
    }
}
```

추상 클래스와 상속을 이용하면 N사와 L사 모두 독자적인 DB Connection 코드를 이용할 수 있다.

* ***슈퍼클래스에 기본적인 로직의 흐름을 만들고, 그 기능의 일부를 추상 메소드나 오버라이딩이 가능한 protected 메소드 등으로 만든 뒤 서브클래스에서 이런 메소드를 필요에 맞게 구현해서 사용하도록하는 디자인 패턴을 "템플릿 메소드 패턴" 이라고 한다.***

* ***getConnection() 메소드를 서브 클래스에서 구현하듯이, 서브클래스에서 구체적으로 어떤 오브젝트를 어떻게 생성할 것인지를 결정하는 디자인 패턴을 "팩토리 메소드 패턴" 이라고 한다.***

  --> 위의 2개의 디자인 패턴은 비슷한 기능을 하는 패턴이다.



#### 4. 상속을 통한 확장의 문제점

상속을 통해 확장을 했지만 상속 관계는 두 가지 다른 관심사에 대해 긴밀한 결합을 허용한다. 서브 클래스는 슈퍼클래스의 기능을 직업 사용할 수 있고, 또 슈퍼클래스 내부의 변경이 있을 때 모든 서브클래스를 함께 수정하거나 다시 개발해야 할 수도 있다.

따라서, **상속은 생각보다 밀접한 관계로 다른 방법을 통해 기능을 분리해야한다!!**



#### 5. 상속 대신에 클래스로 분리해보자. ( 그냥 2개의 클래스로 쪼개보자 !! )

<img src="C:\Users\HyunSeok\Desktop\공부공부\img\pic1.png" alt="그림1" style="zoom:50%;" />

```java
public class UserDao{
    private SimpleConnectionMaker simpleConnectionMaker;
    
    public UserDao(){
        simpleConnectionMaker = new SimpleConnectionMaker();
    }
    
	public void add(User user) throws ~~ {
        Connection c = simpleConnectionMaker.makeNewConnection();
        // ~~ 실행코드 ~~
        
        return;
    }
    
    public User get(String id) throws ~~ {
        Connection c = simpleConnectionMaker.makeNewConnection();
        // ~~ 실행코드 ~~
        
        return user;
    }

}

public Class SimpleConnectionMaker{
    public Connection makeNewConnection() throws ~~ {
        Class.forName("com.mysql.jdbc.Drvier");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "1234");
        
        return c;
	}
}
```

**두 개의 클래스로 화끈하게 분리 --> 문제발생**

**1) N사와 L사에 userDao 클래스만 공급하고 상속을 통해 DB 커넥션 기능을 확장해서 사용하는게 불가능해졌다.** 

**즉, UserDao 의 코드 수정 없이 DB 커넥션을 제공하는 방법을 바꾸는게 불가능해졌다.**

**2) N사와 L사가 connection 을 만드는 메소드 이름이 makeNewConnection() 이 아니라면 모든 메소드의 코드를 수정해야된다.**

**3) UserDao 가 DB 커넥션을 제공하는 클래스가 "SimpleConnectionMaker" 라고 구체적으로 클래스에 대해 알고 있어야 한다.**



#### 6. 인터페이스를 이용해보자.

두 개의 클래스가 긴밀하게 연결되어 있지 않도록 중간에 추상적인 느슨한 연결고리를 만들어주자!! --> **인터페이스 이용!!**

<img src="C:\Users\HyunSeok\Desktop\공부공부\img\pic2.png" alt="그림2" style="zoom: 50%;" />

인터페이스는 어떤 일을 하겠다는 기능만 정의해놓은 것이지, 어떻게 하겠다는 구현 방법은 나타나 있지 않다.

--> 인터페이스의 메소드를 통해 알 수 있는 기능에만 관심을 가지면 되지, 그 기능을 어떻게 구현했는지에 관심을 두지 않을 수 있다.

```java
public class UserDao{
	private ConnectionMaker connectionMaker;
    
    public UserDao(){
        connectionMaker = new DConnectionMaker(); // DConnectionMaker 라고 이름 등장 --> 바꿔주자.
    }
    // ~~ 코드 ~~ 
}
public interface ConnectionMaker {
    public Connection makeConnection() throws ~~ {
        
    }
}

public Class NConnectionMaker implements ConnectionMaker {
    // ~~
    public Connection makeConnection() throws ~~ {
        // N 사의 독자적인 방법 !!
    }
}
```

인터페이스를 이용해도 여전히 UserDao에 어떤 ConnectionMaker 가 사용되는지 코드가 남아 있다.

--> 생성자를 이용해보자

```java
public class UserDao{
	private ConnectionMaker connectionMaker;
    
    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }
    // ~~ 코드 ~~ 
}
public interface ConnectionMaker {
    public Connection makeConnection() throws ~~ {
        
    }
}

public Class NConnectionMaker implements ConnectionMaker {
    // ~~
    public Connection makeConnection() throws ~~ {
        // N 사의 독자적인 방법 !!
    }
}
```

이렇게 구현한다면 UserDao 의 ConnectionMaker 를 결정하는 책임을 떠넘길 수 있다.

```java
public Class UserDaoTest{
    public static void main(String[] arsgs) throws ~~ {
        ConnectionMaker connectionMaker = new NConnectionMaker();

        UserDao dao = new UserDao(connectionMaker); // UserDao 생성할 때 ConnectionMaker 결정!!
    }
}
```

--> UserDao는 자신의 관심사이자 책임인 사용자 데이터 액세스 작업을 위해 SQL을 생성하고, 이를 실행하는 데만 집중하면 된다!!

--> 인터페이스를 도입해 클라이언트의 도움을 얻는 방법을 사용하면 훨씬 유연하게 코드를 구현할 수 있다.



#### 7. 오브젝트 팩토리

UserDao 를 인터페이스를 이용해서 성공적으로 분리

--> 그럼 UserDao 객체를 생성하고 어떤 ConnectionMaker를 사용하는지 누가 결정해줄것인가??

--> 객체의 생성 방법을 결정하고 그렇게 만들어진 객체를 돌려주는 일을 하는 오브젝트를 **팩토리(factory)** 라고 한다.

```java
public class DaoFactory{
	public UserDao userDao(){
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        
        return userDao;
    }
}
```

--> N사, L사에게 UserDao와 ConnectionMaker와 DaoFactory 를 함께 제공한다.

이때, DaoFactory 는 소스코드를 공개해서 제공하면 N사, L사가 자유롭게 UserDao를 이용할 수 있다.



#### 8. 오브젝트 팩토리 활용

UserDao 말고도 AccountDao, MessageDao 등 여러 Dao가 더 필요하다고 해보자.

```java
public class DaoFactory{
	public UserDao userDao(){
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        
        return userDao;
    }
    
    public AccountDao accountDao(){
        ConnectionMaker connectionMaker = new NConnectionMaker();
        AccountDao accountDao = new AccountDao(connectionMaker);
        
        return accountDao;
    }
    
    public MessageDao messageDao(){
        ConnectionMaker connectionMaker = new NConnectionMaker();
        MessageDao messageDao = new MessageDao(connectionMaker);
        
        return messageDao;
    }
}
```

--> DaoFactory 는 중복된 코드가 존재

--> 현재 DaoFactory 는 확장에 용이하지 않다.

--> 별도의 메소드로 뽑아내자.

```java
public class DaoFactory{
    public ConnectionMaker connectionMaker(){
   
    	return new NConnectionMaker();
	}
                        
	public UserDao userDao(){
        UserDao userDao = new UserDao(connectionMaker());
        
        return userDao;
    }
    
    public AccountDao accountDao(){
        AccountDao accountDao = new AccountDao(connectionMaker());
        
        return accountDao;
    }
    
    public MessageDao messageDao(){
        MessageDao messageDao = new MessageDao(connectionMaker());
        
        return messageDao;
    }
}
```



#### 9. 라이브러리 vs 프레임워크

라이브러리를 사용하는 애플리케이션 코드는 애플리케이션 흐름을 직접 제어한다. 단지 동작하는 중에 필요한 기능이 있을 때 능동적으로 라이브러리를 사용할 뿐이다.

**프레임워크는 거꾸로 애플리케이션 코드가 프레임워크에 의해 사용된다. 프레임워크 위에 개발한 클래스를 등록해두고, 프레임워크가 흐름을 주도하는 중에 개발자가 만든 애플리케이션 코드를 사용하도록 만드는 방식이다.**

**--> 프레임워크는 제어권을 상위 템플릿 메소드에 넘기고 자신은 필요할 때 호출되어 사용되도록 하는 IoC(Inversion Of Control) 개념이 적용된 기술이다.**



#### 10. 마무리

위에서 최종적으로 만든 UserDao와 DaoFactory 도 IoC 가 적용되어 있다.

원래 ConnectionMaker의 구현 클래스를 결정하고 오브젝트를 만드는 제어권은 UserDao 에게 있었다. 그러나 지금은 DaoFactory 에게 넘어갔고, UserDao는 이제 수동적인 존재가 되었다.  더욱이 UserDao와 ConnectionMaker의 구현체를 생성하는 책임도 DaoFactory 가 맡고 있다.

