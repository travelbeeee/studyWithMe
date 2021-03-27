package study.signUpUsingJDBC.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class Member {
    private Long id;
    private String username;
    private String userpwd;
    private String salt;
    private String email;
    private String auth;
}
