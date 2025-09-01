package co.com.crediya.r2dbc.repository;

import co.com.crediya.log.Log;
import co.com.crediya.log.Status;
import co.com.crediya.model.role.RoleName;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        Integer,
        UserReactiveRepository
        > implements UserRepository {

    private final TransactionalOperator transactionalOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<String> saveUser(User user) {
        var method = "saveUser";
        Log.logInfo(method, this.getClass().getCanonicalName(), Status.EXECUTED.name());
        return repository.save(toData(user))
                .doOnNext(usr -> Log.logInfo(method, this.getClass().getCanonicalName(), Status.FINALIZED.name()))
                .doOnError(err -> Log.logError(method, this.getClass().getCanonicalName(), Status.ERROR.name(), new Exception(err)))
                .as(transactionalOperator::transactional)
                .then(Mono.just("OK"));
    }

    @Override
    public Mono<User> finByEmail(String email) {
        var method = "finByEmail";
        Log.logInfo(method, this.getClass().getCanonicalName(), Status.EXECUTED.name());
        return repository.findByEmail(email)
                .map(usr -> {
                    var user = toEntity(usr);
                    user.setRole(RoleName.fromId(usr.getRoleId()));
                    return user;
                })
                .doOnNext(usr -> Log.logInfo(method, this.getClass().getCanonicalName(), Status.FINALIZED.name()))
                .doOnError(err -> Log.logError(method, this.getClass().getCanonicalName(), Status.ERROR.name(), new Exception(err)));
    }
}
