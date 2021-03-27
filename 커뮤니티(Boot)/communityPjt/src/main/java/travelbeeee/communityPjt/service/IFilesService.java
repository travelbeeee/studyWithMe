package travelbeeee.communityPjt.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import travelbeeee.communityPjt.domain.UploadFile;
import travelbeeee.communityPjt.repository.UploadFileRepository;
import travelbeeee.communityPjt.util.Sha256Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;

@Service @RequiredArgsConstructor
public class IFilesService implements FileService{

    private final UploadFileRepository uploadFileRepository;
    private final Sha256Util sha256Util;
    private String uploadDir = "C:\\Users\\HyunSeok\\Desktop\\studyWithMe\\springPractice\\커뮤니티(Boot)\\files";
    Logger logger = LoggerFactory.getLogger(IFilesService.class);

    @Override
    public Long fileUpload(MultipartFile multipartFile) throws NoSuchAlgorithmException {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOriginFileName(multipartFile.getOriginalFilename());
        uploadFile.setChangedFileName(sha256Util.sha256(uploadFile.getOriginFileName(), sha256Util.makeSalt()));
        uploadFile.setLocation(uploadDir + File.separator + StringUtils.cleanPath(uploadFile.getChangedFileName()));
        // File.seperator 는 OS종속적이다.
        // Spring에서 제공하는 cleanPath()를 통해서 ../ 내부 점들에 대해서 사용을 억제한다
        Path copyOfLocation = Paths.get(uploadFile.getLocation());

        Long res = 0L;
        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다
            Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
            uploadFileRepository.insert(uploadFile);
            res = uploadFile.getUploadFileId() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public File fileDownload(Long uploadFileId) {
        UploadFile uploadFile = uploadFileRepository.select(uploadFileId);
        String fileName = uploadFile.getChangedFileName();
        File file = new File(uploadDir + "/" + fileName);
        return file;
    }

    @Override
    public UploadFile getUploadFileById(Long uploadFileId){
        return uploadFileRepository.select(uploadFileId);
    }

    @Override
    public void deleteUploadFileById(Long uploadFileId){
        UploadFile uploadFile = uploadFileRepository.select(uploadFileId);
        File file = new File(uploadDir + "/" + uploadFile.getChangedFileName());
        if(file.exists())
            file.delete();
        uploadFileRepository.delete(uploadFileId);
        return;
    }
}
