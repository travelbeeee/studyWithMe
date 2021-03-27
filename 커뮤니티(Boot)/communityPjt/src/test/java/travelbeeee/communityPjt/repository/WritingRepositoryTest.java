package travelbeeee.communityPjt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelbeeee.communityPjt.domain.Writing;

import java.util.List;


@SpringBootTest
class WritingRepositoryTest {

    @Autowired
    WritingRepository writingRepository;

    @Test
    void 출력하기(){
        List<Writing> writings = writingRepository.selectAll();
        for(Writing writing : writings)
            System.out.println(writing.toString());
    }
}