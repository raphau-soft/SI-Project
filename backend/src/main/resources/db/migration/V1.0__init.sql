USE `employee_manager`;

CREATE TABLE IF NOT EXISTS `room`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `number` int(11) NOT NULL,
    `name` varchar(45) DEFAULT NULL,
    `capacity` int(11) NOT NULL,
    `population` int(11) NOT NULL,
    `width` float(45) NOT NULL,
    `height` float(45) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `desk`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `width` float(45) DEFAULT NULL,
    `height` float(45) DEFAULT NULL,
    `taken` boolean DEFAULT false,
    `color` varchar(45) DEFAULT NULL,
    `rotation` float(45) DEFAULT NULL,
    `positionX` float(45) DEFAULT NULL,
    `positionY` float(45) DEFAULT NULL,
    `room_id` int(11) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`room_id`) REFERENCES `room`(`id`)
);

CREATE TABLE IF NOT EXISTS `position`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `min_wage` float(45) NOT NULL,
    `max_wage` float(45) NOT NULL,
    `_usage` int(11) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `employee`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `position_id` int(11) NOT NULL,
    `room_id` int(11) NOT NULL,
    `desk_id` int(11) NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name` varchar(45) NOT NULL,
    `salary` int(45) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`position_id`) REFERENCES `position`(`id`),
    FOREIGN KEY(`room_id`) REFERENCES `room`(`id`),
    FOREIGN KEY(`desk_id`) REFERENCES `desk`(`id`)
);
