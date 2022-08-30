package fairman.aidan.tool_rental.data.charge.access;

import fairman.aidan.tool_rental.data.charge.model.ChargeDataModel;
import fairman.aidan.tool_rental.data.charge.model.ChargeDataModel.ChargeDataModelBuilder;
import fairman.aidan.tool_rental.model.ToolCharge;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeDataService {
  private static final String RATE = "rate";
  private static final String WEEKDAY = "weekday";
  private static final String WEEKEND = "weekend";
  private static final String HOLIDAY = "holiday";

  @Autowired
  private DataSource dataSource;

  public ChargeDataModel getToolCharge(String toolCode){
    String query = "SELECT tc.rate,"
        + " tc." + WEEKDAY + ","
        + " tc." + WEEKEND + ","
        + " tc." + HOLIDAY
        + " FROM tool_charge AS tc"
        + " JOIN tool_info AS ti on ti.tool_type_id = tc.tool_type_id"
        + " WHERE ti.id = ?";
    try(Connection connection = dataSource.getConnection()){
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setString(1, toolCode);
        try(ResultSet resultSet = statement.executeQuery()){
          double rate = resultSet.getDouble(RATE);
          boolean onWeekday = resultSet.getBoolean(WEEKDAY);
          boolean onWeekend = resultSet.getBoolean(WEEKEND);
          boolean onHoliday = resultSet.getBoolean(HOLIDAY);
          return ChargeDataModel.builder()
              .onHolidays(onHoliday)
              .onWeekdays(onWeekday)
              .onWeekends(onWeekend)
              .rate(rate)
              .build();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not find charge information for tool code " + toolCode, e);
    }
  }
}
