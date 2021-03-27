package travelbeeee.communityPjt.exception;

import travelbeeee.communityPjt.myenum.MyExceptionEnum;

public class MemberException extends Exception{
    MyExceptionEnum exception;
    public MemberException(MyExceptionEnum exception){
        this.exception = exception;
    }

    public MyExceptionEnum getException() {
        return exception;
    }
}
