package org.example.apigateway.filter;

import org.apache.http.HttpHeaders;
import org.example.apigateway.exception.ThereIsNoTokenException;
import org.example.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private RestTemplate template;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ThereIsNoTokenException("No token found in the request");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    // REST call to AUTH Service
                    // TODO
//                    template.getForEntity("http://AUTH-SERVICE/auth/validate?token=" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);



                } catch (Exception e) {
                    System.out.println("Invalid access..!");
                    throw new RuntimeException("un authorized access to application");
                }

            }

            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }

}
