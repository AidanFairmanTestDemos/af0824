CREATE TABLE IF NOT EXISTS tool_type(
  id INT PRIMARY KEY,
  name VARCHAR(24) NOT NULL
);

CREATE TABLE IF NOT EXISTS tool_info (
  id CHAR(4) PRIMARY KEY,
  tool_type_id INT NOT NULL,
  brand VARCHAR(24) NOT NULL,
  UNIQUE(tool_type_id, brand),
  FOREIGN KEY(tool_type_id) REFERENCES tool_type(id)
);

CREATE TABLE IF NOT EXISTS tool_charge(
  id INT PRIMARY KEY AUTO_INCREMENT,
  tool_type_id INT NOT NULL,
  rate DOUBLE PRECISION NOT NULL,
  weekday BOOLEAN NOT NULL,
  weekend BOOLEAN NOT NULL,
  holiday BOOLEAN NOT NULL,
  FOREIGN KEY(tool_type_id) REFERENCES tool_type(id),
  UNIQUE(tool_type_id)
);