package ramos.maxuel.socialmedia.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ramos.maxuel.socialmedia.exception.BusinessException;
import ramos.maxuel.socialmedia.exception.Error;
import ramos.maxuel.socialmedia.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        final List<Error> errors = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.add(new Error(error.getDefaultMessage())));

        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(error ->
                        errors.add(new Error(error.getDefaultMessage())));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public ResponseEntity<List<Error>> exceptionHandler(BusinessException e) {
        log.error(format(
                "Business exception: { %s }", e.getMessage() == null ? e.getErrors().toString() : e.getMessage()),
                e
        );

        return ResponseEntity
                .badRequest()
                .body(e.getErrors());
    }

    @ExceptionHandler({ResourceNotFoundException.class })
    @ResponseBody
    public ResponseEntity<List<Error>> exceptionHandler(ResourceNotFoundException e) {
        log.error(format("Resource not found: { %s }", e.getMessage() == null ? e.getError().getMessage() : e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(List.of(e.getError()));
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<List<Error>> exceptionHandler(Exception e) {
        log.error(format("Unknown error: { %s }", e.getMessage()), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(List.of(new Error(e.getMessage())));
    }

}
