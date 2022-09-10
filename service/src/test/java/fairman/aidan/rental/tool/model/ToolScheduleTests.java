package fairman.aidan.rental.tool.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ToolScheduleTests {

  private static final int RENTAL_DAYS = 5;
  private static final int YEAR = 2013;
  private static final int MONTH = 4;
  private static final int DAY = 5;
  private static final LocalDate RENTAL_DATE = LocalDate.of(YEAR, MONTH, DAY);

  @Test
  public void rentalDaysMustBeGT0() {
    final int NEGATIVE_RENTAL = -3;
    assertThrows(RentalDaysRangeException.class, () -> {
      ToolSchedule.builder()
          .rentalDate(RENTAL_DATE)
          .rentalDays(NEGATIVE_RENTAL)
          .build();
    });
  }

  @Test
  public void generatesCorrectEndDate() throws RentalDaysRangeException {
    LocalDate endDate = LocalDate.of(YEAR, MONTH, DAY + RENTAL_DAYS);
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDays(RENTAL_DAYS)
        .rentalDate(RENTAL_DATE)
        .build();
    assertTrue(endDate.isEqual(schedule.getReturnDate()));
  }
}
