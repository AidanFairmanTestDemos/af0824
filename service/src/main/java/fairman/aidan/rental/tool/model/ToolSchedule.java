package fairman.aidan.rental.tool.model;

import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import java.time.LocalDate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class ToolSchedule {

  LocalDate rentalDate;
  int rentalDays;
  LocalDate returnDate;

  @Builder
  private ToolSchedule(@NonNull LocalDate rentalDate, int rentalDays)
      throws RentalDaysRangeException {
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
