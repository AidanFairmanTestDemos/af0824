package fairman.aidan.tool_rental.service.holiday.processor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import org.springframework.stereotype.Component;

@Component
public class July4Processor implements HolidayChargeProcessor {

  @Override
  public boolean isDateHoliday(LocalDate date) {
    int dayOfMonth = date.getDayOfMonth();
    DayOfWeek day = date.getDayOfWeek();
    if (date.getMonth() != Month.JULY) {
      return false;
    }
    if (dayOfMonth == 4 && day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
      return true;
    }
    if (dayOfMonth == 3 && day == DayOfWeek.FRIDAY) {
      return true;
    }
    if (dayOfMonth == 5 && day == DayOfWeek.MONDAY) {
      return true;
    }
    return false;
  }
}
