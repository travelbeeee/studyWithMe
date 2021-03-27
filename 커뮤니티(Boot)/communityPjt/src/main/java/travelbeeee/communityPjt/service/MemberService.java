package travelbeeee.communityPjt.service;

import travelbeeee.communityPjt.domain.Member;

import java.security.NoSuchAlgorithmException;

public interface MemberService {
    void join(Member member);
    void modify(Member member) throws NoSuchAlgorithmException;
    void delete(Long memberId);
    Member login(String username, String userpwd) throws NoSuchAlgorithmException;
    Member getMemberById(Long memberId);
    void auth(Long memberId);
}
