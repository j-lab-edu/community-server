package com.gamecommunityserver.aop;

import com.gamecommunityserver.utils.SessionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * TODO :
 */
@Aspect
@Component
public class LoginCheckAspect {
    @Around("@annotation(com.gamecommunityserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object LoginSessionCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable{
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        int userNumber = 0;
        int index = 0;

        for(int i=0; i< loginCheck.types().length && (userNumber == 0); i++)
        {
            switch( loginCheck.types()[i].toString())
            {
                case "USER":
                    userNumber = SessionUtils.getLoginUserNumber(session);
                    break;
                case "ADMIN":
                    userNumber = SessionUtils.getAdminLoginUserNumber(session);
                    break;
            }
        }


        if(userNumber == 0)
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요"){};
        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if(proceedingJoinPoint.getArgs()!=null)
            modifiedArgs[index] = userNumber;
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}
