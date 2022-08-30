package fairman.aidan.tool_rental.service;

import fairman.aidan.tool_rental.data.info.access.InfoDataService;
import fairman.aidan.tool_rental.data.info.model.InfoDataModel;
import fairman.aidan.tool_rental.model.ToolDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class DescriptionService {
  @Autowired
  private InfoDataService infoDataService;

  @Autowired
  ConversionService conversionService;

  ToolDescription getDescription(String toolCode){
    InfoDataModel info = infoDataService.getToolInfo(toolCode);
    return conversionService.convert(info, ToolDescription.class);
  }
}
