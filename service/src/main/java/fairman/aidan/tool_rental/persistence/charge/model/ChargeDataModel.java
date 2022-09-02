package fairman.aidan.tool_rental.persistence.charge.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChargeDataModel {

  boolean onWeekdays;
  boolean onWeekends;
  boolean onHolidays;
  double rate;
}
