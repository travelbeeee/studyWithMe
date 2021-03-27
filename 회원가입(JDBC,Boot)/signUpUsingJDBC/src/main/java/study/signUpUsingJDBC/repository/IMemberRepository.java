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
