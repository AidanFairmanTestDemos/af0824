package fairman.aidan.tool_rental.controller;

import fairman.aidan.tool_rental.api.client.ToolAgreementClient;
import fairman.aidan.tool_rental.api.dto.ToolAgreementRequest;
import fairman.aidan.tool_rental.api.dto.ToolAgreementResponse;
import fairman.aidan.tool_rental.service.ToolAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;

// All any controller should do is
// convert request to service models and then call service.
// Response from service gets converted back to response model and returned.
@RestController
public class ToolAgreementController implements ToolAgreementClient {

  @Autowired ConversionService conversionService;

  @Autowired ToolAgreementService service;

  @Override
  public ToolAgreementResponse createAgreement(ToolAgreementRequest request) {

    return service;
  }
}
