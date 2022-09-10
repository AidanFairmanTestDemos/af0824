package fairman.aidan.rental.tool.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.model.ToolSchedule;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgreementGenerationServiceTests {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
  private static final String TOOL_CODE = "ABCD";
  private static final String BRAND = "ABC Construction";
  private static final String TYPE = "Digger";
  private static final int DISCOUNT = 10;
  private static final int CHARGEABLE_DAYS = 4;
  private static final double RATE = 4.0;
  private static final boolean WEEKENDS = true;
  private static final boolean WEEKDAYS = true;
  private static final boolean HOLIDAYS = true;
  private static final double PRE_DISCOUNT = RATE * CHARGEABLE_DAYS;
  private static final double DISCOUNT_AMOUNT = PRE_DISCOUNT * (double) ((double) DISCOUNT / 100.0);
  private static final double TOTAL = PRE_DISCOUNT - DISCOUNT_AMOUNT;
  private static final int RENTAL_DAYS = 4;
  private static final LocalDate RENTAL_DATE = LocalDate.now();
  private static final LocalDate RETURN_DATE = RENTAL_DATE.plusDays(RENTAL_DAYS);
  private AgreementGenerationService service;

  @BeforeEach
  public void beforeEach() {
    service = new AgreementGenerationService();
  }

  @Test
  public void returnsCorrectAgreementValues()
      throws RentalDaysRangeException, DiscountOutOfRangeException {
    ToolDescription description = ToolDescription.builder()
        .brand(BRAND)
        .type(TYPE)
        .toolCode(TOOL_CODE)
        .build();
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDays(RENTAL_DAYS)
        .rentalDate(RENTAL_DATE)
        .build();
    ToolCharge charges = ToolCharge.builder()
        .rate(RATE)
        .weekends(WEEKENDS)
        .holidays(HOLIDAYS)
        .weekdays(WEEKDAYS)
        .discount(DISCOUNT)
        .build();
    charges.setPreDiscountCharge(PRE_DISCOUNT);
    charges.setDiscountAmount(DISCOUNT_AMOUNT);
    charges.setChargeableDays(CHARGEABLE_DAYS);
    charges.setCharge(TOTAL);
    String agreement = service.generateAgreement(description, charges, schedule);
    assertNotNull(agreement);
    assertTrue(agreement.contains("Tool Code: " + TOOL_CODE));
    assertTrue(agreement.contains("Tool Type: " + TYPE));
    assertTrue(agreement.contains("Tool Brand: " + BRAND));
    assertTrue(agreement.contains("Rental Days: " + RENTAL_DAYS));
    assertTrue(agreement.contains("Check-out Date: " + FORMATTER.format(RENTAL_DATE)));
    assertTrue(agreement.contains("Due Date: " + FORMATTER.format(RETURN_DATE)));
    assertTrue(agreement.contains(String.format("Daily Rate: %.2f", RATE)));
    assertTrue(agreement.contains("Chargeable Days: " + CHARGEABLE_DAYS));
    assertTrue(agreement.contains("Pre-discount charge: " + PRE_DISCOUNT));
    assertTrue(agreement.contains("Discount Percent: " + DISCOUNT + "%"));
    assertTrue(agreement.contains("Discount Amount: " + DISCOUNT_AMOUNT));
    assertTrue(agreement.contains("Total Charge: " + TOTAL));
  }
}
