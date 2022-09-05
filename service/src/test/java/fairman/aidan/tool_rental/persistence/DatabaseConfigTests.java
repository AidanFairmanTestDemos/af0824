package fairman.aidan.tool_rental.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DatabaseConfigTests {

  private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
  private static final String URL_FIELD = "url";
  private static final String USER = "sa";
  private static final String USER_FIELD = "user";
  private static final String PASS = "sa";
  private static final String PASS_FIELD = "pass";
  private static final String DRIVER_CLASS = "org.h2.Driver";
  private static final String DRIVER_FIELD = "driverClassName";

  @Autowired
  private DatabaseConfig databaseConfig;

  @Test
  public void configIsGettingExpectedValues() throws NoSuchFieldException, IllegalAccessException {
    Class<DatabaseConfig> clazz = DatabaseConfig.class;
    Map<String, Field> fields = new HashMap<>();
    fields.put(URL_FIELD, clazz.getDeclaredField(URL_FIELD));
    fields.put(USER_FIELD, clazz.getDeclaredField(USER_FIELD));
    fields.put(PASS_FIELD, clazz.getDeclaredField(PASS_FIELD));
    fields.put(DRIVER_FIELD, clazz.getDeclaredField(DRIVER_FIELD));
    fields.forEach((k, v) -> v.setAccessible(true));
    assertEquals(URL, fields.get(URL_FIELD).get(databaseConfig));
    assertEquals(USER, fields.get(USER_FIELD).get(databaseConfig));
    assertEquals(PASS, fields.get(PASS_FIELD).get(databaseConfig));
    assertEquals(DRIVER_CLASS, fields.get(DRIVER_FIELD).get(databaseConfig));
  }
}
