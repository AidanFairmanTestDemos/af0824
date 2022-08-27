package fairman.aidan.tool_rental.service.holiday.processor;

import java.time.LocalDate;
import java.util.List;

public interface HolidayChargeProcessor {
  boolean isDateHoliday(LocalDate date);
}
