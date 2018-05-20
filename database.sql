DROP DATABASE IF EXISTS nordic_motorhomes;
CREATE DATABASE nordic_motorhomes;
USE nordic_motorhomes;

DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(25) NOT NULL,
    lastname VARCHAR(25) NOT NULL,
    CPR VARCHAR(11) NOT NULL,
    address VARCHAR(50) NOT NULL,
    postalcode VARCHAR(10) NOT NULL,
    city VARCHAR(20),
    country VARCHAR(20),
    phone VARCHAR(15)
);

DROP TABLE IF EXISTS models;
CREATE TABLE models(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    manufacturer VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    bed_count INT(1) NOT NULL,
    seats INT(1) NOT NULL,
    weight INT(5) NOT NULL,
    description VARCHAR(255)
);

DROP TABLE IF EXISTS motorhomes;
CREATE TABLE motorhomes(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	model INT(5) NOT NULL,
    gearbox VARCHAR(10) NOT NULL,
    year INT(4) NOT NULL,
    mileage INT(7) NOT NULL,
    transmission VARCHAR(3) NOT NULL,
    power INT(3) NOT NULL,
    price INT(4) NOT NULL,
    FOREIGN KEY (model) REFERENCES models(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS reservations;
CREATE TABLE reservations(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	date_from DATE NOT NULL,
    date_to DATE NOT NULL,
    motorhome_id INT(5) NOT NULL,
    customer_id INT(5) NOT NULL,
    status VARCHAR(20) NOT NULL,
    date_booked DATE NOT NULL,
    total FLOAT,
    FOREIGN KEY (motorhome_id) REFERENCES motorhomes(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS extras;
CREATE TABLE extras(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY
);

DROP TABLE IF EXISTS reservationextras;
CREATE TABLE reservationextras(
	extra_id INT(5) NOT NULL,
    reservation_id INT(5) NOT NULL,
    FOREIGN KEY (extra_id) REFERENCES extras(id) ON DELETE CASCADE,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS payments;
CREATE TABLE payments(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT(5) NOT NULL,
    ammount FLOAT,
    date DATE,
    description VARCHAR(50),
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(100) NOT NULL,
    distance FLOAT,
    reservation_id INT(5),
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS picksdrops;
CREATE TABLE picksdrops(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    mileage INT(6) NOT NULL,
    notes VARCHAR(255),
    reservation_id INT(5) NOT NULL,
    fuel FLOAT NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS checkup;
CREATE TABLE checkup(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	reservation_id INT(5) NOT NULL,
    date DATE NOT NULL,
    lights INT(1),
    chasis INT(1),
    engine INT(1),
    interior INT(1),
    exterior INT(1),
    notes VARCHAR(255),
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS service;
CREATE TABLE service(
	id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT(5) NOT NULL,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL,
    description VARCHAR(255),
    lights INT(1),
    chasis INT(1),
    engine INT(1),
    interior INT(1),
    exterior INT(1),
    ammount FLOAT,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

CREATE OR REPLACE VIEW bookings AS
SELECT `nordic_motorhomes`.`reservations`.`id` AS `id`,`nordic_motorhomes`.`reservations`.`date_from` AS `date_from`,
`nordic_motorhomes`.`reservations`.`date_to` AS `date_to`,`nordic_motorhomes`.`reservations`.`motorhome_id` AS `motorhome_id`,
`nordic_motorhomes`.`reservations`.`customer_id` AS `customer_id`,`nordic_motorhomes`.`reservations`.`status` AS `status`,
`nordic_motorhomes`.`reservations`.`date_booked` AS `date_booked`,`nordic_motorhomes`.`customers`.`firstname` AS `firstname`,
`nordic_motorhomes`.`customers`.`lastname` AS `lastname`,`nordic_motorhomes`.`motorhomes`.`model` AS `model_id`,
`nordic_motorhomes`.`models`.`manufacturer` AS `manufacturer`,`nordic_motorhomes`.`models`.`model` AS `model`
,`nordic_motorhomes`.`reservations`.`total` AS `total` 
FROM `nordic_motorhomes`.`reservations` JOIN `nordic_motorhomes`.`customers` ON `nordic_motorhomes`.`reservations`.`customer_id` = `nordic_motorhomes`.`customers`.`id` 
JOIN `nordic_motorhomes`.`motorhomes` ON `nordic_motorhomes`.`reservations`.`motorhome_id` = `nordic_motorhomes`.`motorhomes`.`id`  
JOIN `nordic_motorhomes`.`models` ON `nordic_motorhomes`.`motorhomes`.`model` = `nordic_motorhomes`.`models`.`id`;

CREATE OR REPLACE VIEW gearboxes AS
SELECT `nordic_motorhomes`.`motorhomes`.`gearbox` AS `gearbox` FROM `nordic_motorhomes`.`motorhomes` GROUP BY `nordic_motorhomes`.`motorhomes`.`gearbox`;

CREATE OR REPLACE VIEW manufacturers AS
SELECT `nordic_motorhomes`.`models`.`manufacturer` AS `manufacturer` FROM `nordic_motorhomes`.`models` GROUP BY `nordic_motorhomes`.`models`.`manufacturer` ORDER BY `nordic_motorhomes`.`models`.`manufacturer`;

CREATE OR REPLACE VIEW motorhome AS
SELECT `nordic_motorhomes`.`motorhomes`.`id` AS `id`,`nordic_motorhomes`.`models`.`manufacturer` AS `manufacturer`,
`nordic_motorhomes`.`models`.`model` AS `model`,`nordic_motorhomes`.`models`.`bed_count` AS `bed_count`,
`nordic_motorhomes`.`models`.`seats` AS `seats`,`nordic_motorhomes`.`models`.`weight` AS `weight`,
`nordic_motorhomes`.`motorhomes`.`gearbox` AS `gearbox`,`nordic_motorhomes`.`motorhomes`.`year` AS `year`,
`nordic_motorhomes`.`motorhomes`.`mileage` AS `mileage`,`nordic_motorhomes`.`motorhomes`.`transmission` AS `transmission`,
`nordic_motorhomes`.`models`.`id` AS `model_id`,`nordic_motorhomes`.`models`.`description` AS `description`,
`nordic_motorhomes`.`motorhomes`.`power` AS `power`,`nordic_motorhomes`.`motorhomes`.`price` AS `price` 
FROM `nordic_motorhomes`.`motorhomes` JOIN `nordic_motorhomes`.`models` ON `nordic_motorhomes`.`motorhomes`.`model` = `nordic_motorhomes`.`models`.`id`;

CREATE OR REPLACE VIEW transmissions AS
SELECT `nordic_motorhomes`.`motorhomes`.`transmission` AS `transmission`,count(0) AS `count` from `nordic_motorhomes`.`motorhomes` GROUP BY `nordic_motorhomes`.`motorhomes`.`transmission`;

