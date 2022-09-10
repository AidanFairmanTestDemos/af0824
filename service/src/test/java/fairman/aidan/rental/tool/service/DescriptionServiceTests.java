package fairman.aidan.rental.tool.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import fairman.aidan.rental.tool.converter.InfoDataModelToToolDescriptionConverter;
import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.persistence.info.model.InfoDataModel;
import fairman.aidan.rental.tool.persistence.info.service.InfoDataService;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@ExtendWith(MockitoExtension.class)
public class DescriptionServiceTests {

  private static final String ID = "ABCD";
  private static final String TYPE = "Digger";
  private static final String BRAND = "ABC Construction";

  @Mock
  private InfoDataService infoDataService;

  private ConversionService conversionService;
  private DescriptionService service;

  @BeforeEach
  public void beforeEach() {
    Mockito.reset(infoDataService);
    DefaultConversionService dcs = new DefaultConversionService();
    dcs.addConverter(new InfoDataModelToToolDescriptionConverter());
    conversionService = dcs;
    service = new DescriptionService(conversionService, infoDataService);

  }

  @Test
  public void itWorks() throws SQLException {
    when(infoDataService.getToolInfo(eq(ID))).thenReturn(InfoDataModel.builder()
        .brand(BRAND)
        .type(TYPE)
        .id(ID)
        .build());
    ToolDescription description = service.getDescription(ID);
    assertNotNull(description);
    assertEquals(ID, description.getToolCode());
    assertEquals(TYPE, description.getType());
    assertEquals(BRAND, description.getBrand());
  }

  @Test
  public void leaksSqlException() throws SQLException {
    when(infoDataService.getToolInfo(any())).thenThrow(new SQLException());
    assertThrows(SQLException.class, () -> service.getDescription(ID));
  }
}
