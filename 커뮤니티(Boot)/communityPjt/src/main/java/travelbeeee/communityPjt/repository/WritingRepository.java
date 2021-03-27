package travelbeeee.communityPjt.repository;

import org.apache.ibatis.annotations.Mapper;
import travelbeeee.communityPjt.domain.Writing;

import java.util.List;

@Mapper
public interface WritingRepository {
    int insert(Writing writing); // 글쓰기
    int update(Writing writing); // 글수정하기
    int delete(Long writingId); // 글삭제하기
    List<Writing> selectAll(); // 글List조회
    Writing selectById(Long writingId); // 글조회하기
    int updateViews(Long writingId); // 글 조회수 올리기
}
