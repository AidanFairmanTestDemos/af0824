package fairman.aidan.tool_rental.common;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig{

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String pass;
  @Value("${spring.datasource.driverClassName}")
  private String driverClassName;

  @Bean
  public DataSource dataSource(){
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(pass);
    dataSource.setDriverClassName(driverClassName);
    return dataSource;
  }

}