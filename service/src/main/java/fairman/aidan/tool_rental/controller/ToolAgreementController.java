package fairman.aidan.tool_rental.controller;

import fairman.aidan.tool_rental.api.client.ToolAgreementClient;
import fairman.aidan.tool_rental.api.dto.ToolAgreementRequest;
import fairman.aidan.tool_rental.api.dto.ToolAgreementResponse;
import fairman.aidan.tool_rental.model.ToolAgreement;
import fairman.aidan.tool_rental.service.ToolAgreementService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

// All any controller should do is
// convert request to service models and then call service.
// Response from service gets converted back to response model and returned.
@RestController
public class ToolAgreementController implements ToolAgreementClient {
  private static final List<DateTimeFormatter> FORMATTERS = List.of(
      DateTimeFormatter.ofPattern("M/d/yy"),
      DateTimeFormatter.ofPattern("MM/dd/yy"),
      DateTimeFormatter.ofPattern("MM/d/yy"),
      DateTimeFormatter.ofPattern("M/dd/yy"));
  @Autowired
  ConversionService conversionService;

  @Autowired
  ToolAgreementService service;
  @Override
  public ToolAgreementResponse createAgreement(ToolAgreementRequest request) {
    LocalDate localDate = null;
    for (DateTimeFormatter formatter : FORMATTERS) {
      try{
        localDate = LocalDate.parse(request.getStartDate(), formatter);
      }catch (Exception e){
        // yum
      }
    }
    if(localDate == null){
      throw new RuntimeException("could not parse localDate from input");
    }
    try {

      ToolAgreement agreement = service.generateToolAgreement(
          request.getToolCode(),
          request.getDayCount(),
          request.getDiscount(),
          localDate
      );
      ToolAgreementResponse response = conversionService.convert(agreement, ToolAgreementResponse.class);
      return response;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
