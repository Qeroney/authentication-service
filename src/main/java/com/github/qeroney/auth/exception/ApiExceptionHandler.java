package com.github.qeroney.auth.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final MessageSource source;

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ConflictException.class)
    public MessageError processNotFoundException(ConflictException exception) {
        String message = source.getMessage(exception.getMessage(), null, Locale.getDefault());
        return MessageError.of(message);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public MessageError processConstViolationException(ConstraintViolationException exception) {
        return MessageError.of(exception.getConstraintViolations().stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.joining(", ")));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageError processMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return MessageError.of(exception.getBindingResult().getFieldErrors().stream()
                                        .map(fieldError -> source.getMessage(fieldError, Locale.getDefault()))
                                        .collect(Collectors.joining(", ")));
    }
}