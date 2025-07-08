package br.edu.utfpr.pb.app.labcaapi.exceptions.handlers;

import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.exceptions.NotFoundException;
import br.edu.utfpr.pb.app.labcaapi.exceptions.ValidationException;
import br.edu.utfpr.pb.app.labcaapi.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
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
        return new ApiError(exception, request.getServletPath());
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
        if (exception.getCause() instanceof ConstraintViolationException cause) {
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
                    switch (key.toLowerCase()) {
                        case "cpf_cnpj" -> key = "CPF/CNPJ";
                        case "cpf" -> key = "CPF";
                        case "cnpj" -> key = "CNPJ";
                        default -> key = "GlobalExceptionHandlerAdvice: 99 - KEY NAO MAPEADA";
                    }
                    GenericException aux = new GenericException(key + " já utilizado(a) por outro registro!");
                    return new ApiError(aux, request.getServletPath());
                }
            } else if (cause.getMessage().contains("is still referenced from table")) {
                String tableReferenciada = cause.getMessage().substring(cause.getMessage().lastIndexOf("table") + 7, cause.getMessage().indexOf("\".]"));

                String nomeDaTabela = null;
                switch (tableReferenciada) {
                    case "tb_domain_role" -> nomeDaTabela = "Cadastro de Domínio";

                    case "tb_solicitation" -> nomeDaTabela = "Cadastro de Solicitação";
                    case "tb_solicitation_form" -> nomeDaTabela = "Formulário de Solicitação";
                    case "tb_solicitation_form_gradiente" -> nomeDaTabela = "Formulário de Solicitação Gradiente";
                    case "tb_solicitation_amostra" -> nomeDaTabela = "Solicitação de Amostra";
                    case "tb_solicitation_amostra_foto" -> nomeDaTabela = "Solicitação de Amostra Foto";
                    case "tb_solicitation_amostra_analise" -> nomeDaTabela = "Solicitação de Amostra Análise";
                    case "tb_termsofuse" -> nomeDaTabela = "Termo de Uso";
                    case "tb_email_config" -> nomeDaTabela = "Configuração de E-mail";
                    case "tb_equipment" -> nomeDaTabela = "Cadastro de Equipamentos";
                    case "tb_project" -> nomeDaTabela = "Cadastro de Projeto";
                    case "tb_analysis" -> nomeDaTabela = "Análise";
                }

                if (nomeDaTabela != null) {
                    GenericException aux = new GenericException("Não é possível excluir o registro, pois ele está vinculado a outro registro na tabela " + nomeDaTabela + "!");
                    return new ApiError(aux, request.getServletPath());
                } else {
                    GenericException aux = new GenericException("Não é possível excluir o registro, pois ele está vinculado a outro registro!");
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
