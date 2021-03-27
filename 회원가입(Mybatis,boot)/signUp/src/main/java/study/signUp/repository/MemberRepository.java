package study.signUp.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.Alias;
import study.signUp.domain.Member;

@Mapper
public interface MemberRepository {
    int insert(Member member);
    int update(Member member);
    int updateAuth(Long id);
    int delete(Long id);
    Member selectByName(String username);
    Member selectById(Long id);
    Member selectByNamePwd(String username, String userpwd);
}
