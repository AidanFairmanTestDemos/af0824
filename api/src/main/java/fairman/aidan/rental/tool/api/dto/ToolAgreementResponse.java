package fairman.aidan.rental.tool.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ToolAgreementResponse {

  @NonNull
  String toolCode;
  @NonNull
  String toolType;
  @NonNull
  String toolBrand;
  int rentalDays;
  @NonNull
  String checkoutDate;
  @NonNull
  String returnDate;
  double dailyRate;
  int chargeableDays;
  double preDiscountCharge;
  double discountPercent;
  double discount;
  double finalCharge;
  @NonNull
  String agreementText;
}
