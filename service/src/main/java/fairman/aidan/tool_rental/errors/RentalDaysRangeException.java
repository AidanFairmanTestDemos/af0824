package fairman.aidan.tool_rental.errors;

public class RentalDaysRangeException extends ToolAgreementException {

  public RentalDaysRangeException() {
    super("Rental length must be 1 or more days.");
  }
}
