package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.model.ToolCharge;
import fairman.aidan.tool_rental.persistence.charge.access.ChargeDataService;
import fairman.aidan.tool_rental.persistence.charge.model.ChargeDataModel;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargeService {

  private static final DecimalFormat ROUNDING = new DecimalFormat("0.00");

  static {
    ROUNDING.setRoundingMode(RoundingMode.HALF_UP);
  }

  @NonNull
  private final ChargeDataService chargeDataService;

  public ToolCharge getChargesForTool(String toolCode) {
    ChargeDataModel model = chargeDataService.getToolCharge(toolCode);
    return ToolCharge.builder()
        .rate(model.getRate())
        .weekdays(model.isOnWeekdays())
        .weekends(model.isOnWeekends())
        .holidays(model.isOnHolidays())
        .build();
  }

  public ToolCharge calculateToolCharges(ToolCharge charges) {
    charges.setPreDiscountCharge(round(charges.getRate() * charges.getChargeableDays()));
    charges.setDiscountAmount(round(charges.getPreDiscountCharge() * charges.getDiscountPercent()));
    charges.setCharge(round(charges.getPreDiscountCharge() - charges.getDiscountAmount()));
    return charges;
  }

  private double round(double value) {
    return Double.parseDouble(ROUNDING.format(value));
  }
}
