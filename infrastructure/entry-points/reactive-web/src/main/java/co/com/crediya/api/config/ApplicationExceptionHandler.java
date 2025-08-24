package co.com.crediya.api.config;

import co.com.crediya.api.exception.InvalidInputException;
import co.com.crediya.usecase.signupnewuser.exception.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Service
public class ApplicationExceptionHandler {
    public Mono<ServerResponse> handleException(InvalidInputException ex, ServerRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex, request, problemDetail -> problemDetail.setTitle("Invalid Input"));
    }

    public Mono<ServerResponse> handleException(DuplicateEmailException ex, ServerRequest request) {
        return handleException(HttpStatus.CONFLICT, ex, request, problemDetail -> problemDetail.setTitle("Invalid Email"));
    }

    private Mono<ServerResponse> handleException(HttpStatus status, Exception ex, ServerRequest request, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problem.setInstance(URI.create(request.path()));
        consumer.accept(problem);
        return ServerResponse.status(status).bodyValue(problem);
    }

}
