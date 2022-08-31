package fairman.aidan.tool_rental.service.holiday.processor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.time.Month;
import org.springframework.stereotype.Component;

@Component
public class LaborDayProcessor implements HolidayChargeProcessor {

  @Override
  public boolean isDateHoliday(LocalDate date) {
    int year = date.getYear();
    LocalDate laborDay = LocalDate.of(year, Month.SEPTEMBER, 1);
    while (laborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
      laborDay = laborDay.plusDays(1);
    }
    if (laborDay.isEqual(date)) {
      return true;
    }
    return false;
  }
}
