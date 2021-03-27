package travelbeeee.communityPjt.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


@Setter @Getter @ToString
public class SignUpForm {
    @Pattern(regexp="^(?=.*[0-9])[a-zA-z]{1}[a-zA-Z0-9]{4,14}$", message = "아이디는 영어로 시작하고, 5 ~ 15자의 영어/숫자로 이루어져야합니다.")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%])[a-zA-z]{1}[a-zA-Z0-9!@#$%]{7,19}$", message = "비밀번호는 영어로 시작하고, 8 ~ 20자의 영어/숫자/특수문자(!@#$%)로 이루어져야합니다.")
    private String userpwd;
    @Email
    private String email;
}
