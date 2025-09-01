package co.com.crediya.usecase.loginuser;

import co.com.crediya.model.login.Login;
import co.com.crediya.model.password.gateways.PasswordEncoderPort;
import co.com.crediya.model.role.RoleName;
import co.com.crediya.model.token.gateways.TokenProviderPort;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.exception.InvalidCredentialsException;
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
class LoginUserUseCaseTest {
    @InjectMocks
    LoginUserUseCase loginUserUseCase;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoderPort passwordEncoder;
    @Mock
    TokenProviderPort tokenProvider;

    @Test
    void shouldLoginAnUser() {
        var user = User.builder()
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

        Mockito.when(userRepository.finByEmail(Mockito.anyString())).thenReturn(Mono.just(user));
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenProvider.generateToken(Mockito.any(User.class))).thenReturn(Mono.just("token"));

        var loginDto = Login.builder()
                .email("juan@mail.com")
                .password("123456")
                .build();

        loginUserUseCase.execute(loginDto)
                .as(StepVerifier::create)
                .expectNextMatches(tokenRes -> tokenRes.getToken().equalsIgnoreCase("token"))
                .verifyComplete();

    }

    @Test
    void shouldReturnInvalidCredentialsException() {
        Mockito.when(userRepository.finByEmail(Mockito.anyString())).thenReturn(Mono.empty());

        var loginDto = Login.builder()
                .email("juan@mail.com")
                .password("123456")
                .build();

        loginUserUseCase.execute(loginDto)
                .as(StepVerifier::create)
                .expectError(InvalidCredentialsException.class)
                .verify();

    }

}