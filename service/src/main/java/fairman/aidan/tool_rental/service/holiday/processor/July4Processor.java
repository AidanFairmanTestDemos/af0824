package fairman.aidan.tool_rental.service.holiday.processor;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class July4Processor implements HolidayChargeProcessor{

  @Override
  public boolean isDateHoliday(LocalDate date) {
    return false;
  }
}
