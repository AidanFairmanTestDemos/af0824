package fairman.aidan.tool_rental.persistence.info.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fairman.aidan.tool_rental.persistence.info.model.InfoDataModel;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class InfoDataServiceIntegrationTests {

  private static final String FAKE_TOOL_CODE = "FAKE";
  private static final String TOOL_CODE = "CIRC";
  private static final String TOOL_BRAND = "Craftsman";
  private static final String TOOL_TYPE = "Circular Saw";

  @Autowired
  private InfoDataService service;

  @Test
  public void fetchesExpectedValues() {
    InfoDataModel model = service.getToolInfo(TOOL_CODE);
    assertNotNull(model);
    assertEquals(TOOL_CODE, model.getId());
    assertEquals(TOOL_BRAND, model.getBrand());
    assertEquals(TOOL_TYPE, model.getType());
  }

  @Test
  public void throwsSQLExceptionOnBadToolCode() {
    assertThrows(SQLException.class, () -> {
      service.getToolInfo(FAKE_TOOL_CODE);
    });
  }
}
