DROP DATABASE IF EXISTS BooksAPI;
DROP DATABASE IF EXISTS BOOKSAPI;
CREATE DATABASE BOOKSAPI;

USE BOOKSAPI;

CREATE TABLE UserAccounts (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              username VARCHAR(100) UNIQUE NOT NULL,
                              location VARCHAR(2), -- 00: Kansas City 01: Philadelphia 02: St. Louis
                              email VARCHAR(100) UNIQUE NOT NULL,
                              password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE Volumes (
                         id VARCHAR(50) PRIMARY KEY, -- Google booksAPI's unique string
                         author VARCHAR(50), -- Author associated with unique book.
                         title VARCHAR(255) NOT NULL, -- title associated with unique book
                         description TEXT, -- description from google unique string description of book
                         thumbnail TEXT -- link to normal res book photo thumbnail
);

CREATE TABLE Bookshelves (  -- Each bookshelf is associated with a specific user, can each user can have more than one bookshelf
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             bookshelf_name VARCHAR(255) NOT NULL, -- what they name their specific bookshelf, "i.e. AJ's bookshelf favorites"
                             user_id INT,
                             FOREIGN KEY (user_id) REFERENCES UserAccounts(id)
);

CREATE TABLE BookshelfVolumes (
                                  bookshelf_id INT, -- the bookshelf ID that belongs to a specific user. This way each user could have more than one bookshelf/
                                  volume_id VARCHAR(50), -- this will be the abstracted book.
                                  has_book BOOLEAN, -- TRUE means that person has the book currently, FALSE means person does not have the book on their current bookshelf.
                                  unique_book VARCHAR(255) AS (CONCAT(bookshelf_id, '_', volume_id)),
                                  -- book_location VARCHAR(2),
                                  -- volume_author VARCHAR(50),
                                  -- volume_title VARCHAR(255) NOT NULL,
                                  PRIMARY KEY (bookshelf_id, volume_id),
                                  FOREIGN KEY (bookshelf_id) REFERENCES Bookshelves(id), -- referencing has to be the primary key
                                  FOREIGN KEY (volume_id) REFERENCES Volumes(id) -- referencing has to be the primary key
                                  -- scratch my thinking below.  I am still trying to understand how referencing is working in relation to our tables.
                                  -- we will I think have to.
                                  -- for the sake of clarity I left the bottom three FOREIGN KEY references in (we only need volume_id to reference table).
                                  -- Technically the below volume_author, volume_title, and book_location are superfluous, we can just pull the data directly with a SQL statement since we have volume_id to run a sql query.
                                  -- Our table will be unnecessarily large and duplicated data in both tables if we do this.
                                                                                                                -- Later on we can just to a series of JOINs to get the table we want without duplicating data
                                                                                                                -- FOREIGN KEY (volume_author) REFERENCES Volumes(author),
                                  -- FOREIGN KEY (volume_title) REFERENCES Volumes(title),
);
-- 12/20/2024 Let me know what everyone thinks of this, this is able to work in mysql workbench


/* Adding three books (two are kind of the same book)
userAccounts data, and a bookshelfVolume data */
INSERT INTO Volumes (id, author, title, description, thumbnail) VALUES
('3fOWbIrdRdIC', 'Jane Austen, Seth Grahame-Smith', 'Pride and Prejudice and Zombies: The Deluxe Heirloom Edition',
 'The deluxe heirloom edition of the "New York Times" bestseller boasts additional scenes of zombie mayhem, 13 new full-color illustrations, and an essay Afterword by Dr. Allen Grove, Professor of English Literature.',
 'http://books.google.com/books/content?id=3fOWbIrdRdIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

('g6C6P6NJQ1MC', 'Steve Hockensmith', 'Pride and Prejudice and Zombies: Dreadfully Ever After',
 'Complete with romance, heartbreak, martial arts, cannibalism, and an army of shambling corpses, Dreadfully Ever After brings the story of Pride and Prejudice and Zombies to a thrilling conclusion.',
 'http://books.google.com/books/content?id=g6C6P6NJQ1MC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

('KUMIEAAAQBAJ', 'Khaled Hosseini', 'The Kite Runner',
'THE NUMBER ONE BESTSELLER \'Devastating\' Daily Telegraph \'Heartbreaking\' The Times \'Unforgettable\' Isabel Allende \'Haunting\' Independent Afghanistan, 1975: Twelve-year-old Amir is desperate to win the local kite-fighting tournament and his loyal friend Hassan promises to help him. But neither of the boys can foresee what will happen to Hassan that afternoon, an event that is to shatter their lives. After the Russians invade and the family is forced to flee to America, Amir realises that one day he must return to Afghanistan under Taliban rule to find the one thing that his new world cannot grant him: redemption.',
'http://books.google.com/books/content?id=KUMIEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api');

/* Adding userdata for myself and a made-up password hash
location is kansas_city 01
*/
INSERT INTO UserAccounts (username, location, email, password_hash) VALUES 
('mattet', '00', 'mattetracy@outlook.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S'); -- I don't need to put the ID since it is autoincremented


INSERT INTO Bookshelves (bookshelf_name, user_id) VALUES
("Matt's Good Reads", 1); -- I don't need to put the ID since it is autoincremented

INSERT INTO BookshelfVolumes (bookshelf_id, volume_id, has_book) VALUES
(1, 'KUMIEAAAQBAJ', TRUE); -- I don't need to put the ID since it is autoincremented





SELECT * FROM Bookshelves;
SELECT * FROM BookshelfVolumes
