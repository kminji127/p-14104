package com.back.global.globalExceptionHandler;

import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * 전역 예외 핸들러
 **/
@RestControllerAdvice // 컨트롤러 수준에서 발생한 예외 명시적 처리 (Rest 붙이면 @ResponseBody 필요없음)
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class) // 처리할 예외
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RsData<Void> handle() {
        return new RsData<>(
                "404-1",
                "해당 데이터가 존재하지 않습니다."
        );
    }
}
