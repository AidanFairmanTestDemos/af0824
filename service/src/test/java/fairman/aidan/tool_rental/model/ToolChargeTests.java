package fairman.aidan.tool_rental.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fairman.aidan.tool_rental.errors.DiscountOutOfRangeException;
import fairman.aidan.tool_rental.model.ToolCharge.ToolChargeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ToolChargeTests {

  private static final boolean ON_WEEKENDS = false;
  private static final boolean ON_WEEKDAYS = false;
  private static final boolean ON_HOLIDAYS = false;
  private static final double RATE = Math.E;
  private static final int DISCOUNT = 25;
  private static final double DISCOUNT_PERCENT = 0.25;

  private ToolChargeBuilder chargesBuilder;

  @BeforeEach
  public void beforeEach() throws DiscountOutOfRangeException {
    chargesBuilder = ToolCharge.builder()
        .rate(RATE)
        .weekends(ON_WEEKENDS)
        .weekdays(ON_WEEKDAYS)
        .holidays(ON_HOLIDAYS);
  }

  @Test
  public void discountSetsProperly() throws DiscountOutOfRangeException {
    ToolCharge charges = chargesBuilder.discount(DISCOUNT).build();
    assertEquals(DISCOUNT_PERCENT, charges.getDiscountPercent());
  }

  @Test
  public void discountMayNotBeNegative() {
    final int NEGATIVE_DISCOUNT = -1;
    assertThrows(DiscountOutOfRangeException.class, () -> {
      chargesBuilder.discount(NEGATIVE_DISCOUNT).build();
    });
  }

  @Test
  public void discountMayNotExceed100() {
    final int BIG_DISCOUNT = 101;
    assertThrows(DiscountOutOfRangeException.class, () -> {
      chargesBuilder.discount(BIG_DISCOUNT).build();
    });
  }

  @Test
  public void discountAccepts0To100() {
    final int NO_DISCOUNT = 0;
    final int MID_DISCOUNT = 50;
    final int FULL_DISCOUNT = 100;
    assertDoesNotThrow(() -> {
      chargesBuilder.discount(NO_DISCOUNT).build();
    });
    assertDoesNotThrow(() -> {
      chargesBuilder.discount(MID_DISCOUNT).build();
    });
    assertDoesNotThrow(() -> {
      chargesBuilder.discount(FULL_DISCOUNT).build();
    });
  }
}
