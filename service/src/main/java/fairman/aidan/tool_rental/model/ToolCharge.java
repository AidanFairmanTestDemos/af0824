package fairman.aidan.tool_rental.model;

import fairman.aidan.tool_rental.errors.DiscountOutOfRangeException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
public class ToolCharge {

  final boolean onWeekdays;
  final boolean onWeekends;
  final boolean onHolidays;
  final double rate;
  int chargeableDays;
  @Setter(value = AccessLevel.NONE)
  double discountPercent;
  double preDiscountCharge;
  double discountAmount;
  double charge;

  @Builder
  public ToolCharge(double rate, boolean weekdays, boolean weekends, boolean holidays) {
    this.rate = rate;
    this.onWeekdays = weekdays;
    this.onWeekends = weekends;
    this.onHolidays = holidays;
  }

  public void setDiscount(int discount) throws DiscountOutOfRangeException {
    if (discount < 0 || discount > 100) {
      throw new DiscountOutOfRangeException();
    }
    this.discountPercent = (double) discount / 100.0;
  }
}
