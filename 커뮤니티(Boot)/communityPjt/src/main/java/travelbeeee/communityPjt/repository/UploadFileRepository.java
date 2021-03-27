package travelbeeee.communityPjt.repository;

import org.apache.ibatis.annotations.Mapper;
import travelbeeee.communityPjt.domain.UploadFile;

@Mapper
public interface UploadFileRepository {
    int insert(UploadFile files);
    int update(UploadFile files);
    int delete(Long uploadFileId);
    UploadFile select(Long uploadFileId);
}
