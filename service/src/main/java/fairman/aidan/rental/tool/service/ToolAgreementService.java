package fairman.aidan.rental.tool.service;

import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.model.ToolAgreement;
import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.model.ToolSchedule;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToolAgreementService {

  private final AgreementGenerationService agreementGenerationService;
  private final ChargeService chargeService;
  private final DescriptionService descriptionService;

  public ToolAgreement generateToolAgreement(String toolCode, int rentalDays, int discount,
      LocalDate startDate)
      throws DiscountOutOfRangeException, RentalDaysRangeException, SQLException {
    ToolDescription description = descriptionService.getDescription(toolCode);
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(startDate)
        .rentalDays(rentalDays)
        .build();
    ToolCharge charges = chargeService.getChargesForTool(toolCode, discount, schedule);
    String agreementText = agreementGenerationService.generateAgreement(description, charges,
        schedule);
    return ToolAgreement.builder()
        .toolDescription(description)
        .toolCharge(charges)
        .toolSchedule(schedule)
        .agreementText(agreementText)
        .build();
  }
}
