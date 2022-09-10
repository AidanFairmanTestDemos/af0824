package fairman.aidan.rental.tool.model;

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
