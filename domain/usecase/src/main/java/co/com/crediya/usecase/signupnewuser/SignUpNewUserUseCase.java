package co.com.crediya.usecase.signupnewuser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.IUseCaseMono;
import co.com.crediya.usecase.signupnewuser.exception.ApplicationExceptions;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SignUpNewUserUseCase implements IUseCaseMono<User, Void> {
    private final UserRepository userRepository;

    public Mono<Void> execute(User user) {
        return userRepository.finByEmail(user.getEmail())
                .flatMap(existingUsr -> ApplicationExceptions.emailAlreadyExist(user.getEmail()))
                .switchIfEmpty(userRepository.saveUser(user))
                .then();
    }
}
