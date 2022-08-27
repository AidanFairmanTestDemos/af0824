package fairman.aidan.tool_rental.controller;

import fairman.aidan.tool_rental.api.client.ToolAgreementClient;
import fairman.aidan.tool_rental.api.dto.ToolAgreementRequest;
import fairman.aidan.tool_rental.api.dto.ToolAgreementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToolAgreementController implements ToolAgreementClient {

  @Autowired ConversionService conversionService;

  @Override
  public ToolAgreementResponse createAgreement(ToolAgreementRequest request) {
    return null;
  }
}
