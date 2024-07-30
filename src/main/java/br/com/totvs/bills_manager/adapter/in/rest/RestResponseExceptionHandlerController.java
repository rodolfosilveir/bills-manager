package br.com.totvs.bills_manager.adapter.in.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.totvs.bills_manager.adapter.in.rest.response.DefaultResponse;
import br.com.totvs.bills_manager.domain.exception.BillNotFoundException;
import br.com.totvs.bills_manager.domain.exception.ImportCsvException;
import br.com.totvs.bills_manager.domain.exception.InvalidSituationException;
import br.com.totvs.bills_manager.domain.exception.RoleNotFoundException;
import br.com.totvs.bills_manager.domain.exception.UserAlreadyExistsException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class RestResponseExceptionHandlerController {

    @ExceptionHandler(value = {UserAlreadyExistsException.class, BillNotFoundException.class, 
        InvalidSituationException.class, ImportCsvException.class, RoleNotFoundException.class})
    protected ResponseEntity<DefaultResponse<Object>> handleBadRequestValidationExceptions(RuntimeException ex, WebRequest request) {

        log.error("Bad request error. {}", ex.getMessage(), ex);
        DefaultResponse<Object> body = DefaultResponse.builder()
            .httpStatus(400)
            .errors(Arrays.asList(ex.getMessage()))
        .build();
        return ResponseEntity.badRequest().body(body); 
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentsInvalidsExceptions(MethodArgumentNotValidException ex) {
        
        List<String> errosFields = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList());
        log.error("Bad request error.", ex);
        DefaultResponse<Object> body = DefaultResponse.builder()
            .httpStatus(400)
            .errors(errosFields)
        .build();
        return ResponseEntity.badRequest().body(body); 
    }

    @ExceptionHandler(value = {BadCredentialsException.class, InternalAuthenticationServiceException.class})
    protected ResponseEntity<Object> handleUnauthorizedExceptions(RuntimeException ex, WebRequest request) {

        log.error("Unauthorized. {}", ex.getMessage(), ex);
        DefaultResponse<Object> body = DefaultResponse.builder()
            .httpStatus(401)
        .build();
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(body); 
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleInternalServerErrorExceptions(RuntimeException ex, WebRequest request) {

        log.error(
					"Internal Server error. {}", ex.getMessage(), ex);
        DefaultResponse<Object> body = DefaultResponse.builder()
            .httpStatus(500)
            .errors(Arrays.asList("Ocorreu um erro no servidor. Por favor tente mais tarde e obrigado pela compreensão"))
        .build();
        return ResponseEntity.internalServerError().body(body); 
    }
    
}
