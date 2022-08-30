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
  id INT PRIMARY KEY,
  tool_type_id INT NOT NULL,
  rate NUMERIC NOT NULL,
  weekday BOOLEAN NOT NULL,
  weekend BOOLEAN NOT NULL,
  holiday BOOLEAN NOT NULL,
  FOREIGN KEY(tool_type_id) REFERENCES tool_type(id),
  UNIQUE(tool_type_name)
);

INSERT INTO tool_type (id, name) VALUES (1, 'Ladder'), (2, 'Chainsaw'), (3, 'Jackhammer');
INSERT INTO tool_info (id, tool_type_id, brand)
  VALUES ('CHNS', 2, 'Stihl'),
         ('LADW', 1, 'Werner'),
         ('JAKD', 3, 'DeWalt'),
         ('JAKR', 3, 'Ridgid');
INSERT INTO tool_charge (tool_type_id, rate, weekday, weekend, holiday)
  VALUES (1, 1.99, TRUE, TRUE, FALSE),
         (2, 1.49, TRUE, FALSE, TRUE),
         (3, 2.99, TRUE, FALSE, FALSE);