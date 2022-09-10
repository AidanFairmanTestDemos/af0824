package fairman.aidan.rental.tool.converter;

import fairman.aidan.rental.tool.model.ToolDescription;
import fairman.aidan.rental.tool.persistence.info.model.InfoDataModel;
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
