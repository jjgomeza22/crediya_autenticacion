package co.com.crediya.api;

import co.com.crediya.api.config.ApplicationExceptionHandler;
import co.com.crediya.api.exception.InvalidInputException;
import co.com.crediya.usecase.signupnewuser.exception.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Autowired
    private ApplicationExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route()
                .POST("/api/v1/usuarios", handler::saveNewUser)
                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
                .onError(DuplicateEmailException.class, this.exceptionHandler::handleException)
                .build();
    }
}
