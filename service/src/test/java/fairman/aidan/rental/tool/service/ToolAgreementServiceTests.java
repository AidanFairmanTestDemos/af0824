package fairman.aidan.rental.tool.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.model.ToolAgreement;
import fairman.aidan.rental.tool.model.ToolCharge;
import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.model.ToolSchedule;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ToolAgreementServiceTests {

  private static final boolean CHARGE_DAY = true;
  private static final double RATE = Math.PI;
  private static final int DISCOUNT = 5;
  private static final int BAD_DISCOUNT = -25;
  private static final String CODE = "ABCD";
  private static final String BRAND = "ABC Construction";
  private static final String TYPE = "Digger";
  private static final LocalDate DATE = LocalDate.now();
  private static final int RENTAL_DAYS = 3;
  private static final int BAD_RENTAL_DAYS = -5;
  private static final String AGREEMENT = "I AGREE!";

  @Mock
  private AgreementGenerationService agreementGenerationService;
  @Mock
  private ChargeService chargeService;
  @Mock
  private DescriptionService descriptionService;

  private ToolAgreementService service;

  @BeforeEach
  public void beforeEach() {
    Mockito.reset(agreementGenerationService, chargeService, descriptionService);
    service = new ToolAgreementService(agreementGenerationService, chargeService,
        descriptionService);
  }

  @Test
  public void happyPath()
      throws SQLException, DiscountOutOfRangeException, RentalDaysRangeException {
    when(descriptionService.getDescription(any(String.class))).thenReturn(
        ToolDescription.builder().toolCode(CODE).type(TYPE).brand(BRAND).build());
    when(chargeService.getChargesForTool(any(String.class), anyInt(),
        any(ToolSchedule.class))).thenReturn(
        ToolCharge.builder().rate(RATE).weekends(CHARGE_DAY).weekdays(CHARGE_DAY)
            .holidays(CHARGE_DAY).discount(DISCOUNT).build());
    when(agreementGenerationService.generateAgreement(any(ToolDescription.class),
        any(ToolCharge.class), any(ToolSchedule.class))).thenReturn(AGREEMENT);
    ToolAgreement toolAgreement = service.generateToolAgreement(CODE, RENTAL_DAYS, DISCOUNT, DATE);
    assertNotNull(toolAgreement);
  }

  @Test
  public void leaksSqlException() throws SQLException {
    when(descriptionService.getDescription(any(String.class))).thenThrow(new SQLException());
    assertThrows(SQLException.class,
        () -> service.generateToolAgreement(CODE, RENTAL_DAYS, DISCOUNT, DATE));

  }

  @Test
  public void leaksRentalRangeException() throws SQLException {
    when(descriptionService.getDescription(any(String.class))).thenReturn(
        ToolDescription.builder().toolCode(CODE).type(TYPE).brand(BRAND).build());
    assertThrows(RentalDaysRangeException.class,
        () -> service.generateToolAgreement(CODE, BAD_RENTAL_DAYS, DISCOUNT, DATE));
  }

  @Test
  public void leaksDiscountOutOfRangeException() throws DiscountOutOfRangeException, SQLException {
    when(descriptionService.getDescription(any(String.class))).thenReturn(
        ToolDescription.builder().toolCode(CODE).type(TYPE).brand(BRAND).build());
    when(chargeService.getChargesForTool(any(String.class), anyInt(),
        any(ToolSchedule.class))).thenThrow(new DiscountOutOfRangeException());
    assertThrows(DiscountOutOfRangeException.class, () -> {
      service.generateToolAgreement(CODE, RENTAL_DAYS, BAD_DISCOUNT, DATE);
    });
  }
}
