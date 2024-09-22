package org.example.apigateway.filter;

import org.apache.http.HttpHeaders;
import org.example.apigateway.exception.ThereIsNoTokenException;
import org.example.apigateway.exception.TokenIsNotValidException;
import org.example.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    throw new ThereIsNoTokenException("No token found in the request");
                    return onError(exchange, "No token found in the request", 401);
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    // REST call to AUTH Service
                    // TODO
//                    template.getForEntity("http://AUTH-SERVICE/auth/validate?token=" + authHeader, String.class);
                    // Теперь не нужно каждый запрос отправлять на AUTH-SERVICE
                    jwtUtil.validateToken(authHeader);

                } catch (Exception e) {
//                    throw new TokenIsNotValidException("un authorized access to application");
                    return onError(exchange, "Unauthorized access to the application", 401);
                }

            }
            return chain.filter(exchange);
        }));
    }


    // Метод для обработки ошибок и возврата ответа с нужным статусом
    private Mono<Void> onError(ServerWebExchange exchange, String err, int httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.valueOf(httpStatus));

        // Логируем ошибку
        System.out.println(err);

        // Завершаем запрос с пустым телом
        return response.setComplete();
    }


    public static class Config {

    }

}
