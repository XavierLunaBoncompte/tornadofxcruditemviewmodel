DROP DATABASE IF EXISTS pizzeria;
CREATE DATABASE pizzeria;
DROP USER IF EXISTS admins;
CREATE USER admins IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON pizzeria.* TO admins WITH GRANT OPTION;
USE pizzeria;

CREATE TABLE client
	(telefon			VARCHAR(9)		PRIMARY KEY,
	nom					VARCHAR(20)		NOT NULL
	)ENGINE=InnoDB
	;
    
INSERT INTO client VALUE ('937853354','Josep Vila');
INSERT INTO client VALUE ('937883402','Carme Garcia');
INSERT INTO client VALUE ('606989866','Enric Miralles');
INSERT INTO client VALUE ('937753222','Miquel Bover');
INSERT INTO client VALUE ('937862655','Marta Ribas');
INSERT INTO client VALUE ('937858555','Guillem Jam');
INSERT INTO client VALUE ('626895456','Júlia Guillén');

SELECT * FROM client;