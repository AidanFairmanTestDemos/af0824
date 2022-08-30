package fairman.aidan.tool_rental.model;

import fairman.aidan.tool_rental.errors.DiscountOutOfRangeException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
public class ToolCharge {
  private static final DecimalFormat ROUNDING = new DecimalFormat("0.00");

  static{
    ROUNDING.setRoundingMode(RoundingMode.HALF_UP);
  }

  final boolean onWeekdays;
  final boolean onWeekends;
  final boolean onHolidays;
  final double rate;
  int chargeableDays;
  double discountPercent;
  @Setter(value = AccessLevel.NONE)
  double preDiscountCharge;
  @Setter(value = AccessLevel.NONE)
  double discountAmount;
  @Setter(value = AccessLevel.NONE)
  double charge;
  public ToolCharge(double rate, int discount, boolean weekdays, boolean weekends, boolean holidays){
    this.rate = rate;
    if(discount < 0 || discount > 100){
      throw new DiscountOutOfRangeException();
    }
    this.discountPercent = (double)discount / 100d;
    this.onWeekdays = weekdays;
    this.onWeekends = weekends;
    this.onHolidays = holidays;
  }
  public void setChargeableDays(int chargeableDays) {
    this.chargeableDays = chargeableDays;
    this.preDiscountCharge = round(rate * chargeableDays);
    this.discountAmount = round(preDiscountCharge * discountPercent);
    this.charge = round(preDiscountCharge - discountAmount);
  }

  private double round(double value){
    return Double.parseDouble(ROUNDING.format(value));
  }
}
