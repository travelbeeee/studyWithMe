package study.signUpUsingJDBC.repository;


import study.signUpUsingJDBC.domain.Member;

public interface MemberRepository {
    int insert(Member member);
    int update(Member member);
    int updateAuth(Long id);
    int delete(Long id);
    Member selectByName(String username);
    Member selectById(Long id);
    Member selectByNamePwd(String username, String userpwd);
}
