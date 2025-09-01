package co.com.crediya.api.handler;

import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.api.validator.RequestValidator;
import co.com.crediya.log.Log;
import co.com.crediya.log.Status;
import co.com.crediya.model.user.User;
import co.com.crediya.usecase.IUseCaseMono;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final IUseCaseMono<User, String> signUpNewUserUseCase;
    private final UserDtoMapper userDtoMapper;

    private static final String EVENT = "saveNewUser";

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADVISOR')")
    public Mono<ServerResponse> saveNewUser(ServerRequest request) {
        var endpoint = request.path();
        Log.logInfo(EVENT, this.getClass().getCanonicalName().concat(endpoint), Status.EXECUTED.name());
        return request.bodyToMono(SaveUserDto.class)
                .transform(RequestValidator.validate())
                .map(userDtoMapper::toModel)
                .flatMap(signUpNewUserUseCase::execute)
                .doOnNext(res -> Log.logInfo(EVENT, this.getClass().getCanonicalName().concat(endpoint), Status.FINALIZED.name()))
                .flatMap(ServerResponse.ok()::bodyValue)
                .doOnError(err -> Log.logError(EVENT, this.getClass().getCanonicalName().concat(endpoint), Status.ERROR.name(), new Exception(err)));
    }
}
