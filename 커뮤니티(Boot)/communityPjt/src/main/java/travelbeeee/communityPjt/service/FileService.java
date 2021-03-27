package travelbeeee.communityPjt.service;

import org.springframework.web.multipart.MultipartFile;
import travelbeeee.communityPjt.domain.UploadFile;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public interface FileService {
    Long fileUpload(MultipartFile multipartFile) throws NoSuchAlgorithmException; // 파일업로드하고 파일id반환
    File fileDownload(Long uploadFileId); // 파일 다운로드
    UploadFile getUploadFileById(Long uploadFileId);
    void deleteUploadFileById(Long uploadFileId); // DB에서 삭제하고, 실제 파일도 삭제하고
}
