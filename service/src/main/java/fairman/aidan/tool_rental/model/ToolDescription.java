package fairman.aidan.tool_rental.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ToolDescription {
  @NonNull
  String toolCode;
  @NonNull
  String brand;
  @NonNull
  String type;

}
