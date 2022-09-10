package fairman.aidan.rental.tool.api.client;

import fairman.aidan.rental.tool.api.dto.ToolAgreementRequest;
import fairman.aidan.rental.tool.api.dto.ToolAgreementResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/agreement")
public interface ToolAgreementClient {

  @PostMapping
  ToolAgreementResponse createAgreement(@RequestBody ToolAgreementRequest request);

  //could be extended to accept reservations.
}
