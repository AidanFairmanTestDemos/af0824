package fairman.aidan.tool_rental.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ToolAgreementResponse {
  @NonNull String toolCode;
  @NonNull String toolType;
  @NonNull String toolBrand;
  int rentalDays;
  @NonNull LocalDateTime checkoutDate;
  @NonNull LocalDateTime returnDate;
  double dailyRate;
  int chargeableDays;
  double preDiscountCharge;
  double discountPercent;
  double discount;
  double finalCharge;
  @NonNull String agreementText;
}