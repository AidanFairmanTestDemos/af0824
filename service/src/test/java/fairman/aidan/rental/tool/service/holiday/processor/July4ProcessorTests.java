package fairman.aidan.rental.tool.service.holiday.processor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class July4ProcessorTests {

  private static final LocalDate JULY_4_2020_OBSERVED = LocalDate.of(2020, Month.JULY, 3);
  private static final LocalDate JULY_4_2020 = LocalDate.of(2020, Month.JULY, 4);
  private static final LocalDate JULY_4_2021_OBSERVED = LocalDate.of(2021, Month.JULY, 5);
  private static final LocalDate JULY_4_2021 = LocalDate.of(2021, Month.JULY, 4);
  private static final LocalDate JULY_4_2022 = LocalDate.of(2022, Month.JULY, 4);

  private HolidayChargeProcessor july4Processor;

  @BeforeEach
  public void beforeEach() {
    july4Processor = new July4Processor();
  }

  @Test
  public void july4DuringTheWeekObservedOnDay() {
    assertTrue(july4Processor.isDateHoliday(JULY_4_2022));
  }

  @Test
  public void july4OnSaturdayObservedPreviousFriday() {
    assertFalse(july4Processor.isDateHoliday(JULY_4_2020));
    assertTrue(july4Processor.isDateHoliday(JULY_4_2020_OBSERVED));
  }

  @Test
  public void july4OnSundayObservedFollowingMonday() {
    assertFalse(july4Processor.isDateHoliday(JULY_4_2021));
    assertTrue(july4Processor.isDateHoliday(JULY_4_2021_OBSERVED));
  }
}
