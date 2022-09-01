package fairman.aidan.tool_rental.service.holiday.processor;

import java.time.LocalDate;

public interface HolidayChargeProcessor {

  boolean isDateHoliday(LocalDate date);
}
