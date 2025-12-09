package com.back.global.aspect;

import com.back.global.rsData.RsData;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP(ResponseAspect)를 이용해서
 * 액션메서드가 RsData를 리턴할 때
 * resultCode를 참고하여 HTTP 응답코드를 변경
 */
@Aspect // 직접 AOP 로직 구현
@Component
public class ResponseAspect {
    private final HttpServletResponse response;

    public ResponseAspect(HttpServletResponse response) {
        this.response = response;
    }

    @Around("""
                execution(public com.back.global.rsData.RsData *(..)) &&
                (
                    within(@org.springframework.stereotype.Controller *) ||
                    within(@org.springframework.web.bind.annotation.RestController *)
                ) &&
                (
                    @annotation(org.springframework.web.bind.annotation.GetMapping) ||
                    @annotation(org.springframework.web.bind.annotation.PostMapping) ||
                    @annotation(org.springframework.web.bind.annotation.PutMapping) ||
                    @annotation(org.springframework.web.bind.annotation.DeleteMapping) ||
                    @annotation(org.springframework.web.bind.annotation.RequestMapping)
                )
            """)
    public Object handleResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();

        RsData<?> rsData = (RsData<?>) proceed;
        response.setStatus(rsData.statusCode());

        return proceed;
    }
}