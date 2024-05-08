package com.portal.centro.API.exceptions.handlers;

import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.exceptions.NotFoundException;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ApiError handlerArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        BindingResult result = exception.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            validationErrors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage());
        }

        return new ApiError(exception, request.getServletPath(), validationErrors);
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ApiError handlerValidationExceptionError(
            Exception exception,
            HttpServletRequest request) {
        return new ApiError(exception, request.getServletPath());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ApiError handlerNotFoundExceptionError(
            Exception exception,
            HttpServletRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ApiError handlerExceptionError(
            Exception exception,
            HttpServletRequest request) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ApiError handlerRuntimeExceptionError(
            RuntimeException exception, HttpServletRequest request) {
        exception.printStackTrace();
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ApiError handlerDataIntegrityViolationExceptionError(DataIntegrityViolationException exception, HttpServletRequest request) throws GenericException {
        if (exception.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cause = (ConstraintViolationException) exception.getCause();
            if (cause.getMessage().contains("uplicate key value violates unique constraint")) {
                String message = cause.getMessage();
                Pattern pattern = Pattern.compile("\\((.*?)\\)=\\((.*?)\\)");
                Matcher matcher = pattern.matcher(message.substring(message.indexOf("Key "), message.indexOf("]")));

                String key = null;
                String value = null;

                if (matcher.find()) {
                    String tempKey = matcher.group(1);
                    key = Character.toUpperCase(tempKey.charAt(0)) + tempKey.substring(1);
                    value = matcher.group(2);
                }
                if (key != null && value != null) {
                    final String[] everUppercaseKey = new String[]{"cpf", "cnpj"};
                    final String auxKey = key;
                    if (Arrays.stream(everUppercaseKey).anyMatch(it -> it.equalsIgnoreCase(auxKey))) {
                        key = key.toUpperCase();
                    }
                    GenericException aux = new GenericException(key + " já utilizado(a) por outro registro!");
                    return new ApiError(aux, request.getServletPath());
                }
            }
        }
        return new ApiError(exception, request.getServletPath());
    }

    @ExceptionHandler({GenericException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ApiError handlerGenericExceptionError(GenericException exception, HttpServletRequest request) {
        return new ApiError(exception, request.getServletPath());
    }

}
