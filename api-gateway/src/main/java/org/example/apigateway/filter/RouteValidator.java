package org.example.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",
            "/auth/validate", // добавил
            "/eureka"
//            "/v3/api-docs",
//            "/book-service/v3/api-docs",
//            "/library-service/v3/api-docs",

//            "/swagger-resources/**",
//            "/swagger-ui/**",
//            "/swagger-ui.html",
//            "/swagger-ui/index.html"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
