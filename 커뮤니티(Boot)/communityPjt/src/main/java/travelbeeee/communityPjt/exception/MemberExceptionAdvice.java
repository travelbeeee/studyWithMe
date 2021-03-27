package travelbeeee.communityPjt.exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import travelbeeee.communityPjt.controller.MemberController;

@ControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(MemberExceptionAdvice.class);

    /* common메소드는  Exception 타입으로 처리하는 모든 예외를 처리하도록 설정 */
	@ExceptionHandler(Throwable.class)
    public ModelAndView common(ModelAndView mav, Throwable e) {
        mav.setViewName("/errors/error_common");
        mav.addObject("exception", e);  //예외를 뷰에 던져서 주자.
        return mav;
        
    }

    @ExceptionHandler(MemberException.class)
    public ModelAndView memberException(ModelAndView mav, MemberException e){
        mav.setViewName("/errors/error_common");
	    mav.addObject("errorCode", e.getException().getErrorCode());

	    return mav;
    }
}
