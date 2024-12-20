DROP DATABASE IF EXISTS BooksAPI;

CREATE DATABASE BooksAPI;

USE BooksAPI;

CREATE TABLE UserAccounts (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              username VARCHAR(100) UNIQUE NOT NULL,
                              location INT,
                              email VARCHAR(100) UNIQUE NOT NULL,
                              password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE Volumes (
                         id VARCHAR(50) PRIMARY KEY, -- Google booksAPI's unique string
                         author VARCHAR(50), -- Author associated with unique book
                         title VARCHAR(255) NOT NULL, -- title associated with unique book
                         description TEXT, -- description from google unique string description
                         thumbnail TEXT -- normal res book photo thumbnail
);

CREATE TABLE Bookshelves (  -- Each bookshelf is associated with a specific user, can have more than one bookshelf
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             bookshelf_id VARCHAR(255) NOT NULL, -- what they name their specific bookshelf, "i.e. AJ's bookshelf favorites"
                             user_id INT,
                             FOREIGN KEY (user_id) REFERENCES UserAccounts(id)
);

CREATE TABLE BookshelfVolumes (
                                  bookshelf_id INT, -- the bookshelf ID that belongs to a specific user. This way each user could have more than one bookshelf/
                                  volume_id VARCHAR(50), -- this will be the unique exact book.
                                  has_book BOOLEAN, -- TRUE means that person has the book currently, FALSE means person does not have the book on their current bookshelf.
                                  PRIMARY KEY (bookshelf_id, volume_id),
                                  FOREIGN KEY (bookshelf_id) REFERENCES Bookshelves(id),
                                  FOREIGN KEY (volume_id) REFERENCES Volumes(id),
                                  FOREIGN KEY (volume_author) REFERENCES Volumes(author),
                                  FOREIGN KEY (volume_title) REFERENCES Volumes(title),
                                  FOREIGN KEY (book_location) REFERENCES UserAccounts(location)
);
-- Let me know what everyone thinks of this, commits to come
