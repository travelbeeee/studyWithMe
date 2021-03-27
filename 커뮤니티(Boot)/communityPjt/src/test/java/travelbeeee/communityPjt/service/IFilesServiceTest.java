package travelbeeee.communityPjt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IFilesServiceTest {
    @Autowired
    FileService fileService;

    @Test
    void 파일가져오기(){
        File file = fileService.fileDownload(11L);
        System.out.println(file);
    }
}