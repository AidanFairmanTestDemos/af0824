package fairman.aidan.tool_rental.controller;

import fairman.aidan.tool_rental.api.client.ToolAgreementClient;
import fairman.aidan.tool_rental.api.dto.ToolAgreementRequest;
import fairman.aidan.tool_rental.api.dto.ToolAgreementResponse;
import fairman.aidan.tool_rental.model.ToolAgreement;
import fairman.aidan.tool_rental.service.ToolAgreementService;
import java.time.LocalDate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ToolAgreementController implements ToolAgreementClient {

  @NonNull
  private final ConversionService conversionService;

  @NonNull
  private final ToolAgreementService service;

  @Override
  @SneakyThrows
  public ToolAgreementResponse createAgreement(ToolAgreementRequest request) {
    LocalDate localDate = conversionService.convert(request.getStartDate(), LocalDate.class);
    ToolAgreement agreement = service.generateToolAgreement(
        request.getToolCode(),
        request.getDayCount(),
        request.getDiscount(),
        localDate
    );
    return conversionService.convert(agreement,
        ToolAgreementResponse.class);
  }
}
