package co.com.crediya.api;

import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.api.validator.RequestValidator;
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
    private final UserDtoMapper userDtoMapper;

    public Mono<ServerResponse> saveNewUser(ServerRequest request) {
        return request.bodyToMono(SaveUserDto.class)
                .transform(RequestValidator.validate())
                .map(userDtoMapper::toModel)
                .flatMap(signUpNewUserUseCase::execute)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
