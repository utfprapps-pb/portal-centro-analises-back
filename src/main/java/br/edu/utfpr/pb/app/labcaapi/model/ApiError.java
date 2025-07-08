package br.edu.utfpr.pb.app.labcaapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiError {
    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean mapped = false;
    private int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private String message;
    private String url;
    private Map<String, String> validationErrors;

    public ApiError(int status, String message, String url,
                    Map<String, String> validationErrors) {
        this.status = status;
        this.message = message;
        this.url = url;
        this.validationErrors = validationErrors;
    }

    public ApiError(int status, String message, String url) {
        this.status = status;
        this.message = message;
        this.url = url;
    }

    public ApiError(Exception exception, String url) {
        this.mapped = true;
        this.message = exception.getMessage();
        this.url = url;
    }

    public ApiError(Exception exception, String url,
                    Map<String, String> validationErrors) {
        this.mapped = true;
        this.message = exception.getMessage();
        this.url = url;
        this.validationErrors = validationErrors;
    }

}
