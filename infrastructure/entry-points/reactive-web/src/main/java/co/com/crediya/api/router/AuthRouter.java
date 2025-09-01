package co.com.crediya.api.router;

import co.com.crediya.api.config.ApplicationExceptionHandler;
import co.com.crediya.api.handler.AuthHandler;
import co.com.crediya.security.exception.InvalidAuthException;
import co.com.crediya.usecase.exception.InvalidCredentialsException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {
    @Bean
    public RouterFunction<ServerResponse> authRouterFunction(AuthHandler handler, ApplicationExceptionHandler exceptionHandler) {
        return route()
                .POST("/login", handler::loginUser)
                .onError(InvalidAuthException.class, exceptionHandler::handleException)
                .onError(InvalidCredentialsException.class, exceptionHandler::handleException)
                .build();
    }
}
