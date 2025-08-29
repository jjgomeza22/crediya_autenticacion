package co.com.crediya.api.handler;

import co.com.crediya.api.dto.LoginDto;
import co.com.crediya.api.mapper.LoginDtoMapper;
import co.com.crediya.log.Log;
import co.com.crediya.log.Status;
import co.com.crediya.model.login.Login;
import co.com.crediya.model.token.TokenResponse;
import co.com.crediya.usecase.IUseCaseMono;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {
    private final IUseCaseMono<Login, TokenResponse> loginUserUseCase;
    private final LoginDtoMapper loginDtoMapper;

    private static final String EVENT = "loginUser";

    public Mono<ServerResponse> loginUser(ServerRequest request) {
        var endpoint = request.path();
        Log.logInfo(EVENT, this.getClass().getCanonicalName().concat(endpoint), Status.EXECUTED.name());
        return request.bodyToMono(LoginDto.class)
                .map(loginDtoMapper::toModel)
                .flatMap(loginUserUseCase::execute)
                .doOnNext(res -> Log.logInfo(EVENT, this.getClass().getCanonicalName().concat(endpoint), Status.FINALIZED.name()))
                .flatMap(ServerResponse.ok()::bodyValue)
                .doOnError(err -> Log.logError(EVENT, this.getClass().getCanonicalName().concat(endpoint), Status.ERROR.name(), new Exception(err)));
    }
}
