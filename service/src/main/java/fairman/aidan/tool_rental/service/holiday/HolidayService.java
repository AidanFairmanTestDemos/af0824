package fairman.aidan.tool_rental.service.holiday;

import fairman.aidan.tool_rental.service.holiday.processor.HolidayChargeProcessor;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

  @Autowired
  private List<HolidayChargeProcessor> holidayProcessors;

  public boolean isHoliday(LocalDate date) {
    for (HolidayChargeProcessor processor : holidayProcessors) {
      //is today a holiday
      if (processor.isDateHoliday(date)) {
        return true;
      }
    }
    return false;
  }
}
