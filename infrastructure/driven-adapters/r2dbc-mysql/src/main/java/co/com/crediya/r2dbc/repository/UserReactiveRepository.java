package co.com.crediya.r2dbc.repository;

import co.com.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Integer>, ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<UserEntity> findByEmail(String email);

    Flux<UserEntity> findByEmailIn(List<String> emails);
}
