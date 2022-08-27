package fairman.aidan.tool_rental.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ToolAgreementRequest {
  @NonNull String toolCode;
  @NonNull LocalDateTime startDate;
  int dayCount;
  @Default int discount = 0;
}
