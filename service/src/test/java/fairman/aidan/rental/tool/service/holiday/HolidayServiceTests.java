package fairman.aidan.rental.tool.service.holiday;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fairman.aidan.rental.tool.service.holiday.processor.HolidayChargeProcessor;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HolidayServiceTests {

  private static final LocalDate LABOR_DAY_2022 = LocalDate.of(2022, Month.SEPTEMBER, 5);
  private static final List<HolidayChargeProcessor> PROCESSORS = List.of(
      LABOR_DAY_2022::isEqual,
      (date) -> false);
  private HolidayService service;

  @BeforeEach
  public void beforeEach() {
    service = new HolidayService(PROCESSORS);
  }

  @Test
  public void trueIfAProcessorMatches() {
    assertTrue(service.isHoliday(LABOR_DAY_2022));
  }

  @Test
  public void falseIfNoProcessorsMatch() {
    // Safe to test since the only holiday is in the past
    assertFalse(service.isHoliday(LocalDate.now()));
  }
}
