package com.back.domain.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
@Tag(name = "HomeController", description = "홈 컨트롤러")
public class HomeController {
    // 체크 예외(checked exception)를 언체크 예외(unchecked exception)로 변환 => 예외 처리 간소화, 코드 가독성 향상 목적
    @SneakyThrows
    @GetMapping(produces = TEXT_HTML_VALUE)
    @Operation(summary = "메인 페이지")
    public String main() {
        InetAddress localHost = InetAddress.getLocalHost();
        return """
                <h1>API 서버</h1>
                <p>Host Name: %s</p>
                <p>Host Address: %s</p>
                <div>
                    <a href="/swagger-ui/index.html">API 문서로 이동</a>
                </div>
                """.formatted(localHost.getHostName(), localHost.getHostAddress());
    }
}
