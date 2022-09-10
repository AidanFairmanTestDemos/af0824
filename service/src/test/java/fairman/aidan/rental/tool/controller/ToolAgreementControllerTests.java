package fairman.aidan.rental.tool.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import fairman.aidan.rental.tool.api.dto.ToolAgreementRequest;
import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.service.ToolAgreementService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

@ExtendWith(MockitoExtension.class)
public class ToolAgreementControllerTests {

  private static final String TOOL_CODE = "ABCD";
  private static final String DATE_STRING = "9/9/14";
  private static final int DAY_COUNT = 5;
  private static final int DISCOUNT = 10;
  private ToolAgreementController controller;

  @Mock
  private ConversionService conversionService;
  @Mock
  private ToolAgreementService toolAgreementService;

  @BeforeEach
  public void beforeEach() {
    Mockito.reset(conversionService, toolAgreementService);
    controller = new ToolAgreementController(
        conversionService,
        toolAgreementService);
  }

  @Test
  public void ExceptionsAreAllowedToLeak() {
    ToolAgreementRequest request = ToolAgreementRequest.builder()
        .toolCode(TOOL_CODE)
        .dayCount(DAY_COUNT)
        .discount(DISCOUNT)
        .startDate(DATE_STRING)
        .build();

    when(conversionService.convert(any(), eq(LocalDate.class))).thenReturn(LocalDate.now());

    assertThrows(DiscountOutOfRangeException.class, () -> {
      when(toolAgreementService.generateToolAgreement(eq(TOOL_CODE), eq(DAY_COUNT), eq(DISCOUNT),
          any(
              LocalDate.class))).thenThrow(new DiscountOutOfRangeException());
      controller.createAgreement(request);
    });

    assertThrows(RentalDaysRangeException.class, () -> {
      when(toolAgreementService.generateToolAgreement(eq(TOOL_CODE), eq(DAY_COUNT), eq(DISCOUNT),
          any(
              LocalDate.class))).thenThrow(new RentalDaysRangeException());
      controller.createAgreement(request);
    });
  }

}
