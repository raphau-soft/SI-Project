USE `employee_manager`;

CREATE TABLE IF NOT EXISTS `user`(
    `id` int NOT NULL AUTO_INCREMENT,
    `login` varchar(45) NOT NULL,
    `email` varchar(45) NOT NULL,
    `enabled` boolean default false,
    `password` varchar(120) NOT NULL,
    `role` varchar(45) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `confirmation_token` (
    `id` int NOT NULL AUTO_INCREMENT,
    `confirmation_token` varchar(45) NOT NULL,
    `created_date`  timestamp,
    `user_id` int NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE IF NOT EXISTS `building`(
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE IF NOT EXISTS `room`(
    `id` int NOT NULL AUTO_INCREMENT,
    `building_id` int NOT NULL,
    `number` int NOT NULL,
    `name` varchar(45) DEFAULT NULL,
    `capacity` int NOT NULL,
    `population` int NOT NULL,
    `width` float(45) NOT NULL,
    `height` float(45) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`building_id`) REFERENCES `building`(`id`)
);

CREATE TABLE IF NOT EXISTS `desk`(
    `id` int NOT NULL AUTO_INCREMENT,
    `width` float(45) DEFAULT NULL,
    `height` float(45) DEFAULT NULL,
    `taken` boolean DEFAULT false,
    `color` varchar(45) DEFAULT NULL,
    `rotation` float(45) DEFAULT NULL,
    `positionX` float(45) DEFAULT NULL,
    `positionY` float(45) DEFAULT NULL,
    `room_id` int NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`room_id`) REFERENCES `room`(`id`)
);

CREATE TABLE IF NOT EXISTS `position`(
    `id` int NOT NULL AUTO_INCREMENT,
    `building_id` int NOT NULL,
    `name` varchar(45) NOT NULL,
    `min_wage` float(45) NOT NULL,
    `max_wage` float(45) NOT NULL,
    `_usage` int NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`building_id`) REFERENCES `building`(`id`)
);

CREATE TABLE IF NOT EXISTS `employee`(
    `id` int NOT NULL AUTO_INCREMENT,
    `position_id` int,
    `building_id` int NOT NULL,
    `room_id` int,
    `desk_id` int,
    `first_name` varchar(45) NOT NULL,
    `last_name` varchar(45) NOT NULL,
    `salary` int NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`position_id`) REFERENCES `position`(`id`),
    FOREIGN KEY(`room_id`) REFERENCES `room`(`id`),
    FOREIGN KEY(`desk_id`) REFERENCES `desk`(`id`),
    FOREIGN KEY(`building_id`) REFERENCES `building`(`id`)
);
