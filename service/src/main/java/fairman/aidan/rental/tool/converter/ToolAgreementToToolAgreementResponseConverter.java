package fairman.aidan.rental.tool.converter;

import fairman.aidan.rental.tool.model.ToolAgreement;
import fairman.aidan.rental.tool.api.dto.ToolAgreementResponse;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ToolAgreementToToolAgreementResponseConverter implements
    Converter<ToolAgreement, ToolAgreementResponse> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

  @Override
  public ToolAgreementResponse convert(ToolAgreement source) {
    return ToolAgreementResponse.builder()
        .toolCode(source.getToolDescription().getToolCode())
        .toolType(source.getToolDescription().getType())
        .toolBrand(source.getToolDescription().getBrand())
        .rentalDays(source.getToolSchedule().getRentalDays())
        .checkoutDate(source.getToolSchedule().getRentalDate().format(FORMATTER))
        .returnDate(source.getToolSchedule().getReturnDate().format(FORMATTER))
        .dailyRate(source.getToolCharge().getRate())
        .chargeableDays(source.getToolCharge().getChargeableDays())
        .preDiscountCharge(source.getToolCharge().getPreDiscountCharge())
        .discountPercent(source.getToolCharge().getDiscountPercent())
        .discount(source.getToolCharge().getDiscountAmount())
        .finalCharge(source.getToolCharge().getCharge())
        .agreementText(source.getAgreementText())
        .build();
  }
}
