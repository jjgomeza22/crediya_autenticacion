package co.com.crediya.usecase.signupnewuser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.signupnewuser.exception.ApplicationExceptions;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
public class SignUpNewUserUseCase {
    private final UserRepository userRepository;

    public Mono<Void> execute(User user) {
        return userRepository.finByEmail(user.getEmail())
                .filter(usr -> !Objects.nonNull(usr.getEmail()))
                .switchIfEmpty(ApplicationExceptions.emailAlreadyExist(user.getEmail()))
                .flatMap(usr -> userRepository.saveUser(user));
    }
}
