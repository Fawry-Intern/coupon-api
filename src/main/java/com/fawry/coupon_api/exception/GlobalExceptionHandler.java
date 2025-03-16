package com.fawry.coupon_api.exception;

import com.fawry.coupon_api.dto.ErrorResponseDTO;
import com.fawry.coupon_api.enums.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        log.error("Validation Error: {}", ex.getMessage(), ex);

        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "")
                );

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(ErrorCode.VALIDATION_ERROR.getStatus().value())
                .error(ErrorCode.VALIDATION_ERROR.getStatus().getReasonPhrase())
                .message(ErrorCode.VALIDATION_ERROR.getDefaultMessage())
                .path(request.getRequestURI())
                .build();

        Map<String, Object> response = new HashMap<>();
        response.put("error", error);
        response.put("validationErrors", errors);

        return new ResponseEntity<>(response, ErrorCode.VALIDATION_ERROR.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        log.error("Entity Not Found Error: {}", ex.getMessage(), ex);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(ErrorCode.RESOURCE_NOT_FOUND.getStatus().value())
                .error(ErrorCode.RESOURCE_NOT_FOUND.getStatus().getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, ErrorCode.RESOURCE_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExistsException(
            EntityAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        log.error("Entity Already Exists: {}", ex.getMessage(), ex);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(ErrorCode.RESOURCE_ALREADY_EXISTS.getStatus().value()) // Use 409 Conflict
                .error(ErrorCode.RESOURCE_ALREADY_EXISTS.getStatus().getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, ErrorCode.RESOURCE_ALREADY_EXISTS.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        log.error("Invalid Argument: {}", ex.getMessage(), ex);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(ErrorCode.VALIDATION_ERROR.getStatus().value())
                .error(ErrorCode.VALIDATION_ERROR.getStatus().getReasonPhrase())
                .message("Invalid value provided: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, ErrorCode.VALIDATION_ERROR.getStatus());
    }
}
