package fairman.aidan.tool_rental.persistence.info.access;

import fairman.aidan.tool_rental.persistence.info.model.InfoDataModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoDataService {

  private static final String BRAND = "brand";
  private static final String NAME = "name";
  private static final String ID = "id";
  @Autowired
  private DataSource dataSource;

  public InfoDataModel getToolInfo(String toolCode) {
    String query = "SELECT ti." + ID + ","
        + " tt." + NAME + ","
        + " ti." + BRAND
        + " FROM tool_info AS ti"
        + " JOIN tool_type AS tt ON tt.id = ti.tool_type_id"
        + " WHERE ti.id = ?";

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, toolCode);
        try (ResultSet resultSet = statement.executeQuery()) {
          if(resultSet.first()) {
            return InfoDataModel.builder()
                .brand(resultSet.getString(BRAND))
                .id(resultSet.getString(ID))
                .type(resultSet.getString(NAME))
                .build();
          }else{
            throw new SQLException();
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Could not find data info for tool code " + toolCode, e);
    }
  }
}
