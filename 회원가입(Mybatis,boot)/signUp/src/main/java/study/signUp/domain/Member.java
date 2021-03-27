package study.signUp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("member")
@Setter @Getter @ToString
public class Member {
    private Long id;
    private String username;
    private String userpwd;
    private String salt;
    private String email;
    private String auth;
}
