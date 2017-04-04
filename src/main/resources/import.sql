SET foreign_key_checks = 0;



DROP TABLE IF EXISTS publisher;
CREATE TABLE publisher (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,`name` VARCHAR(255) NOT NULL);


DROP TABLE IF EXISTS author;
CREATE TABLE author (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,`first_name` VARCHAR(100) NOT NULL,`last_name` VARCHAR(100) NOT NULL);


DROP TABLE IF EXISTS book;
CREATE TABLE book (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,`author_id` INT UNSIGNED NOT NULL,`publisher_id` INT UNSIGNED NOT NULL,`title` VARCHAR(255) NOT NULL,`description` TEXT NOT NULL,`genre` VARCHAR(255) NOT NULL,`format` VARCHAR(255)  NOT NULL,`lang` VARCHAR(255) NOT NULL,`stock` SMALLINT NOT NULL,`pages` SMALLINT UNSIGNED NOT NULL,`rating` DECIMAL(3,2) NOT NULL,`isbn` VARCHAR(13) NOT NULL,CONSTRAINT `fk_book_author` FOREIGN KEY (`publisher_id`) REFERENCES `publisher`(`id`) ON UPDATE CASCADE ON DELETE SET NULL,CONSTRAINT `fk_book_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher`(`id`) ON UPDATE CASCADE ON DELETE SET NULL);


DROP TABLE IF EXISTS member;
CREATE TABLE member (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,`username` VARCHAR(100) NOT NULL,`password` VARCHAR(255) NOT NULL,`auth` TINYINT NOT NULL DEFAULT 2);


insert into publisher (id, name) values (1, 'Geba');
insert into publisher (id, name) values (2, 'Layo');
insert into publisher (id, name) values (3, 'Jaxbean');


insert into author (id, first_name, last_name) values (1, 'Anthony', 'Romero');
insert into author (id, first_name, last_name) values (2, 'Kelly', 'James');
insert into author (id, first_name, last_name) values (3, 'Judith', 'Bowman');


insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (3, 2, 'Human Resources Manager', 'Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo. Pellentesque ultrices mattis odio.', 'Sarjakuvat', 'Nidottu', 'English', 255, 10, 5.05, '096106245-2');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (2, 2, 'Business Systems Development Analyst', 'Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus.', 'Maantiede', 'Nidottu', 'English', 300, 15, 1, '892265104-0');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (1, 1, 'GIS Technical Architect', 'Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 'Sarjakuvat', 'Nidottu', 'Finnish', 300, 14, 1.5, '925385856-7');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (1, 2, 'Administrative Officer', 'Morbi porttitor lorem id ligula.', 'Maantiede', 'Nidottu', 'Finnish', 300, 8, 2.5, '462938921-0');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (1, 3, 'Account Executive', 'In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.', 'Dekkarit', 'Nidottu', 'Finnish', 229, 15, 2.5, '208587674-9');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (2, 1, 'Programmer Analyst I', 'Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat.', 'Maantiede', 'Nidottu', 'English', 300, 8, 2.5, '195864032-8');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (2, 1, 'Chief Design Engineer', 'Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus.', 'Dekkarit', 'Kovakantinen', 'English', 229, 8, 5, '985358682-7');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (2, 2, 'Web Designer II', 'Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 'Kielet', 'Kovakantinen', 'English', 255, 14, 0.5, '890626834-3');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (2, 2, 'Staff Accountant I', 'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend.', 'Sarjakuvat', 'Nidottu', 'Finnish', 350, 8, 4.5, '727478107-3');
insert into book (author_id, publisher_id, title, description, genre, format, lang, pages, stock, rating, isbn) values (2, 1, 'Technical Writer', 'Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', 'Sarjakuvat', 'Nidottu', 'English', 185, 8, 3.5, '033951610-0');


insert into member (username, password, auth) values ('admin', 'admin', 1);
insert into member (username, password) values ('jeppe', 'jeppe');
insert into member (username, password) values ('jussi', 'jussi');
insert into member (username, password) values ('Heather', 'X4xPzgsoZPE');
insert into member (username, password) values ('Ralph', 'H3AYrdpV');



SET foreign_key_checks = 1;