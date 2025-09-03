package co.com.crediya.usecase.getusersbyemail;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
public class GetUsersByEmailUseCase {
    private final UserRepository userRepository;

    public Flux<User> execute(List<String> emails) {
        return userRepository.findUsersByEmail(emails);
    }
}
