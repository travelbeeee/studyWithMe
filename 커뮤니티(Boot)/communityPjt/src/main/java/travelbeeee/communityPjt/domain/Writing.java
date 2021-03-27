package travelbeeee.communityPjt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@Alias("writing")
public class Writing {
    Long writingId;
    Long memberId;
    Long uploadFileId;
    String title;
    String content;
    LocalDateTime writingTime;
    Long views;
}
