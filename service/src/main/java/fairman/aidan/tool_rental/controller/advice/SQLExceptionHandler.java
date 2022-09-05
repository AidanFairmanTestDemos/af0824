package fairman.aidan.tool_rental.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fairman.aidan.tool_rental.controller.advice.model.ErrorMessage;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class SQLExceptionHandler extends ResponseEntityExceptionHandler {

  private final ObjectMapper objectMapper;

  @SneakyThrows
  @ExceptionHandler()
  public ResponseEntity<Object> handleConflict(
      SQLException ex, WebRequest request) {

    String message = objectMapper.writeValueAsString(ErrorMessage.builder()
        .message(ex.getMessage())
        .build());
    return handleExceptionInternal(ex, message,
        new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
  }
}
