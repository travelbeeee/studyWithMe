package study.signUpUsingJDBC.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class SignUpForm {
    private String username;
    private String userpwd;
    private String email;
}
