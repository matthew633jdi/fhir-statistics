package kr.irm.FHIRext.statistics.handler;

import kr.irm.FHIRext.statistics.dto.ExceptionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionVO> handleValidationException(MethodArgumentNotValidException exception) {
        StringJoiner joiner = new StringJoiner(", ");

        if (exception.getBindingResult().hasFieldErrors()) {
            for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
                joiner.add(String.format("%s: %s",fieldError.getField(), fieldError.getDefaultMessage()));
            }
        } else if (exception.getBindingResult().hasGlobalErrors()) {
            joiner.add(exception.getBindingResult().getGlobalError().getDefaultMessage());
        }

        log.error("fhirext-statistics: BAD REQUEST - {}", joiner.toString(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionVO.of(HttpStatus.BAD_REQUEST.name(), joiner.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionVO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("fhirext-statistics: BAD REQUEST - {}", exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionVO.of(HttpStatus.BAD_REQUEST.name(), exception.getMessage()));
    }
}
