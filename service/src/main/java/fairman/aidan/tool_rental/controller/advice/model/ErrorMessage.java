package fairman.aidan.tool_rental.controller.advice.model;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorMessage {

  String message;
}
