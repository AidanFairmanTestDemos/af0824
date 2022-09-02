package fairman.aidan.tool_rental.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import fairman.aidan.tool_rental.errors.ToolAgreementException;
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
public class ToolAgreementExceptionHandlerTests {

  @Mock
  private WebRequest webRequest;

  private ToolAgreementExceptionHandler exceptionHandler;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void beforeEach() {
    Mockito.reset(webRequest);
    objectMapper = new ObjectMapper();
    exceptionHandler = new ToolAgreementExceptionHandler(objectMapper);
  }

  @Test
  public void returnIsProperJson() {
    String message = "this is a test message";
    String output = "{\"message\":\"" + message + "\"}";
    ToolAgreementException exception = new ToolAgreementException(message);
    ResponseEntity<Object> response = exceptionHandler.handleConflict(exception, webRequest);
    String responseBody = response.getBody().toString();
    assertNotNull(responseBody);
    assertEquals(output, responseBody);
  }

  @Test
  public void returnIs406NotAcceptable() {
    ToolAgreementException exception = new ToolAgreementException("test");
    ResponseEntity<Object> response = exceptionHandler.handleConflict(exception, webRequest);
    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
  }
}
