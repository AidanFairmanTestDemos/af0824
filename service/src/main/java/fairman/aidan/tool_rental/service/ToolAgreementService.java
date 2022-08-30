package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.model.ToolAgreement;
import fairman.aidan.tool_rental.model.ToolAgreement.ToolAgreementBuilder;
import fairman.aidan.tool_rental.model.ToolCharge;
import fairman.aidan.tool_rental.model.ToolDescription;
import fairman.aidan.tool_rental.model.ToolSchedule;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolAgreementService {
  @Autowired private AgreementGenerationService agreementGenerationService;
  @Autowired private ChargeService chargeService;
  @Autowired private ChargeScheduleService chargeScheduleService;
  @Autowired private DescriptionService descriptionService;
  public ToolAgreement generateToolAgreement(String toolCode, int rentalDays, int discount, LocalDate startDate){
    ToolAgreementBuilder builder = ToolAgreement.builder();
    ToolDescription description = descriptionService.getDescription(toolCode);
    ToolCharge charges = chargeService.getChargesForTool(toolCode, discount);
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDate(startDate)
        .rentalDays(rentalDays)
        .build();
    charges = chargeScheduleService.getToolChargeSchedule(schedule, charges);
    String agreementText = agreementGenerationService
    return builder.build();
  }
}
