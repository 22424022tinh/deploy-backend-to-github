package com.vn.hcmus.qlqtpm.backendvnexpress.controller;

import com.vn.hcmus.qlqtpm.backendvnexpress.exception.BadRequestException;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.ForbiddenException;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.HttpClientErrorException;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.UnauthorizedException;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;


@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleApiBadRequestException(BadRequestException ex, HttpServletRequest request) {
        String trace = ex.getErrorMessage() + " - " + Arrays.toString(ex.getStackTrace()).replaceAll("[\\[\\]]", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .message(ex.getErrorMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(new Date())
                .path(request.getServletPath())
                .trace(trace)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
        String trace = ex.getErrorMessage() + " - " + Arrays.toString(ex.getStackTrace()).replaceAll("[\\[\\]]", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .message(ex.getErrorMessage())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(new Date())
                .path(request.getServletPath())
                .trace(trace)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorizationException(UnauthorizedException ex, HttpServletRequest request) {
        String trace = ex.getErrorMessage() + " - " + Arrays.toString(ex.getStackTrace()).replaceAll("[\\[\\]]", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .message(ex.getErrorMessage())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(new Date())
                .path(request.getServletPath())
                .trace(trace)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleApiExceptionException(HttpClientErrorException exception, HttpServletRequest request) {
        return redirectHttpClientErrorException(exception, request);
    }

    private ResponseEntity<ApiErrorResponse> redirectHttpClientErrorException(HttpClientErrorException ex, HttpServletRequest request) {
        String trace = ex.getErrorMessage() + " - " + Arrays.toString(ex.getStackTrace()).replaceAll("[\\[\\]]", "");
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .message(ex.getErrorMessage())
                .timestamp(new Date())
                .path(request.getServletPath())
                .trace(trace)
                .build();
        switch (ex.getStatusCode()) {
            case BAD_REQUEST:
                errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            case CONFLICT:
                errorResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.CONFLICT.value());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            case EXPECTATION_FAILED:
                errorResponse.setError(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
            case FORBIDDEN:
                errorResponse.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
            case LOCKED:
                errorResponse.setError(HttpStatus.LOCKED.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.LOCKED.value());
                return ResponseEntity.status(HttpStatus.LOCKED).body(errorResponse);
            case REQUEST_TIMEOUT:
                errorResponse.setError(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(errorResponse);
            case NOT_FOUND:
                errorResponse.setError(HttpStatus.NO_CONTENT.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.NO_CONTENT.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            case TOO_MANY_REQUESTS:
                errorResponse.setError(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorResponse);
            case UNAUTHORIZED:
                errorResponse.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            case FAILED_DEPENDENCY:
                errorResponse.setError(HttpStatus.FAILED_DEPENDENCY.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(errorResponse);
            case NOT_ACCEPTABLE:
                errorResponse.setError(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
                errorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
            default:
                errorResponse.setError(ex.getStatusCode().getReasonPhrase());
                errorResponse.setStatus(ex.getStatusCode().value());
                return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }


}

