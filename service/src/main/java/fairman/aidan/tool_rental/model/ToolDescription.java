package fairman.aidan.tool_rental.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ToolDescription {
  @NonNull
  String toolCode;
  @NonNull
  String brand;
  @NonNull
  String type;

}
