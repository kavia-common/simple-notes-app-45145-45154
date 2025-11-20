package com.example.notesbackend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Tag(name = "Hello Controller", description = "Basic endpoints for notesbackend")
public class HelloController {

    @GetMapping("/")
    @Operation(summary = "Welcome endpoint", description = "Returns a welcome message")
    public String hello() {
        return "Hello, Spring Boot! Welcome to notesbackend";
    }

    @GetMapping("/docs")
    @Operation(summary = "API Documentation", description = "Redirects to Swagger UI preserving original scheme/host/port")
    public RedirectView docs(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isBlank()) scheme = request.getScheme();

        String hostHeader = request.getHeader("X-Forwarded-Host");
        String hostPort = (hostHeader != null && !hostHeader.isBlank()) ? hostHeader : request.getServerName() + (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort() : "");
        String contextPath = request.getContextPath() != null ? request.getContextPath() : "";

        String target = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(hostPort.contains(":") ? hostPort.split(":")[0] : hostPort)
                .port(hostPort.contains(":") ? hostPort.split(":")[1] : null)
                .path(contextPath + "/swagger-ui.html")
                .build()
                .toUriString();

        RedirectView rv = new RedirectView(target);
        rv.setHttp10Compatible(false);
        return rv;
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns application health status")
    public String health() {
        return "OK";
    }

    @GetMapping("/api/info")
    @Operation(summary = "Application info", description = "Returns application information")
    public String info() {
        return "Spring Boot Application: notesbackend";
    }
}