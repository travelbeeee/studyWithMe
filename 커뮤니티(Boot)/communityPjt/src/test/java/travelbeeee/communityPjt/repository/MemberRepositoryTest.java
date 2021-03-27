package travelbeeee.communityPjt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelbeeee.communityPjt.domain.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 출력하기(){
        List<Member> members = memberRepository.selectAll();
        for(Member member : members)
            System.out.println(member);
    }
}