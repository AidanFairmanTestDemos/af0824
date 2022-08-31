package fairman.aidan.tool_rental.api.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Value
@Builder
public class ToolAgreementRequest {
  @NonNull String toolCode;
  @NonNull String startDate;
  int dayCount;
  @Default int discount = 0;
}
