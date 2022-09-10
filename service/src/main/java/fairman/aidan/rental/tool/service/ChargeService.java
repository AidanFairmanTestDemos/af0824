package fairman.aidan.rental.tool.service;

import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolSchedule;
import fairman.aidan.rental.tool.persistence.charge.service.ChargeDataService;
import fairman.aidan.rental.tool.service.holiday.HolidayService;
import fairman.aidan.rental.tool.persistence.charge.model.ChargeDataModel;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargeService {

  private static final DecimalFormat ROUNDING = new DecimalFormat("0.00");
  private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

  static {
    ROUNDING.setRoundingMode(RoundingMode.HALF_UP);
  }

  @NonNull
  private final ChargeDataService chargeDataService;
  @NonNull
  private final HolidayService holidayService;

  public ToolCharge getChargesForTool(String toolCode, int discount, ToolSchedule schedule)
      throws DiscountOutOfRangeException {
    ChargeDataModel model = chargeDataService.getToolCharge(toolCode);
    ToolCharge charges = ToolCharge.builder()
        .rate(model.getRate())
        .weekdays(model.isOnWeekdays())
        .weekends(model.isOnWeekends())
        .holidays(model.isOnHolidays())
        .discount(discount)
        .build();
    charges = getToolChargeSchedule(charges, schedule);
    return calculateToolCharges(charges);
  }

  private ToolCharge calculateToolCharges(ToolCharge charges) {
    charges.setPreDiscountCharge(round(charges.getRate() * charges.getChargeableDays()));
    charges.setDiscountAmount(round(charges.getPreDiscountCharge() * charges.getDiscountPercent()));
    charges.setCharge(round(charges.getPreDiscountCharge() - charges.getDiscountAmount()));
    return charges;
  }

  private ToolCharge getToolChargeSchedule(ToolCharge charges, ToolSchedule schedule) {
    LocalDate startDate = schedule.getRentalDate();
    int chargeableDays = 0;
    boolean chargeableDay;
    for (int i = 0; i < schedule.getRentalDays(); i++) {
      chargeableDay = true;
      LocalDate day = startDate.plusDays(i);
      if (!charges.isOnWeekends() && WEEKEND.contains(day.getDayOfWeek())) {
        chargeableDay = false;
      } else if (!charges.isOnWeekdays() && !(WEEKEND.contains(day.getDayOfWeek()))) {
        chargeableDay = false;
      } else if (!charges.isOnHolidays() && holidayService.isHoliday(day)) {
        chargeableDay = false;
      }
      chargeableDays = chargeableDay ? chargeableDays + 1 : chargeableDays;
    }
    charges.setChargeableDays(chargeableDays);
    return charges;
  }

  private double round(double value) {
    return Double.parseDouble(ROUNDING.format(value));
  }
}
