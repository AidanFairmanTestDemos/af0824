package fairman.aidan.rental.tool.persistence.charge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fairman.aidan.rental.tool.persistence.charge.model.ChargeDataModel;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChargeDataServiceIntegrationTests {

  private static final String TOOL_CODE = "CIRC";
  private static final String FAKE_TOOL_CODE = "FAKE";
  private static final double RATE = 3.14;
  private static final boolean WEEKEND = true;
  private static final boolean WEEKDAY = false;
  private static final boolean HOLIDAY = false;

  @Autowired
  private ChargeDataService service;

  @Test
  public void fetchesExpectedValues() {
    ChargeDataModel model = service.getToolCharge(TOOL_CODE);
    assertNotNull(model);
    assertEquals(RATE, model.getRate());
    assertEquals(WEEKEND, model.isOnWeekends());
    assertEquals(WEEKDAY, model.isOnWeekdays());
    assertEquals(HOLIDAY, model.isOnHolidays());
  }

  @Test
  public void throwsSQLExceptionOnBadToolCode() {
    assertThrows(SQLException.class, () -> {
      service.getToolCharge(FAKE_TOOL_CODE);
    });
  }
}
