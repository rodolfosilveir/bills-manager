package br.com.totvs.bills_manager.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import br.com.totvs.bills_manager.adapter.in.rest.response.DefaultResponse;
import br.com.totvs.bills_manager.domain.exception.BillNotFoundException;
import br.com.totvs.bills_manager.domain.exception.ImportCsvException;
import br.com.totvs.bills_manager.domain.exception.InvalidSituationException;
import br.com.totvs.bills_manager.domain.exception.RoleNotFoundException;
import br.com.totvs.bills_manager.domain.exception.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class RestResponseExceptionHandlerControllerTest {

    private RestResponseExceptionHandlerController exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new RestResponseExceptionHandlerController();
    }

    @ParameterizedTest
    @MethodSource("provideBadRequestExceptions")
    @DisplayName("Deve lidar com Erros de BadRequest e retornar uma resposta de erro")
    void shouldHandleBadRequestsExceptionsTest(RuntimeException exception) {
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<DefaultResponse<Object>> response = exceptionHandler.handleBadRequestValidationExceptions(exception, request);

        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve lidar com MethodArgumentNotValidException e retornar uma resposta de erro")
    void shouldHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        List<ObjectError> errors = Arrays.asList(new ObjectError("field", "Erro de validação"));
        when(bindingResult.getAllErrors()).thenReturn(errors);

        @SuppressWarnings("null")
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> response = exceptionHandler.handleArgumentsInvalidsExceptions(ex);

        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCode().value());
    }

    @ParameterizedTest
    @MethodSource("provideUnauthorizedExceptions")
    @DisplayName("Deve lidar com Erros de BadRequest e retornar uma resposta de erro")
    void shouldHandleUnauthorizedExceptionsTest(RuntimeException exception) {
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = exceptionHandler.handleUnauthorizedExceptions(exception, request);

        assertNotNull(response.getBody());
        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve lidar com Exception e retornar uma resposta de erro")
    void shouldHandleException() {
        RuntimeException ex = new RoleNotFoundException("Erro interno");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = exceptionHandler.handleInternalServerErrorExceptions(ex, request);

        assertNotNull(response.getBody());
        assertEquals(500, response.getStatusCode().value());
    }

    private static Stream<Arguments> provideBadRequestExceptions() {
        return Stream.of(
           Arguments.of(new UserAlreadyExistsException("User already exists")),
           Arguments.of(new BillNotFoundException(2)),
           Arguments.of(new InvalidSituationException("situacao")),
           Arguments.of(new ImportCsvException("file", "msg"))
        );
    }

    private static Stream<Arguments> provideUnauthorizedExceptions() {
        return Stream.of(
           Arguments.of(new BadCredentialsException("User or password incorret")),
           Arguments.of(new InternalAuthenticationServiceException("invalid"))
        );
    }
    
}
