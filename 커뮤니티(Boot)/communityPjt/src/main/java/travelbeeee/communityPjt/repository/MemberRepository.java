package travelbeeee.communityPjt.repository;

import org.apache.ibatis.annotations.Mapper;
import travelbeeee.communityPjt.domain.Member;

import java.util.List;

@Mapper
public interface MemberRepository {
    int insert(Member member);
    int update(Member member);
    int updateAuth(Long memberId);
    int delete(Long memberId);
    Member selectByName(String username);
    Member selectById(Long memberId);
    Member selectByNamePwd(String username, String userpwd);
    List<Member> selectAll();
}
