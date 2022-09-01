package fairman.aidan.tool_rental.model;

import fairman.aidan.tool_rental.errors.RentalDaysRangeException;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Builder.ObtainVia;
import lombok.Data;
import lombok.NonNull;

@Data
public class ToolSchedule {

  @NonNull
  LocalDate rentalDate;

  int rentalDays;

  LocalDate returnDate;

  @Builder
  ToolSchedule(LocalDate rentalDate, int rentalDays) throws RentalDaysRangeException {
    this.rentalDate = rentalDate;
    if (rentalDays < 1) {
      throw new RentalDaysRangeException();
    }
    this.rentalDays = rentalDays;
    this.returnDate = generateEndDate();
  }

  private LocalDate generateEndDate() {
    return rentalDate.plusDays(rentalDays);
  }
}
