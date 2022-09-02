package fairman.aidan.tool_rental.converter;

import fairman.aidan.tool_rental.persistence.info.model.InfoDataModel;
import fairman.aidan.tool_rental.model.ToolDescription;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InfoDataModelToToolDescriptionConverter implements
    Converter<InfoDataModel, ToolDescription> {

  @Override
  public ToolDescription convert(InfoDataModel source) {
    return ToolDescription.builder()
        .brand(source.getBrand())
        .toolCode(source.getId())
        .type(source.getType())
        .build();
  }
}
