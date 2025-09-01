package co.com.crediya.usecase.signupnewuser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.exception.DuplicateEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class SignUpNewUserUseCaseTest {
    @InjectMocks
    SignUpNewUserUseCase signUpNewUserUseCase;

    @Mock
    UserRepository userRepository;

    private final User user = User.builder()
            .name("juan")
            .lastname("gomez")
            .email("juan@mail.com")
            .documentId("10947832854")
            .phoneNumber("3146576454")
            .roleId(1)
            .baseSalary(new BigDecimal(8500000))
            .birthDay(LocalDate.of(1999, 5, 22))
            .address("Las Mercedes")
            .build();

    @Test
    void shouldSignUpNewUser() {
        Mockito.when(userRepository.finByEmail(Mockito.anyString())).thenReturn(Mono.empty());
        Mockito.when(userRepository.saveUser(Mockito.any(User.class))).thenReturn(Mono.just("OK"));

        signUpNewUserUseCase.execute(user)
                .as(StepVerifier::create)
                .expectNext("OK")
                .verifyComplete();
    }

    @Test
    void shouldReturnDuplicateEmailException() {
        Mockito.when(userRepository.finByEmail(Mockito.anyString())).thenReturn(Mono.just(user));
        Mockito.when(userRepository.saveUser(Mockito.any(User.class))).thenReturn(Mono.just("OK"));

        signUpNewUserUseCase.execute(user)
                .as(StepVerifier::create)
                .expectError(DuplicateEmailException.class)
                .verify();
    }
}