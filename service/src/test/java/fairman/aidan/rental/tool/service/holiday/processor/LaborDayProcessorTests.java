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
public class LaborDayProcessorTests {

  private static final LocalDate LABOR_DAY_2020 = LocalDate.of(2020, Month.SEPTEMBER, 7);
  private static final LocalDate LABOR_DAY_2021 = LocalDate.of(2021, Month.SEPTEMBER, 6);
  private static final LocalDate LABOR_DAY_2022 = LocalDate.of(2022, Month.SEPTEMBER, 5);
  private static final LocalDate LABOR_DAY_2023 = LocalDate.of(2023, Month.SEPTEMBER, 4);
  private static final LocalDate LABOR_DAY_2024 = LocalDate.of(2024, Month.SEPTEMBER, 2);

  private HolidayChargeProcessor laborDayProcessor;

  @BeforeEach
  public void beforeEach() {
    laborDayProcessor = new LaborDayProcessor();
  }

  @Test
  public void correctlyCalculatesLaborDay() {
    assertTrue(laborDayProcessor.isDateHoliday(LABOR_DAY_2020));
    assertTrue(laborDayProcessor.isDateHoliday(LABOR_DAY_2021));
    assertTrue(laborDayProcessor.isDateHoliday(LABOR_DAY_2022));
    assertTrue(laborDayProcessor.isDateHoliday(LABOR_DAY_2023));
    assertTrue(laborDayProcessor.isDateHoliday(LABOR_DAY_2024));
  }

  @Test
  public void returnsFalseWhenNotLaborDay() {
    assertFalse(laborDayProcessor.isDateHoliday(LABOR_DAY_2020.minusYears(1)));
    assertFalse(laborDayProcessor.isDateHoliday(LABOR_DAY_2021.minusMonths(1)));
    assertFalse(laborDayProcessor.isDateHoliday(LABOR_DAY_2022.plusMonths(1)));
    assertFalse(laborDayProcessor.isDateHoliday(LABOR_DAY_2023.minusDays(1)));
    assertFalse(laborDayProcessor.isDateHoliday(LABOR_DAY_2024.plusDays(1)));
  }
}
