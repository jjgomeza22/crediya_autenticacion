package co.com.crediya.usecase.signupnewuser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SignUpNewUserUseCase {
    private final UserRepository userRepository;

    public Mono<Void> execute(User user) {
        return userRepository.saveUser(user);
    }
}
