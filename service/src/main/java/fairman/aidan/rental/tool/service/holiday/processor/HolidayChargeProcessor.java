package fairman.aidan.rental.tool.service.holiday.processor;

import java.time.LocalDate;

public interface HolidayChargeProcessor {

  boolean isDateHoliday(LocalDate date);
}
