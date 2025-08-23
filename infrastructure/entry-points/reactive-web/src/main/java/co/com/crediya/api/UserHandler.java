package co.com.crediya.api;

import co.com.crediya.model.user.User;
import co.com.crediya.usecase.signupnewuser.SignUpNewUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final SignUpNewUserUseCase signUpNewUserUseCase;

    public Mono<ServerResponse> saveNewUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(signUpNewUserUseCase::execute)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
