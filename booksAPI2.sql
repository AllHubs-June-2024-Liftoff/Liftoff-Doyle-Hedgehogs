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
                             bookshelves_user_id INT,
                             FOREIGN KEY (bookshelves_user_id) REFERENCES UserAccounts(id)
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
"THE NUMBER ONE BESTSELLER 'Devastating' Daily Telegraph 'Heartbreaking' The Times 'Unforgettable' Isabel Allende 'Haunting' Independent Afghanistan, 1975: Twelve-year-old Amir is desperate to win the local kite-fighting tournament and his loyal friend Hassan promises to help him. But neither of the boys can foresee what will happen to Hassan that afternoon, an event that is to shatter their lives. After the Russians invade and the family is forced to flee to America, Amir realises that one day he must return to Afghanistan under Taliban rule to find the one thing that his new world cannot grant him: redemption.",
'http://books.google.com/books/content?id=KUMIEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

('kotPYEqx7kMC', 'George Orwell', '1984',
'75th ANNIVERSARY EDITION “Orwell saw, to his credit, that the act of falsifying reality is only secondarily a way of changing perceptions. It is, above all, a way of asserting power.” In 1984, London is a grim city in the totalitarian state of Oceania where Big Brother is always watching you and the Thought Police can practically read your mind. Winston Smith is a man in grave danger for the simple reason that his memory still functions. Drawn into a forbidden love affair, Winston finds the courage to join a secret revolutionary organization called The Brotherhood, dedicated to the destruction of the Party. Together with his beloved Julia, he hazards his life in a deadly match against the powers that be.',
    'http://books.google.com/books/content?id=kotPYEqx7kMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

('wrOQLV6xB-wC', 'J.K. Rowling', "Harry Potter and the Sorcerer's Stone",
"Turning the envelope over, his hand trembling, Harry saw a purple wax seal bearing a coat of arms; a lion, an eagle, a badger and a snake surrounding a large letter 'H'. Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry's eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin! Having become classics of our time, the Harry Potter eBooks never fail to bring comfort and escapism. With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new readers.",
'http://books.google.com/books/content?id=wrOQLV6xB-wC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),

('PGR2AwAAQBAJ', 'Harper Lee', 'To Kill a Mockingbird',
    "Harper Lee's Pulitzer Prize-winning masterwork of honor and injustice in the deep South—and the heroism of one man in the face of blind and violent hatred. One of the most cherished stories of all time, To Kill a Mockingbird has been translated into more than forty languages, sold more than forty million copies worldwide, served as the basis for an enormously popular motion picture, and was voted one of the best novels of the twentieth century by librarians across the country. A gripping, heart-wrenching, and wholly remarkable tale of coming-of-age in a South poisoned by virulent prejudice, it views a world of great beauty and savage inequities through the eyes of a young girl, as her father—a crusading local lawyer—risks everything to defend a black man unjustly accused of a terrible crime.",
    'http://books.google.com/books/content?id=PGR2AwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'
);
/* Adding userdata for myself and a made-up password hash from BCrypt
location is kansas_city 00
*/
INSERT INTO UserAccounts (username, location, email, password_hash) VALUES
('mattet', '00', 'mattetracy@outlook.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S'); -- I don't need to put the ID since it is autoincremented
INSERT INTO Bookshelves (bookshelf_name, bookshelves_user_id) VALUES
("Matt's Good Reads", 1); -- I don't need to put the ID since it is autoincremented
INSERT INTO BookshelfVolumes (bookshelf_id, volume_id, has_book) VALUES
(1, 'KUMIEAAAQBAJ', TRUE);

/* Adding userdata for random person and a made-up password hash from BCrypt
location is St. Louis 02
*/
INSERT INTO UserAccounts (username, location, email, password_hash) VALUES
('bob2342', '02', 'bob2342@gmail.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S'); -- I don't need to put the ID since it is autoincremented
INSERT INTO Bookshelves (bookshelf_name, bookshelves_user_id) VALUES
("Bob's Good Reads", 1); -- I don't need to put the ID since it is autoincremented
INSERT INTO BookshelfVolumes (bookshelf_id, volume_id, has_book) VALUES
(2, 'g6C6P6NJQ1MC', TRUE);

/* Adding userdata for random person and a made-up password hash from BCrypt
location is St. Louis 02
*/
INSERT INTO UserAccounts (username, location, email, password_hash) VALUES
('alice4242', '02', 'alice4242@gmail.com', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S'); -- I don't need to put the ID since it is autoincremented
INSERT INTO Bookshelves (bookshelf_name, bookshelves_user_id) VALUES
("Alice's Good Reads", 3); -- I don't need to put the ID since it is autoincremented
INSERT INTO BookshelfVolumes (bookshelf_id, volume_id, has_book) VALUES
(3, 'PGR2AwAAQBAJ', TRUE);

INSERT INTO UserAccounts (username, location, email, password_hash) VALUES
('doyle', '02', 'doyle@launchcode.org', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S'); -- I don't need to put the ID since it is autoincremented
INSERT INTO Bookshelves (bookshelf_name, bookshelves_user_id) VALUES
("Doyle's Good Reads", 4); -- I don't need to put the ID since it is autoincremented
INSERT INTO BookshelfVolumes (bookshelf_id, volume_id, has_book) VALUES
(4, 'KUMIEAAAQBAJ', TRUE);

INSERT INTO UserAccounts (username, location, email, password_hash) VALUES
('emily', '02', 'emilyu@launchcode.org', '$2a$10$7nT.LzAErkRf8nQuvDLP5OGW/R2fRS03zB6F8kMG/lCVdXsJ5lK.S'); -- I don't need to put the ID since it is autoincremented
INSERT INTO Bookshelves (bookshelf_name, bookshelves_user_id) VALUES
("Emily's Good Reads", 5); -- I don't need to put the ID since it is autoincremented
INSERT INTO BookshelfVolumes (bookshelf_id, volume_id, has_book) VALUES
(5, 'wrOQLV6xB-wC', TRUE);

-- this query will give us the information for all user_id, and all the book data we need across all of every users bookshelves (all their bookshelfs)
SELECT
    UserAccounts.id AS user_id,
    Bookshelves.bookshelves_user_id,
    UserAccounts.username AS username,
    UserAccounts.email AS email,
    UserAccounts.location AS location,
    Bookshelves.id AS bookshelf_id,
    Bookshelves.bookshelf_name AS bookshelf_name,
    BookshelfVolumes.volume_id AS volume_id,
    BookshelfVolumes.has_book AS has_book,
    BookshelfVolumes.unique_book,
    Volumes.author AS author,
    Volumes.title AS title,
    Volumes.description AS description,
    Volumes.thumbnail AS thumbnail
FROM
    UserAccounts
JOIN
    Bookshelves ON UserAccounts.id = Bookshelves.bookshelves_user_id -- fixed output result of SQL query, needed conditional
JOIN
    BookshelfVolumes ON Bookshelves.id = BookshelfVolumes.bookshelf_id -- fixed output result of SQL query, needed conditional
JOIN
    Volumes ON BookshelfVolumes.volume_id = Volumes.id
WHERE
    BookshelfVolumes.has_book = TRUE
ORDER BY
    UserAccounts.location, UserAccounts.username, Volumes.title;