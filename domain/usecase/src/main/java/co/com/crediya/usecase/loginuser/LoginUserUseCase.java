package co.com.crediya.usecase.loginuser;

import co.com.crediya.model.login.Login;
import co.com.crediya.model.token.TokenResponse;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.IUseCaseMono;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUserUseCase implements IUseCaseMono<Login, TokenResponse> {
    private final UserRepository userRepository;

    @Override
    public Mono<TokenResponse> execute(Login request) {
        return userRepository.finByEmail(request.getEmail())
                .doOnNext(res -> res.getEmail())
                .map(res -> new TokenResponse("tokens"));
    }
}
