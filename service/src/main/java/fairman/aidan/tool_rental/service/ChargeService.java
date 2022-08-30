package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.data.charge.access.ChargeDataService;
import fairman.aidan.tool_rental.data.charge.model.ChargeDataModel;
import fairman.aidan.tool_rental.model.ToolCharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
  @Autowired
  private ChargeDataService chargeDataService;

  ToolCharge getChargesForTool(String toolCode, int discount){
    ChargeDataModel model = chargeDataService.getToolCharge(toolCode);
    return new ToolCharge(
        model.getRate(),
        discount,
        model.isOnWeekdays(),
        model.isOnWeekends(),
        model.isOnHolidays());
  }
}
