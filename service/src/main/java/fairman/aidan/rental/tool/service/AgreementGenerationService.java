package fairman.aidan.rental.tool.service;

import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.model.ToolSchedule;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class AgreementGenerationService {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
  private static final String LINE_BREAK = System.lineSeparator();
  private static final String AGREEMENT_TEMPLATE =
      "Customer Agreement consists of the following terms:" + LINE_BREAK
          + "\tTool Code: %s " + LINE_BREAK
          + "\tTool Type: %s " + LINE_BREAK
          + "\tTool Brand: %s " + LINE_BREAK
          + "\tRental Days: %d " + LINE_BREAK
          + "\tCheck-out Date: %s " + LINE_BREAK
          + "\tDue Date: %s " + LINE_BREAK
          + "\tDaily Rate: %.2f " + LINE_BREAK
          + "\tChargeable Days: %d " + LINE_BREAK
          + "\tPre-discount charge: %.2f " + LINE_BREAK
          + "\tDiscount Percent: %d%% " + LINE_BREAK
          + "\tDiscount Amount: %.2f " + LINE_BREAK
          + "\tTotal Charge: %.2f " + LINE_BREAK
          + "Please present to customer and keep a copy for store records.";

  public String generateAgreement(
      ToolDescription description,
      ToolCharge charge,
      ToolSchedule schedule) {
    return String.format(AGREEMENT_TEMPLATE,
        description.getToolCode(),
        description.getType(),
        description.getBrand(),
        schedule.getRentalDays(),
        schedule.getRentalDate().format(FORMATTER),
        schedule.getReturnDate().format(FORMATTER),
        charge.getRate(),
        charge.getChargeableDays(),
        charge.getPreDiscountCharge(),
        (int) (charge.getDiscountPercent() * 100),
        charge.getDiscountAmount(),
        charge.getCharge());
  }
}
