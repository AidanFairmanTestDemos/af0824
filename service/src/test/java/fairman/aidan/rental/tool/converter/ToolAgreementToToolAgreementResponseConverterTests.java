package fairman.aidan.rental.tool.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fairman.aidan.rental.tool.api.dto.ToolAgreementResponse;
import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.model.ToolAgreement;
import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.model.ToolSchedule;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ToolAgreementToToolAgreementResponseConverterTests {

  private static final DecimalFormat ROUNDING = new DecimalFormat("0.00");
  private static final String CODE = "CODE";
  private static final String TYPE = "TYPE";
  private static final String BRAND = "BRAND";
  private static final String AGREEMENT = "I Agree!";
  private static final int DAYS = 4;
  private static final int CHARGEABLE = 3;
  private static final double RATE = 3.14;
  private static final double DISCOUNT_PERCENT = 0.25;
  private static final int MONTH = 6;
  private static final int YEAR = 1999;
  private static final int CHECKOUT_DAY = 4;
  private static final int RETURN_DAY = 8;
  private static final LocalDate CHECKOUT_DATE = LocalDate.of(YEAR, MONTH, CHECKOUT_DAY);
  private static final LocalDate RETURN_DATE = LocalDate.of(YEAR, MONTH, RETURN_DAY);
  private static final String CHECKOUT_DATE_STRING = "06/04/99";
  private static final String RETURN_DATE_STRING = "06/08/99";

  static {
    ROUNDING.setRoundingMode(RoundingMode.HALF_UP);
  }

  private ToolAgreementToToolAgreementResponseConverter converter;

  @BeforeEach
  private void beforeEach() {
    converter = new ToolAgreementToToolAgreementResponseConverter();
  }

  @Test
  public void convertsProperly() throws RentalDaysRangeException, DiscountOutOfRangeException {
    ToolSchedule schedule = ToolSchedule.builder()
        .rentalDays(DAYS)
        .rentalDate(CHECKOUT_DATE)
        .build();
    ToolCharge charge = ToolCharge.builder()
        .rate(RATE)
        .discount((int) (DISCOUNT_PERCENT * 100.0))
        .weekends(true)
        .weekdays(true)
        .holidays(false)
        .build();
    charge.setChargeableDays(CHARGEABLE);
    ToolDescription description = ToolDescription.builder()
        .type(TYPE)
        .brand(BRAND)
        .toolCode(CODE)
        .build();
    ToolAgreement agreement = ToolAgreement.builder()
        .agreementText(AGREEMENT)
        .toolSchedule(schedule)
        .toolCharge(charge)
        .toolDescription(description)
        .build();
    ToolAgreementResponse response = converter.convert(agreement);
    assertNotNull(response);
    assertEquals(CODE, response.getToolCode());
    assertEquals(TYPE, response.getToolType());
    assertEquals(BRAND, response.getToolBrand());
    assertEquals(DAYS, response.getRentalDays());
    assertEquals(CHECKOUT_DATE_STRING, response.getCheckoutDate());
    assertEquals(RETURN_DATE_STRING, response.getReturnDate());
    assertEquals(RATE, response.getDailyRate());
    assertEquals(CHARGEABLE, response.getChargeableDays());
    assertEquals(charge.getPreDiscountCharge(), response.getPreDiscountCharge());
    assertEquals(DISCOUNT_PERCENT, response.getDiscountPercent());
    assertEquals(charge.getDiscountAmount(), response.getDiscount());
    assertEquals(charge.getCharge(), response.getFinalCharge());
    assertEquals(AGREEMENT, response.getAgreementText());
  }
}
