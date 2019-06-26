-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema online_auction_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `tiny_auction` ;

-- -----------------------------------------------------
-- Schema online_auction_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tiny_auction` DEFAULT CHARACTER SET utf8 ;
USE `tiny_auction` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `user`(username, first_name, last_name, password, email) 
	VALUE ('batman','Bruce','Wayne','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','batman@gotham.com');
-- -----------------------------------------------------
-- Table `image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `image` ;

CREATE TABLE IF NOT EXISTS `image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `file_path` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `parent_category_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_category1_idx` (`parent_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`parent_category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
  )
ENGINE = InnoDB;

INSERT INTO `category` (name, parent_category_id) VALUES ('All Categories', NULL),
	('Electronics', 1), ('Fashion', 1), ('Health and Beauty', 1), ('Home and Garden', 1), ('Sporting Goods', 1),
    ('Collectibles and Art', 1), ('Music and Books', 1),('Toys', 1),
    
    ('Cell Phones, Smart Watches & Accessories', 2), ('Smart Watches', 2),('Video Games & Consoles', 2),('Computers, Tablets & Network Hardware', 2),
    ('Cameras & Photo', 2), ('Portable Audio & Headphones', 2),('TV, Video & Home Audio Electronics', 2),('Vehicle Electronics & GPS', 2),
    ('Women\'s Clothing', 3), ('Women\'s Shoes', 3),('Men\'s Clothing', 3),('Men\'s Shoes', 3),
    ('Watches, Parts & Accessories', 3), ('Fine Jewelry', 3),('Jewelry', 3),('Men\'s Accessories', 3),
    ('Women\'s Bags & Handbags', 3), ('Kids\' Clothing, Shoes & Accessories', 3),('Makeup Products', 4),('Health', 4),
    ('Fragrances', 4), ('Manicure, Pedicure & Nail Care Products', 4),('Hair Care & Styling Products', 4),('Skin Care Products', 4),
    ('Vitamins & Dietary Supplements', 4), ('Shaving & Hair Removal Products', 4),('Vision Care Products', 4),('Bath & Body Products', 4),
    ('Tools & Workshop Equipment', 5), ('Yard, Garden & Outdoor Living Items', 5),('Home Improvement', 5),('Kitchen, Dining & Bar Supplies', 5),
    ('Lamps, Lighting & Ceiling Fans', 5), ('Home Décor', 5),('Home Organization Supplies', 5),('Beads & Jewelry Making Supplies', 5),
    ('Scrapbooking & Paper Craft Supplies', 5), ('Pet Supplies', 5),('Cycling Equipment', 6),('Outdoor Sports', 6),
    ('Hunting Equipment', 6), ('Fishing Equipment & Supplies', 6),('Fitness, Running & Yoga Equipmen', 6),('Tennis & Racquet Sports', 6),
    ('Water Sports', 6), ('Winter Sports', 6),('Fitness Technology', 6),('Team Sports', 6),
    ('Action Figures', 7), ('Coins & Paper Money', 7),('Stamps', 7),('Collectible Postcards', 7),
    ('Entertainment Memorabilia', 7), ('Antiques', 7),('Diecast & Toy Vehicles', 7),('Collectible Comics', 7),
    ('Paintings', 7), ('Sculptures', 7),('Posters', 7),('Photographs', 7),
    ('Musical Instuments & Gear', 8), ('Music', 8),('Books', 8);
-- -----------------------------------------------------
-- Table `currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency` ;

CREATE TABLE IF NOT EXISTS `currency` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `currency` (name) VALUES ('USD'),('EUR'),('NIS');
-- -----------------------------------------------------
-- Table `bid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bid` ;

CREATE TABLE IF NOT EXISTS `bid` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` FLOAT NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `bidder_id` INT NOT NULL,
  `listing_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bid_user1_idx` (`bidder_id` ASC) VISIBLE,
  INDEX `fk_bid_listing1_idx` (`listing_id` ASC) VISIBLE,
  CONSTRAINT `fk_bid_user1`
    FOREIGN KEY (`bidder_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bid_listing1`
    FOREIGN KEY (`listing_id`)
    REFERENCES `listing` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `status` ;

CREATE TABLE IF NOT EXISTS `status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `status`(name) VALUES ('ACTIVE'),('CANCELED'),('CLOSED');
-- -----------------------------------------------------
-- Table `auction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `listing` ;

CREATE TABLE IF NOT EXISTS `listing` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `start_date` TIMESTAMP NOT NULL,
  `end_date` TIMESTAMP NOT NULL,
  `starting_price` FLOAT NOT NULL,
  `number_of_bids` INT NOT NULL,
  `description` TEXT NOT NULL,
  `seller_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `currency_id` INT NOT NULL,
  `winning_bid_id` INT NULL,
  `image_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_listing_user1_idx` (`seller_id` ASC) VISIBLE,
  INDEX `fk_listing_currency1_idx` (`currency_id` ASC) VISIBLE,
  INDEX `fk_listing_bid1_idx` (`winning_bid_id` ASC) VISIBLE,
  INDEX `fk_listing_image1_idx` (`image_id` ASC) VISIBLE,
  INDEX `fk_listing_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_listing_category1_idx`(`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_listing_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_listing_user1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_listing_currency1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `currency` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_listing_bid1`
    FOREIGN KEY (`winning_bid_id`)
    REFERENCES `bid` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_listing_image1`
    FOREIGN KEY (`image_id`)
    REFERENCES `image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_listing_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `role`(name) VALUES ('PERSONAL'), ('BUSINESS'), ('ADMIN');
-- -----------------------------------------------------
-- Table `users_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users_roles` ;

CREATE TABLE IF NOT EXISTS `users_roles` (
  `role_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`role_id`, `user_id`),
  INDEX `fk_users_roles_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_roles_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_roles_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `users_roles`(role_id, user_id) VALUE (1,1); 

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
