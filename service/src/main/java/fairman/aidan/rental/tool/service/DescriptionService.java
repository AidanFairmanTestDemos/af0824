package fairman.aidan.rental.tool.service;

import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.persistence.info.model.InfoDataModel;
import fairman.aidan.rental.tool.persistence.info.service.InfoDataService;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DescriptionService {

  private final ConversionService conversionService;
  private final InfoDataService infoDataService;

  ToolDescription getDescription(String toolCode) throws SQLException {
    InfoDataModel info = infoDataService.getToolInfo(toolCode);
    return conversionService.convert(info, ToolDescription.class);
  }
}
