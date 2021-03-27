package study.signUpUsingJDBC.service;

import org.springframework.stereotype.Service;
import study.signUpUsingJDBC.domain.Member;

import java.security.NoSuchAlgorithmException;

public interface MemberService {
    void join(Member member);
    void modify(Member member) throws NoSuchAlgorithmException;
    void delete(Long id);
    Member login(String username, String userpwd) throws NoSuchAlgorithmException;
    Member getMemberById(Long id);
    void auth(Long id);
}
