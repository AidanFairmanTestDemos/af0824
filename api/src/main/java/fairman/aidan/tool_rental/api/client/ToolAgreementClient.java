package fairman.aidan.tool_rental.api.client;

import fairman.aidan.tool_rental.api.dto.ToolAgreementRequest;
import fairman.aidan.tool_rental.api.dto.ToolAgreementResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/agreement")
public interface ToolAgreementClient {

  @PostMapping
  ToolAgreementResponse createAgreement(@RequestBody ToolAgreementRequest request);

  //could be extended to accept reservations.
}
