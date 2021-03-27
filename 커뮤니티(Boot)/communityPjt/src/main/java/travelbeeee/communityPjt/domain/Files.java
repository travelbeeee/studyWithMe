package travelbeeee.communityPjt.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("file")
public class Files {
    Long filesId;
    String originFileName;
    String changedFileName;
    String location;
}
