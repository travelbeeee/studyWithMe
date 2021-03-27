package study.signUp.service;


import study.signUp.domain.Member;

import java.security.NoSuchAlgorithmException;

public interface MemberService {
    void join(Member member);
    void modify(Member member) throws NoSuchAlgorithmException;
    void delete(Long id);
    Member login(String username, String userpwd) throws NoSuchAlgorithmException;
    Member getMemberById(Long id);
    void auth(Long id);
}
