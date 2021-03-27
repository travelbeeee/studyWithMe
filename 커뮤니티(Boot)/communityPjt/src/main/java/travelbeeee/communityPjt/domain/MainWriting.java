package travelbeeee.communityPjt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class MainWriting {
    Long writingId;
    Long uploadFileId;
    String username;
    String title;
    String content;
    LocalDateTime writingTime;
    Long views;
}
