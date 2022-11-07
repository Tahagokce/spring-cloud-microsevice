package com.verseup.accountservice.aspectj;

import com.verseup.accountservice.config.i18n.LocalMessageResolver;
import com.verseup.accountservice.exception.CustomException;
import com.verseup.accountservice.modal.GenericResponse;
import com.verseup.accountservice.modal.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RestControllerLocalizeMessageAspect {

    private final LocalMessageResolver localMessageResolver;

    public RestControllerLocalizeMessageAspect(LocalMessageResolver localMessageResolver) {
        this.localMessageResolver = localMessageResolver;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    protected void controllerPointCut() {
        throw new UnsupportedOperationException("controllerPointCut not implemented.");
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
        throw new UnsupportedOperationException("allMethod not implemented.");
    }

    @Around("controllerPointCut() && allMethod()")
    public Object localizeResponseMessage(ProceedingJoinPoint proceedingJoinPoint) {
        GenericResponse<?> response;
        try {
            response = (GenericResponse<?>) proceedingJoinPoint.proceed();
            ResponseMessage message = response.getMessage();
            String code = message.getCode();
            if (!StringUtils.isEmpty(code)) {
                String localizedMessage = getLocalizeMessage(code);
                if (!StringUtils.isEmpty(localizedMessage)) {
                    response.getMessage().setDefaultMessage(localizedMessage);
                }
            }
        } catch (Throwable throwable) {
            response = createResponse(throwable);
        }
        return response;
    }

    private String getLocalizeMessage(String code, Object... params) {
        return localMessageResolver.getMessage(LocaleContextHolder.getLocale().getLanguage(), code, params);
    }

    private GenericResponse<?> createResponse(Throwable throwable) {
        GenericResponse<?> response = GenericResponse.bad();
        log.info("Bad request, caused by  {}", throwable.getMessage(), throwable);
        String localizeMessage;
        if (throwable instanceof CustomException) {
            localizeMessage = getLocalizeMessage(throwable.getMessage(), ((CustomException) throwable).getParams());
        } else {
            localizeMessage = getLocalizeMessage(throwable.getMessage());
        }

        response.getMessage().setDefaultMessage(!StringUtils.isEmpty(localizeMessage) ? localizeMessage : throwable.getMessage());
        return response;
    }

}
