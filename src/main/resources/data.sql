INSERT INTO currency (id,name) VALUES (1,'USD');
INSERT INTO currency (id,name) VALUES (2,'PEN');
INSERT INTO currency (id,name) VALUES (3,'EUR');

INSERT INTO exchange (id,currency_from_id,currency_to_id,rate, created_on) VALUES (1,1,2,3.64, '2021-02-02 17:43:51.300942');
INSERT INTO exchange (id,currency_from_id,currency_to_id,rate, created_on) VALUES (2,2,1,0.27, '2021-02-02 17:43:51.300942');