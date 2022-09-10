package fairman.aidan.rental.tool.controller;

import fairman.aidan.rental.tool.model.ToolAgreement;
import fairman.aidan.rental.tool.service.ToolAgreementService;
import fairman.aidan.rental.tool.api.client.ToolAgreementClient;
import fairman.aidan.rental.tool.api.dto.ToolAgreementRequest;
import fairman.aidan.rental.tool.api.dto.ToolAgreementResponse;
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
