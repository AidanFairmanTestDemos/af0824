package fairman.aidan.rental.tool.errors;

public class RentalDaysRangeException extends ToolAgreementException {

  public RentalDaysRangeException() {
    super("Rental length must be 1 or more days.");
  }
}
