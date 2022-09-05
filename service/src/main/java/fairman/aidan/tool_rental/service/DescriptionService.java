package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.model.ToolDescription;
import fairman.aidan.tool_rental.persistence.info.model.InfoDataModel;
import fairman.aidan.tool_rental.persistence.info.service.InfoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class DescriptionService {

  @Autowired
  ConversionService conversionService;
  @Autowired
  private InfoDataService infoDataService;

  ToolDescription getDescription(String toolCode) {
    InfoDataModel info = infoDataService.getToolInfo(toolCode);
    return conversionService.convert(info, ToolDescription.class);
  }
}
