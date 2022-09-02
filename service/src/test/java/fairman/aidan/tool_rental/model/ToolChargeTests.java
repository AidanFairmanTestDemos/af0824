package fairman.aidan.tool_rental.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fairman.aidan.tool_rental.errors.DiscountOutOfRangeException;
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

  private ToolCharge charges;

  @BeforeEach
  public void beforeEach() {
    charges = ToolCharge.builder()
        .rate(RATE)
        .weekends(ON_WEEKENDS)
        .weekdays(ON_WEEKDAYS)
        .holidays(ON_HOLIDAYS)
        .build();
  }

  @Test
  public void discountSetsProperly() throws DiscountOutOfRangeException {
    charges.setDiscount(DISCOUNT);
    assertEquals(DISCOUNT_PERCENT, charges.getDiscountPercent());
  }

  @Test
  public void discountMayNotBeNegative() {
    final int NEGATIVE_DISCOUNT = -1;
    assertThrows(DiscountOutOfRangeException.class, () -> {
      charges.setDiscount(NEGATIVE_DISCOUNT);
    });
  }

  @Test
  public void discountMayNotExceed100() {
    final int BIG_DISCOUNT = 101;
    assertThrows(DiscountOutOfRangeException.class, () -> {
      charges.setDiscount(BIG_DISCOUNT);
    });
  }

  @Test
  public void discountAccepts0To100() {
    final int NO_DISCOUNT = 0;
    final int MID_DISCOUNT = 50;
    final int FULL_DISCOUNT = 100;
    assertDoesNotThrow(() -> {
      charges.setDiscount(NO_DISCOUNT);
    });
    assertDoesNotThrow(() -> {
      charges.setDiscount(MID_DISCOUNT);
    });
    assertDoesNotThrow(() -> {
      charges.setDiscount(FULL_DISCOUNT);
    });
  }
}
