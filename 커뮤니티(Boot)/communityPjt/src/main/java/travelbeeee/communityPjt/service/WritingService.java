package travelbeeee.communityPjt.service;

import travelbeeee.communityPjt.domain.Writing;

import java.util.List;

// 글쓰기 글수정하기 글삭제하기 글조회하기
public interface WritingService {
    List<Writing> getWritings(); // 글 조회하기
    Writing getWritingById(Long writingId); // 글 1개 조회
    void insertWriting(Writing writing); // 글 쓰기
    void deleteWriting(Long writingId); // 글 삭제
    void updateViews(Long writingId);
    void update(Writing writing); // 글 수정하기
}
