package co.com.crediya.api.router;

import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.api.dto.UserByEmailDto;
import co.com.crediya.api.handler.UserHandler;
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
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserRouter {
    private final UserPath userPath;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "saveNewUser",
                    operation = @Operation(
                            operationId = "saveNewUser",
                            summary = "Create a new user",
                            description = "Create a new user with input data.",
                            requestBody = @RequestBody(
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = SaveUserDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Ok: User has been created successful",
                                            content = @Content(schema = @Schema(implementation = String.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "409",
                                            description = "Conflict: Email already exists"
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request: Incorrect Input Data",
                                            content = @Content(
                                                    examples = @ExampleObject(
                                                            value = """
                                                                    {
                                                                      "type": "about:blank",
                                                                      "title": "Invalid Input",
                                                                      "status": 400,
                                                                      "detail": "Email should have a correct format",
                                                                      "instance": "/api/v1/usuarios"
                                                                    }
                                                                    """
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "getUsersByEmail",
                    operation = @Operation(
                            operationId = "getUsersByEmail",
                            summary = "Retrieve users by email",
                            description = "Retrieves a list of users based on their email addresses. Emails must be provided as a comma-separated list.",
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.QUERY,
                                            name = "emails",
                                            description = "A comma-separated list of email addresses.",
                                            required = true,
                                            schema = @Schema(type = "string"),
                                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                    name = "Multiple emails",
                                                    value = "user1@example.com,user2@example.com"
                                            )
                                    )
                            },
                            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "BearerAuth"),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "OK: Users retrieved successfully.",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = UserByEmailDto.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Unauthorized: Authentication required."
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: User does not have the 'ADVISOR' authority."
                                    ),
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
        return route()
                .GET("/", req -> ServerResponse.permanentRedirect(URI.create("/swagger-ui.html")).build())
                .GET(userPath.getUsers(), handler::getUsersByEmail)
                .POST(userPath.getUsers(), handler::saveNewUser)
                .build();
    }
}
