package travelbeeee.communityPjt.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
public class MemberValidatorTest {

    @Test
    void 아이디정규식테스트(){ // 아이디는 영어로 시작하고, 영어와 숫자만 들어간 5~15자
        Pattern pattern = Pattern.compile("^(?=.*[0-9])[a-zA-z]{1}[a-zA-Z0-9]{4,14}$");

        String val1 = "sochun1518"; //통과
        String val2 = "sochdfun"; // 숫자 미포함이라 불통과
        String val3 = "1sodasj"; // 시작이 숫자라 불통과
        String val4 = "skjads233_"; // 특수문자로 불통과
        String val5 = "sochun1518@"; // 특수문자로 불통과
        String val6 = "ab3"; // 길이 부족으로 불통과

        Assertions.assertThat(pattern.matcher(val1).find()).isTrue();
        Assertions.assertThat(pattern.matcher(val2).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val3).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val4).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val5).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val6).find()).isFalse();
    }

    @Test
    void 비밀번호정규식테스트(){ // 비밀번호는 영어로 시작하고, 영어, 숫자, 특수문자(!@#$%)가 1개씩은 포함된 8 - 20자
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%])[a-zA-z]{1}[a-zA-Z0-9!@#$%]{7,19}$");

        String val1 = "testPassword12#"; //통과
        String val2 = "12testPassword#"; // 불통과
        String val3 = "testPassword"; // 불통과
        String val4 = "testPassword#"; // 불통과
        String val5 = "#testPassword1"; // 불통과
        String val6 = "t123est#pass^word"; // 통과

        Assertions.assertThat(pattern.matcher(val1).find()).isTrue();
        Assertions.assertThat(pattern.matcher(val2).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val3).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val4).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val5).find()).isFalse();
        Assertions.assertThat(pattern.matcher(val6).find()).isTrue();
    }
}
