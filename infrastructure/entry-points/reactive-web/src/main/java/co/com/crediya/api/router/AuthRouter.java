package co.com.crediya.api.router;

import co.com.crediya.api.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {
    @Bean
    public RouterFunction<ServerResponse> authRouterFunction(AuthHandler handler) {
        return route()
                .POST("/auth/login", handler::loginUser)
                .build();
    }
}
