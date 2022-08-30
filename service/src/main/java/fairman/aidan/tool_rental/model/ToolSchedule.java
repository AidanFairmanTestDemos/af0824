package fairman.aidan.tool_rental.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Builder.ObtainVia;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ToolSchedule {
  @NonNull
  LocalDate rentalDate;
  @NonNull
  int rentalDays;

  @ObtainVia(method = "generateEndDate")
  LocalDate returnDate;

  public LocalDate generateEndDate() {
    return rentalDate.plusDays(rentalDays);
  }
}
