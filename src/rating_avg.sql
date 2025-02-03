DROP DATABASE IF EXISTS BooksAPI;

DROP DATABASE IF EXISTS BOOKSAPI;
DROP DATABASE IF EXISTS booksapi;
CREATE DATABASE booksapi;
USE booksapi;

CREATE TABLE location (
						id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) UNIQUE NOT NULL
);

INSERT into location VALUES (1, 'Kansas City');
INSERT into location VALUES (2, 'Philadelphia');
INSERT into location VALUES (3, 'St. Louis');



CREATE TABLE user (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              username VARCHAR(255) UNIQUE NOT NULL,
                              location_id INT, -- '1': Kansas City '2': Philadelphia '3': St. Louis
                              email VARCHAR(255) NOT NULL,
                              pw_hash VARCHAR(255) NOT NULL,
                              verification_code VARCHAR (100) DEFAULT NULL,
                              is_verified BOOLEAN DEFAULT 0,
                              FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE volume (
                        id VARCHAR(255) PRIMARY KEY, -- Google booksAPI's unique string
                        author VARCHAR(255), -- Author associated with unique book.
                        title VARCHAR(255) NOT NULL, -- title associated with unique book
                        description TEXT, -- description from google unique string description of book
                        thumbnail TEXT, -- link to normal res book photo thumbnail
                        rating_avg FLOAT DEFAULT NULL
                        
);

CREATE TABLE bookshelf (  -- Each bookshelf is associated with a specific user, can each user can have more than one bookshelf
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           bookshelf_name VARCHAR(255) NOT NULL, -- what they name their specific bookshelf, "i.e. AJ's bookshelf favorites"
                           user_id INT,
                           FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE bookshelf_volume (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  bookshelf_id INT, -- the bookshelf ID that belongs to a specific user. This way each user could have more than one bookshelf/
                                  volume_id VARCHAR(255), -- this will be the abstracted book.
                                  has_book BOOLEAN DEFAULT TRUE, -- Allows us to be able to search whether the book is in thier possession
                                  unique_book VARCHAR(255) AS (CONCAT(bookshelf_id, '_', volume_id)),
                                  rating FLOAT DEFAULT NULL CHECK (rating BETWEEN 0.0 AND 5.0),
                                  pendingtransferto VARCHAR(255) DEFAULT '', -- Allows user to see that someone is requesting the book
                                  FOREIGN KEY (bookshelf_id) REFERENCES bookshelf(id), -- 
                                  FOREIGN KEY (volume_id) REFERENCES volume(id) -- 
);

DELIMITER $$

CREATE TRIGGER update_rating_avg_on_delete
AFTER DELETE ON bookshelf_volume
FOR EACH ROW
BEGIN
    UPDATE volume
    SET rating_avg = (
		SELECT ROUND(AVG(bookshelf_volume.rating), 1)
		FROM bookshelf_volume
		WHERE bookshelf_volume.volume_id = OLD.volume_id AND rating IS NOT NULL
	)
	WHERE volume.id = OLD.volume_id;
END $$

CREATE TRIGGER update_rating_avg_on_update
AFTER UPDATE ON bookshelf_volume
FOR EACH ROW
BEGIN
	UPDATE volume
	SET rating_avg = (
		SELECT ROUND(AVG(bookshelf_volume.rating), 1)
		FROM bookshelf_volume
		WHERE bookshelf_volume.volume_id = NEW.volume_id AND rating IS NOT NULL
	)
	WHERE volume.id = NEW.volume_id;
END $$

CREATE TRIGGER update_rating_avg_on_insert
AFTER INSERT ON bookshelf_volume
FOR EACH ROW
BEGIN
	UPDATE volume
	SET rating_avg = (
		SELECT ROUND(AVG(bookshelf_volume.rating), 1)
		FROM bookshelf_volume
		WHERE bookshelf_volume.volume_id = NEW.volume_id AND rating IS NOT NULL
	)
    WHERE volume.id = NEW.volume_id;
END $$

DELIMITER ;

/* Adding three books (two are kind of the same book)
userAccounts data, and a bookshelfVolume data */
INSERT INTO volume (id, author, title, description, thumbnail) VALUES
                                                                   ('3fOWbIrdRdIC', 'Jane Austen, Seth Grahame-Smith', 'Pride and Prejudice and Zombies: The Deluxe Heirloom Edition',
                                                                    'The deluxe heirloom edition of the "New York Times" bestseller boasts additional scenes of zombie mayhem, 13 new full-color illustrations, and an essay Afterword by Dr. Allen Grove, Professor of English Literature.',
                                                                    'http://books.google.com/books/content?id=3fOWbIrdRdIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

                                                                   ('g6C6P6NJQ1MC', 'Steve Hockensmith', 'Pride and Prejudice and Zombies: Dreadfully Ever After',
                                                                    'Complete with romance, heartbreak, martial arts, cannibalism, and an army of shambling corpses, Dreadfully Ever After brings the story of Pride and Prejudice and Zombies to a thrilling conclusion.',
                                                                    'http://books.google.com/books/content?id=g6C6P6NJQ1MC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

                                                                   ('KUMIEAAAQBAJ', 'Khaled Hosseini', 'The Kite Runner',
                                                                    'THE NUMBER ONE BESTSELLER',
                                                                    'http://books.google.com/books/content?id=KUMIEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

                                                                   ('kotPYEqx7kMC', 'George Orwell', '1984',
                                                                    '75th ANNIVERSARY EDITION',
                                                                    'http://books.google.com/books/content?id=kotPYEqx7kMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

                                                                   ('wrOQLV6xB-wC', 'J.K. Rowling', "Harry Potter and the Sorcerer's Stone",
                                                                    "Turning the envelope over...",
                                                                    'http://books.google.com/books/content?id=wrOQLV6xB-wC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

                                                                   ('PGR2AwAAQBAJ', 'Harper Lee', 'To Kill a Mockingbird',
                                                                    "Harper Lee's Pulitzer Prize-winning masterwork of honor and injustice in the deep South—and the heroism of one man in the face of blind and violent hatred.",
                                                                    'http://books.google.com/books/content?id=PGR2AwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'
                                                                   );
                                                                   
/* Adding userdata for myself and a made-up password hash from BCrypt
location is Kansas City
*/

INSERT INTO user (username, location_id, email, pw_hash, verification_code, is_verified) VALUES
    ('mattet', 1, 'mattetracy@outlook.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S', '14321', 1); 
INSERT INTO bookshelf (bookshelf_name, user_id) VALUES
    ("Matt's Good Reads", 1);
INSERT INTO bookshelf_volume (bookshelf_id, volume_id, has_book, rating) VALUES
    (1, '3fOWbIrdRdIC', TRUE, 4),
    (1, 'g6C6P6NJQ1MC', TRUE, 3),
    (1, 'KUMIEAAAQBAJ', TRUE, 4),
    (1, 'kotPYEqx7kMC', TRUE, 4),
    (1, 'PGR2AwAAQBAJ', TRUE, 4),
    (1, 'wrOQLV6xB-wC', TRUE, 3);

/* Adding userdata for random person and a made-up password hash from BCrypt
location is St. Louis
*/

INSERT INTO user (username, location_id, email, pw_hash, verification_code, is_verified) VALUES
    ('bob2342', 2, 'bob2342@gmail.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S', '14321', 1); 
INSERT INTO bookshelf (bookshelf_name, user_id) VALUES
    ("Bob's Good Reads", 2);
INSERT INTO bookshelf_volume (bookshelf_id, volume_id, has_book, rating) VALUES
	(1, '3fOWbIrdRdIC', TRUE, 2),
    (1, 'g6C6P6NJQ1MC', TRUE, 2),
    (1, 'KUMIEAAAQBAJ', TRUE, 2),
    (1, 'kotPYEqx7kMC', TRUE, 2),
    (1, 'PGR2AwAAQBAJ', TRUE, 2),
    (1, 'wrOQLV6xB-wC', TRUE, 1);

/* Adding userdata for random person and a made-up password hash from BCrypt
location is St. Louis
*/
INSERT INTO user (username, location_id, email, pw_hash, verification_code, is_verified) VALUES
    ('alice4242', 3, 'alice4242@gmail.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S', '14321', 1); 
INSERT INTO bookshelf (bookshelf_name, user_id) VALUES
    ("Alice's Good Reads", 3);
INSERT INTO bookshelf_volume (bookshelf_id, volume_id, has_book, rating) VALUES
    (3, '3fOWbIrdRdIC', TRUE, 1),
    (3, 'g6C6P6NJQ1MC', TRUE, 1),
    (3, 'KUMIEAAAQBAJ', TRUE, 1),
    (3, 'kotPYEqx7kMC', TRUE, 1),
    (3, 'PGR2AwAAQBAJ', TRUE, 1),
    (3, 'wrOQLV6xB-wC', TRUE, 1);

/* Adding userdata for another random person and a made-up password hash from BCrypt
location is St. Louis
*/
INSERT INTO user (username, location_id, email, pw_hash, verification_code, is_verified) VALUES
    ('doyle', 2, 'doyle@launchcode.org', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S', '14321', 1);
INSERT INTO bookshelf (bookshelf_name, user_id) VALUES
    ("Doyle's Good Reads", 4);
INSERT INTO bookshelf_volume (bookshelf_id, volume_id, has_book, rating) VALUES
    (4, '3fOWbIrdRdIC', TRUE, 3),
    (4, 'g6C6P6NJQ1MC', TRUE, 4),
    (4, 'KUMIEAAAQBAJ', TRUE, 4),
    (4, 'kotPYEqx7kMC', TRUE, 3),
    (4, 'PGR2AwAAQBAJ', TRUE, 4),
    (4, 'wrOQLV6xB-wC', TRUE, 4);

INSERT INTO user (username, location_id, email, pw_hash, verification_code, is_verified) VALUES
    ('d2oyle232', 3, 'doyle@launchcode.org', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S', '14321', 1);
INSERT INTO bookshelf (bookshelf_name, user_id) VALUES
    ("Doyle's Good Reads", 5);
INSERT INTO bookshelf_volume (bookshelf_id, volume_id, has_book, rating) VALUES
    (4, '3fOWbIrdRdIC', TRUE, 1),
    (4, 'g6C6P6NJQ1MC', TRUE, 1),
    (4, 'KUMIEAAAQBAJ', TRUE, 1),
    (4, 'kotPYEqx7kMC', TRUE, 1),
    (4, 'PGR2AwAAQBAJ', TRUE, 1),
    (4, 'wrOQLV6xB-wC', TRUE, 1);
/* Adding userdata for another random person and a made-up password hash from BCrypt
location is St. Louis
*/
INSERT INTO user (username, location_id, email, pw_hash, verification_code, is_verified) VALUES
    ('emily', 3, 'emilyu@launchcode.org', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S', '14321', 1); 
INSERT INTO bookshelf (bookshelf_name, user_id) VALUES
    ("Emily's Good Reads", 4);
INSERT INTO bookshelf_volume (bookshelf_id, volume_id, has_book, rating) VALUES
    (5, '3fOWbIrdRdIC', TRUE, 5),
    (5, 'g6C6P6NJQ1MC', TRUE, 5),
    (5, 'KUMIEAAAQBAJ', TRUE, 5),
    (5, 'kotPYEqx7kMC', TRUE, 5),
    (5, 'PGR2AwAAQBAJ', TRUE, 5),
    (5, 'wrOQLV6xB-wC', TRUE, 5);

-- tag some books for functional testing
-- INSERT INTO bookshelf_volume_tags (bookshelf_volumes_id, tags_id) VALUES
-- (1, 1),
-- (1, 3),
-- (3, 2),
-- (3, 3),
-- (4, 3),
-- (5, 2),
-- (5, 3);

-- this query will give us the information for all user_id, and all the book data we need across all of every users bookshelves (all their bookshelfs)
SELECT
    user.id,
    bookshelf.user_id,
    user.username AS username,
    user.email AS email,
    user.location_id AS location,
    
    bookshelf.id AS bookshelf_id,
    bookshelf.bookshelf_name AS bookshelf_name,
    bookshelf_volume.volume_id AS volume_id,
    bookshelf_volume.has_book AS has_book,
    bookshelf_volume.unique_book,
    bookshelf_volume.rating,
    volume.author AS author,
    volume.title AS title,
    volume.description AS description,
    volume.thumbnail AS thumbnail

FROM 
	user
        JOIN
    bookshelf ON user.id = bookshelf.user_id -- fixed output result of SQL query, needed conditional
        JOIN
    bookshelf_volume ON bookshelf.id = bookshelf_volume.bookshelf_id -- fixed output result of SQL query, needed conditional
        JOIN
    volume ON bookshelf_volume.volume_id = volume.id
WHERE
    bookshelf_volume.has_book = TRUE
ORDER BY
    user.location_id, user.username, volume.title;

SELECT * FROM VOLUME;