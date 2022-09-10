package fairman.aidan.rental.tool.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolSchedule;
import fairman.aidan.rental.tool.persistence.charge.model.ChargeDataModel;
import fairman.aidan.rental.tool.persistence.charge.service.ChargeDataService;
import fairman.aidan.rental.tool.service.holiday.HolidayService;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChargeServiceTests {

  private static final String TOOL_CODE = "HAHA";
  private static final double RATE = 2.0;
  private static final boolean WEEKEND = true;
  private static final boolean NO_WEEKEND = false;
  private static final boolean WEEKDAY = true;
  private static final boolean NO_WEEKDAY = false;
  private static final boolean HOLIDAY = true;
  private static final boolean NO_HOLIDAY = false;
  private static final int DISCOUNT = 10;
  private static final LocalDate NOW = LocalDate.now();
  private static final LocalDate WEEKEND_AND_WEEKDAY = LocalDate.of(2022, Month.SEPTEMBER, 9);
  private static final LocalDate WEEKEND_AND_HOLIDAY = LocalDate.of(2022, Month.SEPTEMBER, 2);
  private static final LocalDate HOLIDAY_NO_WEEKEND = LocalDate.of(2022, Month.SEPTEMBER, 5);
  private static final LocalDate LABOR_DAY_2022 = LocalDate.of(2022, Month.SEPTEMBER, 5);
  private static final int DAYS = 5;
  private static final double PRE_DISCOUNT = RATE * DAYS;
  private static final double DISCOUNT_AMOUNT = ((double) DISCOUNT / 100.0) * PRE_DISCOUNT;
  private static final double TOTAL = PRE_DISCOUNT - DISCOUNT_AMOUNT;

  private ChargeService service;

  @Mock
  private ChargeDataService chargeDataService;
  @Mock
  private HolidayService holidayService;

  @BeforeEach
  public void beforeEach() {
    Mockito.reset(chargeDataService, holidayService);
    service = new ChargeService(chargeDataService, holidayService);
  }

  @Test
  public void returnsExpectedCharges()
      throws RentalDaysRangeException, DiscountOutOfRangeException {
    when(chargeDataService.getToolCharge(any(String.class))).thenReturn(
        ChargeDataModel.builder()
            .rate(RATE)
            .onHolidays(HOLIDAY)
            .onWeekdays(WEEKDAY)
            .onWeekends(WEEKEND)
            .build());

    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(NOW)
        .rentalDays(DAYS)
        .build();
    ToolCharge charges = service.getChargesForTool(TOOL_CODE, DISCOUNT, schedule);
    assertNotNull(charges);
    assertEquals(DAYS, charges.getChargeableDays());
    assertEquals((double) DISCOUNT / 100.0, charges.getDiscountPercent());
    assertEquals(PRE_DISCOUNT, charges.getPreDiscountCharge());
    assertEquals(DISCOUNT_AMOUNT, charges.getDiscountAmount());
    assertEquals(TOTAL, charges.getCharge());
  }

  @Test
  public void correctChargeableDaysWhenNotChargingWeekend()
      throws RentalDaysRangeException, DiscountOutOfRangeException {
    when(chargeDataService.getToolCharge(any(String.class))).thenReturn(
        ChargeDataModel.builder()
            .rate(RATE)
            .onHolidays(HOLIDAY)
            .onWeekdays(WEEKDAY)
            .onWeekends(NO_WEEKEND)
            .build());
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(WEEKEND_AND_WEEKDAY)
        .rentalDays(DAYS)
        .build();
    ToolCharge charges = service.getChargesForTool(TOOL_CODE, DISCOUNT, schedule);
    assertNotNull(charges);
    assertEquals(3, charges.getChargeableDays());
  }

  @Test
  public void correctChargeableDaysWhenNotChargingWeekendOrHoliday()
      throws RentalDaysRangeException, DiscountOutOfRangeException {
    when(holidayService.isHoliday(any(LocalDate.class))).thenAnswer(invocation -> {
      LocalDate argDate = invocation.getArgument(0);
      if (LABOR_DAY_2022.isEqual(argDate)) {
        return HOLIDAY;
      }
      return NO_HOLIDAY;
    });
    when(chargeDataService.getToolCharge(any(String.class))).thenReturn(
        ChargeDataModel.builder()
            .rate(RATE)
            .onHolidays(NO_HOLIDAY)
            .onWeekdays(WEEKDAY)
            .onWeekends(NO_WEEKEND)
            .build());
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(WEEKEND_AND_HOLIDAY)
        .rentalDays(DAYS)
        .build();
    ToolCharge charges = service.getChargesForTool(TOOL_CODE, DISCOUNT, schedule);
    assertNotNull(charges);
    assertEquals(2, charges.getChargeableDays());
  }

  @Test
  public void correctChargeableDaysWhenNotChargingHoliday()
      throws RentalDaysRangeException, DiscountOutOfRangeException {
    when(holidayService.isHoliday(any(LocalDate.class))).thenAnswer(invocation -> {
      LocalDate argDate = invocation.getArgument(0);
      if (LABOR_DAY_2022.isEqual(argDate)) {
        return HOLIDAY;
      }
      return NO_HOLIDAY;
    });
    when(chargeDataService.getToolCharge(any(String.class))).thenReturn(
        ChargeDataModel.builder()
            .rate(RATE)
            .onHolidays(NO_HOLIDAY)
            .onWeekdays(WEEKDAY)
            .onWeekends(WEEKEND)
            .build());
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(WEEKEND_AND_HOLIDAY)
        .rentalDays(DAYS)
        .build();
    ToolCharge charges = service.getChargesForTool(TOOL_CODE, DISCOUNT, schedule);
    assertNotNull(charges);
    assertEquals(4, charges.getChargeableDays());
  }

  @Test
  public void correctChargeableDaysWhenNotChargingWeekdays()
      throws RentalDaysRangeException, DiscountOutOfRangeException {
    when(chargeDataService.getToolCharge(any(String.class))).thenReturn(
        ChargeDataModel.builder()
            .rate(RATE)
            .onHolidays(HOLIDAY)
            .onWeekdays(NO_WEEKDAY)
            .onWeekends(WEEKEND)
            .build());
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(WEEKEND_AND_WEEKDAY)
        .rentalDays(DAYS)
        .build();
    ToolCharge charges = service.getChargesForTool(TOOL_CODE, DISCOUNT, schedule);
    assertNotNull(charges);
    assertEquals(2, charges.getChargeableDays());
  }


}
