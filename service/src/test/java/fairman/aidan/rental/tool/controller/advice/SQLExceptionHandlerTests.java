package fairman.aidan.rental.tool.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
public class SQLExceptionHandlerTests {

  @Mock
  private WebRequest webRequest;

  private SQLExceptionHandler exceptionHandler;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void beforeEach() {
    Mockito.reset(webRequest);
    objectMapper = new ObjectMapper();
    exceptionHandler = new SQLExceptionHandler(objectMapper);
  }

  @Test
  public void returnIsProperJson() {
    String message = "this is a test message";
    String output = "{\"message\":\"" + message + "\"}";
    SQLException exception = new SQLException(message);
    ResponseEntity<Object> response = exceptionHandler.handleConflict(exception, webRequest);
    String responseBody = response.getBody().toString();
    assertNotNull(responseBody);
    assertEquals(output, responseBody);
  }

  @Test
  public void returnIs406NotAcceptable() {
    SQLException exception = new SQLException("test");
    ResponseEntity<Object> response = exceptionHandler.handleConflict(exception, webRequest);
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
  }
}
