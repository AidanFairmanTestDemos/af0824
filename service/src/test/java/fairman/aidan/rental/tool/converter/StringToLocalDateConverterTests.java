package fairman.aidan.rental.tool.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StringToLocalDateConverterTests {

  private StringToLocalDateConverter converter;

  @BeforeEach
  public void beforeEach() {
    converter = new StringToLocalDateConverter();
  }

  @Test
  public void canConvert2DigitMonthAndDay() {
    String dateString = "12/11/20";
    LocalDate date = LocalDate.of(2020, 12, 11);
    LocalDate returned = converter.convert(dateString);
    assertNotNull(returned);
    assertTrue(date.isEqual(returned));
  }

  @Test
  public void canConvert1DMonthAnd2DDay() {
    String dateString = "6/11/20";
    LocalDate date = LocalDate.of(2020, 6, 11);
    LocalDate returned = converter.convert(dateString);
    assertNotNull(returned);
    assertTrue(date.isEqual(returned));
  }

  @Test
  public void canConvert2DMonthAnd1DDay() {
    String dateString = "10/5/20";
    LocalDate date = LocalDate.of(2020, 10, 5);
    LocalDate returned = converter.convert(dateString);
    assertNotNull(returned);
    assertTrue(date.isEqual(returned));
  }

  @Test
  public void canConvert1DigitMonthAndDay() {
    String dateString = "6/5/20";
    LocalDate date = LocalDate.of(2020, 6, 5);
    LocalDate returned = converter.convert(dateString);
    assertNotNull(returned);
    assertTrue(date.isEqual(returned));
  }

  @Test
  public void canConvertIsoDate() {
    String dateString = "2020-04-09";
    LocalDate date = LocalDate.of(2020, 4, 9);
    LocalDate returned = converter.convert(dateString);
    assertNotNull(returned);
    assertTrue(date.isEqual(returned));
  }

  @Test
  public void throwsRuntimeExceptionOnUnknownPattern() {
    String dateString = "23-09-20";
    assertThrows(RuntimeException.class, () -> converter.convert(dateString));
  }

}
