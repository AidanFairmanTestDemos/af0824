package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.model.ToolAgreement;
import fairman.aidan.tool_rental.model.ToolCharge;
import fairman.aidan.tool_rental.model.ToolDescription;
import org.springframework.stereotype.Service;

@Service
public class AgreementGenerationService {
  private static final String AGREEMENT_TEMPLATE = "";
  public String generateAgreement(
      ToolDescription description,
      ToolCharge charge,
      ToolAgreement agreement){
    return String.format(AGREEMENT_TEMPLATE, )
  }
}
