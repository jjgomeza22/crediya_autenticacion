package co.com.crediya.r2dbc.repository;

import co.com.crediya.model.user.User;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    TransactionalOperator transactionalOperator;

    private final User user = User.builder()
            .name("juan")
            .lastname("gomez")
            .email("juan@mail.com")
            .documentId("123456")
            .phoneNumber("3154564097")
            .roleId(1)
            .baseSalary(new BigDecimal(2500000))
            .build();

    private final UserEntity userEntity = UserEntity.builder()
            .id_usuario(1)
            .name("juan")
            .lastname("gomez")
            .email("juan@mail.com")
            .documentId("123456")
            .phoneNumber("3154564097")
            .roleId(1)
            .build();

    @Test
    void mustSaveAnUser() {
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(Mockito.any(UserEntity.class))).thenReturn(Mono.just(userEntity));
        when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenReturn(Mono.just(userEntity));

        repositoryAdapter.saveUser(user)
                .as(StepVerifier::create)
                .expectNext("OK")
                .verifyComplete();
    }

    @Test
    void shouldFindUserByEmail() {

        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(repository.findByEmail(Mockito.anyString())).thenReturn(Mono.just(userEntity));

        repositoryAdapter.finByEmail(user.getEmail())
                .as(StepVerifier::create)
                .expectNextMatches(usr -> usr.getEmail().equals(user.getEmail()))
                .verifyComplete();
    }
}