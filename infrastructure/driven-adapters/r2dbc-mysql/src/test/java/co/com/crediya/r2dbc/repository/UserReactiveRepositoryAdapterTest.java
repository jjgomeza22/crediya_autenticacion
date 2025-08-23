package co.com.crediya.r2dbc.repository;

import co.com.crediya.model.user.User;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
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
        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);

        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));

        Mono<Void> result = repositoryAdapter.saveUser(user);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
