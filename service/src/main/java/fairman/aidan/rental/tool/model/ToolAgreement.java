package fairman.aidan.rental.tool.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolAgreement {

  String agreementText;
  ToolCharge toolCharge;
  ToolDescription toolDescription;
  ToolSchedule toolSchedule;
}
