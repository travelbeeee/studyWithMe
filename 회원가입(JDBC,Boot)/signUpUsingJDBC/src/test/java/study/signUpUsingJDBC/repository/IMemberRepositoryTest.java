package study.signUpUsingJDBC.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.signUpUsingJDBC.domain.Member;

import static org.junit.jupiter.api.Assertions.*;

class IMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository = new IMemberRepository();

    @Test
    public void 회원가입테스트(){
        Member member = new Member();
        member.setUsername("testid");
        member.setUserpwd("testpwd");
        member.setSalt("testsalt");
        member.setEmail("testemail");

        memberRepository.insert(member);
    }
}