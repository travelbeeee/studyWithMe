package travelbeeee.communityPjt.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter @Getter @ToString
public class WritingForm {
    String title;
    String content;
    MultipartFile files;
}
