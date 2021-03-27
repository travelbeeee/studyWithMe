package travelbeeee.communityPjt.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("uploadfile")
public class UploadFile {
    private Long uploadFileId;
    private String originFileName;
    private String changedFileName;
    private String location;
}
