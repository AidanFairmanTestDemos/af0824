package fairman.aidan.tool_rental.errors;

public class DiscountOutOfRangeException extends ToolAgreementException {

  public DiscountOutOfRangeException() {
    super("Discount must be in the range of 0 to 100, inclusive.");
  }
}
