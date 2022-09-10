package fairman.aidan.rental.tool.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

  private static final List<DateTimeFormatter> FORMATTERS = List.of(
      DateTimeFormatter.ofPattern("M/d/yy"),
      DateTimeFormatter.ofPattern("yyyy-MM-dd")
  );

  public LocalDate convert(String dateString) {
    LocalDate localDate = null;
    for (DateTimeFormatter formatter : FORMATTERS) {
      try {
        localDate = LocalDate.parse(dateString, formatter);
        if (localDate != null) {
          return localDate;
        }
      } catch (Exception e) {
        // we might fail, we just need to try the next one
      }
    }
    throw new RuntimeException("could not parse localDate from input");
  }
}
