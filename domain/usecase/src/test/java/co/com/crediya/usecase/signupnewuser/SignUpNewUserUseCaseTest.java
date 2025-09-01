package co.com.crediya.usecase.signupnewuser;

import co.com.crediya.model.password.gateways.PasswordEncoderPort;
import co.com.crediya.model.role.RoleName;
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

    @Mock
    PasswordEncoderPort passwordEncoder;

    private final User user = User.builder()
            .name("juan")
            .lastname("gomez")
            .email("juan@mail.com")
            .documentId("10947832854")
            .phoneNumber("3146576454")
            .role(RoleName.ADMIN)
            .baseSalary(new BigDecimal(8500000))
            .birthDay(LocalDate.of(1999, 5, 22))
            .address("Las Mercedes")
            .password("123456")
            .build();

    @Test
    void shouldSignUpNewUser() {
        Mockito.when(userRepository.finByEmail(Mockito.anyString())).thenReturn(Mono.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("$2a$10$ZsLC0s.81fSiLjwJPTn01Onz0.s01ZKq.P32dDQJM6YaMPacklbCu");
        Mockito.when(userRepository.saveUser(Mockito.any(User.class))).thenReturn(Mono.just("OK"));

        signUpNewUserUseCase.execute(user)
                .as(StepVerifier::create)
                .expectNext("OK")
                .verifyComplete();
    }

    @Test
    void shouldReturnDuplicateEmailException() {
        Mockito.when(userRepository.finByEmail(Mockito.anyString())).thenReturn(Mono.just(user));

        signUpNewUserUseCase.execute(user)
                .as(StepVerifier::create)
                .expectError(DuplicateEmailException.class)
                .verify();
    }
}