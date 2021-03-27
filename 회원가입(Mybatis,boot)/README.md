# 프로젝트: Mybatis를 이용한 회원가입예제

https://github.com/travelbeeee/Spring_Practice/tree/master/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85(JDBC%2CBoot)

JDBC로 구현했던 회원가입 프로젝트를 Mybatis로 교체했습니다.

**[ 기존 IMemberRepository Class ]**

```java
package study.signUpUsingJDBC.repository;

import org.springframework.stereotype.Repository;
import study.signUpUsingJDBC.domain.Member;

import java.sql.*;

@Repository
public class IMemberRepository implements MemberRepository{
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@//localhost:1521";
    private String user = "travelbeeee";
    private String password = "1234";

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public int insert(Member member) {
        int result = 0;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO member (id, username, userpwd, salt, email, auth) values (member_seq.nextval, ?,?,?,?, 'UNAUTHORIZATION')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getUserpwd());
            pstmt.setString(3, member.getSalt());
            pstmt.setString(4, member.getEmail());
            result = pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return result;
    }

    @Override
    public int update(Member member) {
        int result = 0;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE member SET username = ?, userpwd = ?, email = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getUserpwd());
            pstmt.setString(3, member.getEmail());
            pstmt.setLong(4, member.getId());
            result = pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return result;
    }

    @Override
    public int updateAuth(Long id) {
        int result = 0;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE member SET auth = 'AUTHORIZATION' WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            result = pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        int result = 0;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE member WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            result = pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return result;
    }

    @Override
    public Member selectByName(String username) {
        Member member = new Member();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM member WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            if(rs.next()){
                member.setId(rs.getLong("id"));
                member.setUsername(rs.getString("username"));
                member.setUserpwd(rs.getString("userpwd"));
                member.setEmail(rs.getString("email"));
                member.setSalt(rs.getString("salt"));
                member.setAuth(rs.getString("auth"));
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return member;
    }

    @Override
    public Member selectById(Long id) {
        Member member = new Member();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM member WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();
            if(rs.next()){
                member.setId(rs.getLong("id"));
                member.setUsername(rs.getString("username"));
                member.setUserpwd(rs.getString("userpwd"));
                member.setEmail(rs.getString("email"));
                member.setSalt(rs.getString("salt"));
                member.setAuth(rs.getString("auth"));
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return member;
    }

    @Override
    public Member selectByNamePwd(String username, String userpwd) {
        Member member = new Member();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM member WHERE username = ? and userpwd = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, userpwd);
            rs = pstmt.executeQuery();
            if(rs.next()){
                member.setId(rs.getLong("id"));
                member.setUsername(rs.getString("username"));
                member.setUserpwd(rs.getString("userpwd"));
                member.setEmail(rs.getString("email"));
                member.setSalt(rs.getString("salt"));
                member.setAuth(rs.getString("auth"));
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return member;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(pstmt != null){
                pstmt.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

```

**[ Mybatis XML ]**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="study.signUp.repository.MemberRepository">

    <insert id="insert" parameterType="member">
        INSERT INTO member (id, username,userpwd, salt, email, auth)
        VALUES (member_seq.nextval, #{username}, #{userpwd}, #{salt}, #{email}, 'UNAUTHORIZATION')
    </insert>
    <update id="update" parameterType="member">
        UPDATE member SET username = #{username}, userpwd = #{userpwd}, email = #{email} WHERE id = #{id}
    </update>
    <delete id="delete" parameterType="Long">
        DELETE member WHERE id = #{id}
    </delete>
    <select id="selectByName" parameterType="String" resultType="member">
        SELECT * FROM member WHERE username = #{username}
    </select>
    <select id="selectById" parameterType="Long" resultType="member">
        SELECT * FROM member WHERE id = #{id}
    </select>
    <select id="selectByNamePwd" parameterType="String" resultType="member">
        SELECT * FROM member WHERE username = #{param1} and userpwd = #{param2}
    </select>
    <update id="updateAuth" parameterType="Long">
        UPDATE member SET auth = 'AUTHORIZATION' where id = #{id}
    </update>
</mapper>
```

Mybatis를 사용하면 훨씬 더 직관적으로 보기 좋게 코딩을 할 수 있는 것 같다.

<br>

### 에러사항

**1) Select결과 Null**

select 결과가 Null인 경우에는 해당 객체의 id나 name 등을 참조하면 nullpointerException이 발생한다.

[ 기존 JDBC를 이용한 코드 ]

```java
Member member = memberService.login(loginForm.getUsername(),loginForm.getUserpwd());

if (member.getId() == null)
	return "redirect:/";
```

[ Mybatis를 이용한 코드 ]

```java
Member member = memberService.login(loginForm.getUsername(), loginForm.getUserpwd());

if (member == null)
	return "redirect:/";
```

