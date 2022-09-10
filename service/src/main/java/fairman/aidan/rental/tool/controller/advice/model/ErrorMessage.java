package fairman.aidan.rental.tool.controller.advice.model;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorMessage {

  String message;
}
