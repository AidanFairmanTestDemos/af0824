package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.model.ToolCharge;
import fairman.aidan.tool_rental.model.ToolSchedule;
import fairman.aidan.tool_rental.service.holiday.HolidayService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeScheduleService {
  private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
  @Autowired private HolidayService holidayService;
  public ToolCharge getToolChargeSchedule(ToolSchedule schedule, ToolCharge charges){
    LocalDate startDate = schedule.getRentalDate();
    int chargeableDays = 0;
    boolean chargeableDay = true;
    for (int i = 0; i < schedule.getRentalDays() ; i++) {
      chargeableDay = true;
      LocalDate day = startDate.plusDays(i);
      if(!charges.isOnWeekends() && WEEKEND.contains(day)){
        chargeableDay = false;
      }
      if(chargeableDay && !charges.isOnHolidays() && holidayService.isHoliday(day)){
        chargeableDay = false;
      }
      chargeableDays = chargeableDay ? chargeableDays + 1 : chargeableDays;
    }
    charges.setChargeableDays(chargeableDays);
    return charges;
  }
}
