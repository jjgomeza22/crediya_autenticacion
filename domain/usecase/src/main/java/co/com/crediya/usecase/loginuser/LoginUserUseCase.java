package co.com.crediya.usecase.loginuser;

import co.com.crediya.model.login.Login;
import co.com.crediya.model.password.gateways.PasswordEncoderPort;
import co.com.crediya.model.token.TokenResponse;
import co.com.crediya.model.token.gateways.TokenProviderPort;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.IUseCaseMono;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUserUseCase implements IUseCaseMono<Login, TokenResponse> {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProviderPort tokenProvider;

    @Override
    public Mono<TokenResponse> execute(Login request) {
        return userRepository.finByEmail(request.getEmail())
                .filter(usr -> passwordEncoder.matches(request.getPassword(), usr.getPassword()))
                .switchIfEmpty(Mono.error(new Throwable("bad credentials")))
                .flatMap(tokenProvider::generateToken)
                .map(TokenResponse::new);
    }
}
