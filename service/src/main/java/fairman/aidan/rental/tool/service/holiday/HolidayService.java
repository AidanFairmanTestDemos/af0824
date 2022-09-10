package fairman.aidan.rental.tool.service.holiday;

import fairman.aidan.rental.tool.service.holiday.processor.HolidayChargeProcessor;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HolidayService {

  private final List<HolidayChargeProcessor> holidayProcessors;

  public boolean isHoliday(LocalDate date) {
    for (HolidayChargeProcessor processor : holidayProcessors) {
      if (processor.isDateHoliday(date)) {
        return true;
      }
    }
    return false;
  }
}
