package fairman.aidan.rental.tool.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.persistence.info.model.InfoDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InfoDataModelToToolDescriptionConverterTests {

  private static final String BRAND = "Toshiba";
  private static final String TOOL_CODE = "MAST";
  private static final String TOOL_TYPE = "Master Key";

  private InfoDataModelToToolDescriptionConverter converter;

  @BeforeEach
  public void beforeEach() {
    converter = new InfoDataModelToToolDescriptionConverter();
  }

  @Test
  public void properlyConverts() {
    InfoDataModel dataModel = InfoDataModel.builder()
        .brand(BRAND)
        .type(TOOL_TYPE)
        .id(TOOL_CODE)
        .build();
    ToolDescription description = converter.convert(dataModel);
    assertNotNull(description);
    assertEquals(BRAND, description.getBrand());
    assertEquals(TOOL_CODE, description.getToolCode());
    assertEquals(TOOL_TYPE, description.getType());
  }
}
