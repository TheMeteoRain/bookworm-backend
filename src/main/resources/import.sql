SET foreign_key_checks = 0;

DROP TABLE IF EXISTS publisher;
CREATE TABLE publisher (`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,`name` VARCHAR(150) NOT NULL,`country` VARCHAR(255),`city` VARCHAR(255)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS author;
CREATE TABLE author (`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,`first_name` VARCHAR(100) NOT NULL,`last_name` VARCHAR(100) NOT NULL) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS book;
CREATE TABLE book (`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,`publisher_id` INT UNSIGNED,`title` VARCHAR(255) NOT NULL,`description` TEXT,`genre` VARCHAR(150) NOT NULL,`format` VARCHAR(150)  NOT NULL,`price` DECIMAL(5,2) NOT NULL,`stock` SMALLINT NOT NULL,`pages` SMALLINT UNSIGNED NOT NULL,`isbn` VARCHAR(15) NOT NULL,CONSTRAINT `fk_book_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher`(`id`) ON UPDATE CASCADE ON DELETE SET NULL) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS book_author;
CREATE TABLE book_author (`author_id` INT UNSIGNED,`book_id` INT UNSIGNED,PRIMARY KEY (`author_id`, `book_id`),CONSTRAINT `fk_bookauthor_author` FOREIGN KEY (`author_id`) REFERENCES `author`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,CONSTRAINT `fk_bookauthor_book` FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON UPDATE CASCADE ON DELETE CASCADE) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS member;
CREATE TABLE member (`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,`username` VARCHAR(100) NOT NULL,`email` VARCHAR(150) NOT NULL,`password` VARCHAR(255) NOT NULL,UNIQUE KEY `username` (`username`),UNIQUE KEY `email` (`email`)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS review;
CREATE TABLE review (`member_id` INT UNSIGNED,`book_id` INT UNSIGNED,`text` TEXT,`stars` DECIMAL(3,2) NOT NULL,PRIMARY KEY (`member_id`, `book_id`),CONSTRAINT `fk_review_member` FOREIGN KEY (`member_id`) REFERENCES `member`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,CONSTRAINT `fk_review_book` FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON UPDATE CASCADE ON DELETE CASCADE) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS purchase;
CREATE TABLE purchase (`id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,`member_id` INT UNSIGNED NOT NULL,`book_id` INT UNSIGNED NOT NULL,`amount` TINYINT UNSIGNED NOT NULL,CONSTRAINT `fk_purchase_member` FOREIGN KEY (`member_id`) REFERENCES `member`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,CONSTRAINT `fk_purchase_book` FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON UPDATE CASCADE ON DELETE CASCADE) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS notification;
CREATE TABLE notification (`book_id` INT UNSIGNED,`member_id` INT UNSIGNED,PRIMARY KEY (`member_id`, `book_id`),CONSTRAINT `fk_notification_member` FOREIGN KEY (`member_id`) REFERENCES `member`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,CONSTRAINT `fk_notification_book` FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON UPDATE CASCADE ON DELETE CASCADE) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS genre;
CREATE TABLE genre (`name` VARCHAR(150) PRIMARY KEY) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE INDEX `index_book_title` ON `book` (`title`);


INSERT INTO genre (name) VALUES ('C');
INSERT INTO genre (name) VALUES ('CSS');
INSERT INTO genre (name) VALUES ('Java');
INSERT INTO genre (name) VALUES ('Python');
INSERT INTO genre (name) VALUES ('JavaScript');


# INSERT INTO member (username, email, password) VALUES ('admin', 'admin@admin.fi', 'admin');
INSERT INTO member (username, email, password) VALUES ('jeppe', 'jeppe@jeppe.fi', 'jeppe');
INSERT INTO member (username, email, password) VALUES ('jussi', 'jussi@jussi.fi', 'jussi');


INSERT INTO publisher (name, country, city) VALUES ('McGraw-Hill Education', 'United States', 'New York City');
INSERT INTO publisher (name, country, city) VALUES ('O''Reilly Media', 'United States', 'Sebastopol, California');
INSERT INTO publisher (name, country, city) VALUES ('Triangle Connection LLC', '', '');
INSERT INTO publisher (name, country, city) VALUES ('John Wiley & Sons', 'United States', 'New York City');
INSERT INTO publisher (name, country, city) VALUES ('Prentice Hall', 'United States' ,'New Jersey');
INSERT INTO publisher (name, country, city) VALUES ('Addison-Wesley', 'United States' ,'Boston');

INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (3, 'The Self-Taught Programmer: The Definitive Guide to Programming Professionally', 'I am a self-taught programmer. After a year of self-study, I learned to program well enough to land a job as a software engineer II at eBay. Once I got there, I realized I was severely under-prepared. I was overwhelmed by the amount of things I needed to know but hadn''t learned yet. My journey learning to program, and my experience at my first job as a software engineer were the inspiration for this book. This book is not just about learning to program; although you will learn to code. If you want to program professionally, it is not enough to learn to code; that is why, in addition to helping you learn to program, I also cover the rest of the things you need to know to program professionally that classes and books don''t teach you. ''The Self-taught Programmer'' is a roadmap, a guide to take you from writing your first Python program, to passing your first technical interview. I divided the book into six sections: 1. Learn to program in Python 3 and build your first program. 2. Learn Object-oriented programming and create a powerful Python program to get you hooked. 3. Learn to use tools like Git, Bash, regular expressions and databases. Then use your new coding skills to build a web scraper. 4. Study Computer Science fundamentals including computer architecture, data structures, algorithms and network programming. 5. Learn to program for production: I cover the software development process, testing, and best coding practices. 6. Finish with tips for working with a team and landing a programming job. You CAN learn to program professionally. The path is there. Will you take it?', 'Python', 'Paperback', 24.35, 15, 299, '978-1520288178');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (4, 'HTML and CSS: Design and Build Websites', 'A full-color introduction to the basics of HTML and CSS from the publishers of Wrox! Every day, more and more people want to learn some HTML and CSS. Joining the professional web designers and programmers are new audiences who need to know a little bit of code at work (update a content management system or e-commerce store) and those who want to make their personal blogs more attractive. Many books teaching HTML and CSS are dry and only written for those who want to become programmers, which is why this book takes an entirely new approach. Introduces HTML and CSS in a way that makes them accessible to everyone—hobbyists, students, and professionals—and it’s full-color throughout. Utilizes information graphics and lifestyle photography to explain the topics in a simple way that is engaging. Boasts a unique structure that allows you to progress through the chapters from beginning to end or just dip into topics of particular interest at your leisure. This educational book is one that you will enjoy picking up, reading, then referring back to. It will make you wish other technical topics were presented in such a simple, attractive and engaging way!', 'CSS', 'Hardcover', 17.19, 20, 490, '978-1118008188');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (2, 'Head First Java','Learning a complex new language is no easy task especially when it s an object-oriented computer programming language like Java. You might think the problem is your brain. It seems to have a mind of its own, a mind that doesn''t always want to take in the dry, technical stuff you''re forced to study. The fact is your brain craves novelty. It''s constantly searching, scanning, waiting for something unusual to happen. After all, that''s the way it was built to help you stay alive. It takes all the routine, ordinary, dull stuff and filters it to the background so it won''t interfere with your brain''s real work--recording things that matter. How does your brain know what matters? It''s like the creators of the Head First approach say, suppose you''re out for a hike and a tiger jumps in front of you, what happens in your brain? Neurons fire. Emotions crank up. Chemicals surge. That''s how your brain knows. And that''s how your brain will learn Java. Head First Java combines puzzles, strong visuals, mysteries, and soul-searching interviews with famous Java objects to engage you in many different ways. It''s fast, it''s fun, and it''s effective. And, despite its playful appearance, Head First Java is serious stuff: a complete introduction to object-oriented programming and Java. You''ll learn everything from the fundamentals to advanced topics, including threads, network sockets, and distributed programming with RMI. And the new. second edition focuses on Java 5.0, the latest version of the Java language and development platform. Because Java 5.0 is a major update to the platform, with deep, code-level changes, even more careful study and implementation is required. So learning the Head First way is more important than ever. If you''ve read a Head First book, you know what to expect--a visually rich format designed for the way your brain works. If you haven''t, you''re in for a treat. You''ll see why people say it''s unlike any other Java book you''ve ever read. By exploiting how your brain works, Head First Java compresses the time it takes to learn and retain--complex information. Its unique approach not only shows you what you need to know about Java syntax, it teaches you to think like a Java programmer. If you want to be bored, buy some other book. But if you want to understand Java, this book''s for you.', 'Java', 'Paperback', 30.59, 8, 688, '978-0596009205');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (5, 'The C Programming Language','The authors present the complete guide to ANSI standard C language programming. Written by the developers of C, this new version helps readers keep up with the finalized ANSI standard for C while showing how to take advantage of C''s rich set of operators, economy of expression, improved control flow, and data structures. The 2/E has been completely rewritten with additional examples and problem sets to clarify the implementation of difficult language constructs. For years, C programmers have let K&R guide them to building well-structured and efficient programs. Now this same help is available to those working with ANSI compilers. Includes detailed coverage of the C language plus the official C language reference manual for at-a-glance help with syntax notation, declarations, ANSI changes, scope rules, and the list goes on and on.', 'C', 'Hardcover', 17.19, 20, 272, '978-0131103627');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (1, 'Java: A Beginner''s Guide','Fully updated for Java Platform, Standard Edition 8 (Java SE 8), Java: A Beginner''s Guide, Sixth Edition gets you started programming in Java right away. Bestselling programming author Herb Schildt begins with the basics, such as how to create, compile, and run a Java program. He then moves on to the keywords, syntax, and constructs that form the core of the Java language. This Oracle Press resource also covers some of Java''s more advanced features, including multithreaded programming, generics, and Swing. Of course, new Java SE 8 features such as lambda expressions and default interface methods are described. An introduction to JavaFX, Java''s newest GUI, concludes this step-by-step tutorial.', 'Java', 'Hardcover', 20.89, 5, 728, '978-0071809252');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (2, 'Learning Spark: Lightning-Fast Big Data Analysis', 'Data in all domains is getting bigger. How can you work with it efficiently? Recently updated for Spark 1.3, this book introduces Apache Spark, the open source cluster computing system that makes data analytics fast to write and fast to run. With Spark, you can tackle big datasets quickly through simple APIs in Python, Java, and Scala. This edition includes new information on Spark SQL, Spark Streaming, setup, and Maven coordinates. Written by the developers of Spark, this book will have data scientists and engineers up and running in no time. You’ll learn how to express parallel jobs with just a few lines of code, and cover applications from simple batch jobs to stream processing and machine learning.', 'Java', 'Paperback', 31.61, 16, 276, '978-1449358624');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (6, 'Effective Java', 'Are you looking for a deeper understanding of the Java programming language so that you can write code that is clearer, more correct, more robust, and more reusable? Look no further! Effective Java™, Second Edition, brings together seventy-eight indispensable programmer’s rules of thumb: working, best-practice solutions for the programming challenges you encounter every day. This highly anticipated new edition of the classic, Jolt Award-winning work has been thoroughly updated to cover Java SE 5 and Java SE 6 features introduced since the first edition. Bloch explores new design patterns and language idioms, showing you how to make the most of features ranging from generics to enums, annotations to autoboxing. Each chapter in the book consists of several “items” presented in the form of a short, standalone essay that provides specific advice, insight into Java platform subtleties, and outstanding code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.', 'Java', 'Paperback', 47.58, 4, 346, '978-0321356680');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (6, 'The C++ Programming Language', 'C++11 has arrived: thoroughly master it, with the definitive new guide from C++ creator Bjarne Stroustrup, C++ Programming Language, Fourth Edition! The brand-new edition of the world''s most trusted and widely read guide to C++, it has been comprehensively updated for the long-awaited C++11 standard. Extensively rewritten to present the C++11 language, standard library, and key design techniques as an integrated whole, Stroustrup thoroughly addresses changes that make C++11 feel like a whole new language, offering definitive guidance for leveraging its improvements in performance, reliability, and clarity. C++ programmers around the world recognize Bjarne Stoustrup as the go-to expert for the absolutely authoritative and exceptionally useful information they need to write outstanding C++ programs.', 'Java', 'Paperback', 61.56, 2, 1368, '978-0321563842');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (6, 'Programming: Principles and Practice Using C++', 'Preparation for Programming in the Real World. The book assumes that you aim eventually to write non-trivial programs, whether for work in software development or in some other technical field. Focus on Fundamental Concepts and Techniques: The book explains fundamental concepts and techniques in greater depth than traditional introductions. This approach will give you a solid foundation for writing useful, correct, maintainable, and efficient code. Programming with Today’s C++ (C++11 and C++14): The book is an introduction to programming in general, including object-oriented programming and generic programming. It is also a solid introduction to the C++ programming language, one of the most widely used languages for real-world software. The book presents modern C++ programming techniques from the start, introducing the C++ standard library and C++11 and C++14 features to simplify programming tasks. For Beginners—And Anyone Who Wants to Learn Something New: The book is primarily designed for people who have never programmed before, and it has been tested with many thousands of first-year university students. It has also been extensively used for self-study. Also, practitioners and advanced students have gained new insight and guidance by seeing how a master approaches the elements of his art. Provides a Broad View: The first half of the book covers a wide range of essential concepts, design and programming techniques, language features, and libraries. Those will enable you to write programs involving input, output, computation, and simple graphics. The second half explores more specialized topics (such as text processing, testing, and the C programming language) and provides abundant reference material. Source code and support supplements are available from the author’s website.', 'Java', 'Hardcover', 47.85, 0, 1312, '978-0321992789');
INSERT INTO book (publisher_id, title, description, genre, format, price, stock, pages, isbn) VALUES (2, 'Learning Python', 'Get a comprehensive, in-depth introduction to the core Python language with this hands-on book. Based on author Mark Lutz’s popular training course, this updated fifth edition will help you quickly write efficient, high-quality code with Python. It’s an ideal way to begin, whether you’re new to programming or a professional developer versed in other languages. Complete with quizzes, exercises, and helpful illustrations, this easy-to-follow, self-paced tutorial gets you started with both Python 2.7 and 3.3— the latest releases in the 3.X and 2.X lines—plus all other releases in common use today. You’ll also learn some advanced language features that recently have become more common in Python code.', 'Python', 'Paperback', 48.53, 0, 1648, '978-1449355739');

INSERT INTO author (first_name, last_name) VALUES ('Cory', 'Althoff');
INSERT INTO author (first_name, last_name) VALUES ('Jon', 'Duckkett');
INSERT INTO author (first_name, last_name) VALUES ('Brian', 'Kerninghan');
INSERT INTO author (first_name, last_name) VALUES ('Kathy', 'Sierra');
INSERT INTO author (first_name, last_name) VALUES ('Herbert', 'Schildt');
INSERT INTO author (first_name, last_name) VALUES ('Holden', 'Karau');
INSERT INTO author (first_name, last_name) VALUES ('Andy', 'Konwinski');
INSERT INTO author (first_name, last_name) VALUES ('Patrick', 'Wendell');
INSERT INTO author (first_name, last_name) VALUES ('Matei', 'Zaharia');
INSERT INTO author (first_name, last_name) VALUES ('Joshua', 'Bloch');
INSERT INTO author (first_name, last_name) VALUES ('Bjarne', 'Stroustrup');
INSERT INTO author (first_name, last_name) VALUES ('Mark', 'Lutz');

INSERT INTO book_author (author_id, book_id) VALUES (1,1);
INSERT INTO book_author (author_id, book_id) VALUES (2,2);
INSERT INTO book_author (author_id, book_id) VALUES (3,4);
INSERT INTO book_author (author_id, book_id) VALUES (4,3);
INSERT INTO book_author (author_id, book_id) VALUES (5,5);
INSERT INTO book_author (author_id, book_id) VALUES (6,6);
INSERT INTO book_author (author_id, book_id) VALUES (7,6);
INSERT INTO book_author (author_id, book_id) VALUES (8,6);
INSERT INTO book_author (author_id, book_id) VALUES (9,6);
INSERT INTO book_author (author_id, book_id) VALUES (10,7);
INSERT INTO book_author (author_id, book_id) VALUES (11,8);
INSERT INTO book_author (author_id, book_id) VALUES (11,9);
INSERT INTO book_author (author_id, book_id) VALUES (12,10);


SET foreign_key_checks = 1;
