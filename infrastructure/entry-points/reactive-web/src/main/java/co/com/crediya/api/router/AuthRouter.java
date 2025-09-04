package co.com.crediya.api.router;

import co.com.crediya.api.config.AuthPath;
import co.com.crediya.api.dto.LoginDto;
import co.com.crediya.api.handler.AuthHandler;
import co.com.crediya.model.token.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AuthRouter {
    private final AuthPath authPath;

    @Bean
    @RouterOperation(
            path = "/login",
            method = RequestMethod.POST,
            beanClass = AuthHandler.class,
            beanMethod = "loginUser",
            operation = @Operation(
                    operationId = "loginUser",
                    summary = "User login",
                    description = "Authenticates an user and returns a JWT token.",
                    parameters = {
                            @Parameter(
                                    in = ParameterIn.HEADER,
                                    name = HttpHeaders.AUTHORIZATION,
                                    description = "Authentication token (Bearer <token>)",
                                    required = true,
                                    schema = @Schema(type = "string")
                            )
                    },
                    requestBody = @RequestBody(
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoginDto.class)
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Ok: User has been logged in successfully",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TokenResponse.class)
                                    )
                            ),
                            @ApiResponse(
                                    responseCode = "401",
                                    description = "Conflict: Incorrect credentials",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            examples = @ExampleObject(
                                                    value = """
                                                            {
                                                              "type": "about:blank",
                                                              "title": "Invalid Input",
                                                              "status": 400,
                                                              "detail": "Email should have a correct format",
                                                              "instance": "/login"
                                                            }
                                                            """
                                            )
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> authRouterFunction(AuthHandler handler) {
        return route()
                .POST(authPath.getLogin(), handler::loginUser)
                .build();
    }
}
