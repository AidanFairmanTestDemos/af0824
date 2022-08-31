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