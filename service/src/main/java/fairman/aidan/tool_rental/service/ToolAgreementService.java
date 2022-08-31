package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.errors.DiscountOutOfRangeException;
import fairman.aidan.tool_rental.errors.RentalDaysRangeException;
import fairman.aidan.tool_rental.model.ToolAgreement;
import fairman.aidan.tool_rental.model.ToolCharge;
import fairman.aidan.tool_rental.model.ToolDescription;
import fairman.aidan.tool_rental.model.ToolSchedule;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolAgreementService {

  @Autowired
  private AgreementGenerationService agreementGenerationService;
  @Autowired
  private ChargeService chargeService;
  @Autowired
  private ChargeScheduleService chargeScheduleService;
  @Autowired
  private DescriptionService descriptionService;

  public ToolAgreement generateToolAgreement(String toolCode, int rentalDays, int discount,
      LocalDate startDate)
      throws DiscountOutOfRangeException, RentalDaysRangeException {
    ToolDescription description = descriptionService.getDescription(toolCode);
    ToolCharge charges = chargeService.getChargesForTool(toolCode, discount);
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(startDate)
        .rentalDays(rentalDays)
        .build();
    charges = chargeScheduleService.getToolChargeSchedule(schedule, charges);
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
