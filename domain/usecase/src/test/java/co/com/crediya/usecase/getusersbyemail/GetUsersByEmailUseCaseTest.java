package co.com.crediya.usecase.getusersbyemail;

import co.com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetUsersByEmailUseCaseTest {
    @InjectMocks
    GetUsersByEmailUseCase getUsersByEmailUseCase;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldGetOneMail() {
        Mockito.when(userRepository.findUsersByEmail(Mockito.any(List.class))).thenReturn(Flux.empty());

        getUsersByEmailUseCase.execute(List.of("juan@mail.com"))
                .as(StepVerifier::create)
                .verifyComplete();

    }
}