package travelbeeee.communityPjt.myenum;

import java.lang.reflect.Type;

public enum MyExceptionEnum {
    SIGNUP_ERROR("아이디, 이메일, 비밀번호를 양식에 맞게 입력해주세요."),
    LOGIN_ERROR("아이디 / 비밀번호를 잘못 입력하셨습니다.");

    String errorCode;

    MyExceptionEnum (String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }
}
