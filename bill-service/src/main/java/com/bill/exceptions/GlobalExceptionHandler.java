package com.bill.exceptions;

import com.fasterxml.jackson.databind.DatabindException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ProblemDetail runtimeException(RuntimeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ProblemDetail handleValidationViolation(MethodArgumentNotValidException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getFieldErrors().stream().
                map(this::parseFieldException).toList().toString());
    }

    @ExceptionHandler(BindException.class)
    protected ProblemDetail handleBindException(BindException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getFieldErrors().stream().
                map(this::parseFieldException).toList().toString());
    }

    @ExceptionHandler({DatabindException.class})
    public ProblemDetail handleDatabindException(DatabindException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getOriginalMessage());

    }

    private String parseFieldException(FieldError fieldError) {
        return fieldError.getField() + " is set to '" +
                fieldError.getRejectedValue() + "'" + ", " + fieldError.getDefaultMessage();
    }

    @ExceptionHandler({BadCredentialsException.class, AccountStatusException.class, AccessDeniedException.class,
            SignatureException.class, ExpiredJwtException.class})
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return errorDetail;
    }
}
