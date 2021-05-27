package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.validate.ValidatorService;
import utils.exceptions.UnauthorizedException;

@Component
@Aspect
public class SecurityAspect {

    private Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    private ValidatorService validatorService;

    @Autowired
    public SecurityAspect(ValidatorService validatorService){
        this.validatorService = validatorService;
    }

    @Before("execution(@utils.security.Allowed * *(..))")
    public void validateEndpoint(JoinPoint joinPoint){
        logger.info("Entered in Before advice for method " + joinPoint.getSignature().getName());
        String username = (String) getParameterFromJoinPoint(joinPoint, "username");
        if(username == null)
            username = "admin";
        String token = (String) getParameterFromJoinPoint(joinPoint, "token");
        if(username == null || token == null || (!validatorService.areTokenAndUsernameValid(token, username))){
            throw new UnauthorizedException();
        }
    }

    private Object getParameterFromJoinPoint(JoinPoint joinPoint, String parameter) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] values = joinPoint.getArgs();

        for(int i = 0; i < names.length; i++){
            if(names[i].equals(parameter))
                return values[i];
        }
        return null;
    }

}
