package fairman.aidan.rental.tool.persistence.info.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InfoDataModel {

  String id;
  String type;
  String brand;
}
