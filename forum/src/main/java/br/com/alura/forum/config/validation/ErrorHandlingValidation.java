package br.com.alura.forum.config.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlingValidation {

    private final MessageSource messageSource;

    public ErrorHandlingValidation(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormDto> handle(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors =  e.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(fieldError ->
                new ErrorFormDto(fieldError.getField(),messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))).collect(Collectors.toList());
    }

}
