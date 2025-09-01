package co.com.crediya.usecase.signupnewuser;

import co.com.crediya.model.password.gateways.PasswordEncoderPort;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.IUseCaseMono;
import co.com.crediya.usecase.exception.ApplicationExceptions;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SignUpNewUserUseCase implements IUseCaseMono<User, String> {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public Mono<String> execute(User user) {
        return userRepository.finByEmail(user.getEmail())
                .flatMap(existingUsr -> ApplicationExceptions.emailAlreadyExist(user.getEmail()))
                .switchIfEmpty(Mono.defer(() -> userRepository.saveUser(encodePassword(user))))
                .cast(String.class);
    }

    private User encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
