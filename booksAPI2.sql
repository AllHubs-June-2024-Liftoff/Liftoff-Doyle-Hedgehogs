DROP DATABASE IF EXISTS BooksAPI;

CREATE DATABASE BooksAPI;

USE BooksAPI;

CREATE TABLE UserAccounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE Volumes (
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    publisher VARCHAR(255),
    publishedDate VARCHAR(10),
    description TEXT,
    pageCount INT,
    printType VARCHAR(50),
    language VARCHAR(10),
    previewLink TEXT,
    infoLink TEXT,
    canonicalVolumeLink TEXT
);

CREATE TABLE Authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE VolumeAuthors (
    volume_id VARCHAR(50),
    author_id INT,
    PRIMARY KEY (volume_id, author_id),
    FOREIGN KEY (volume_id) REFERENCES Volumes(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES Authors(id) ON DELETE CASCADE
);

CREATE TABLE Bookshelves (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES UserAccounts(id) ON DELETE CASCADE
);

CREATE TABLE VolumeBookshelves (
    bookshelf_id INT,
    volume_id VARCHAR(50),
    PRIMARY KEY (bookshelf_id, volume_id),
    FOREIGN KEY (bookshelf_id) REFERENCES Bookshelves(id) ON DELETE CASCADE,
    FOREIGN KEY (volume_id) REFERENCES Volumes(id) ON DELETE CASCADE
);

INSERT INTO Authors (name)
VALUES 
('Joshua Bloch'), 
('Robert C. Martin'), 
('Stephen King');

-- Volumes Table Inserts
INSERT INTO Volumes (id, title, subtitle, publisher, publishedDate, description, pageCount, printType, language, previewLink, infoLink, canonicalVolumeLink)
VALUES 
('1cVqswEACAAJ', 'Effective Java', '', 'Addison-Wesley Professional', '2018', 
    'A definitive guide to Java best practices. This Jolt-award-winning classic has now been thoroughly updated to take full advantage of the latest language and library features, including Java 7, 8, and 9.', 
    416, 'BOOK', 'en', 
    'http://books.google.com/preview', 
    'http://books.google.com/info', 
    'http://books.google.com/canonical'),
('hjEFCAAAQBAJ', 'Clean Code', 'A Handbook of Agile Software Craftsmanship', 'Pearson Education', '2009', 
    'This title shows the process of cleaning code. Rather than just illustrating the end result, or just the starting and ending state, the author shows how several dozen seemingly small code changes can positively impact the performance and maintainability of an application code base.', 
    464, 'BOOK', 'en', 
    'http://books.google.com/clean-code-preview', 
    'http://books.google.com/clean-code-info', 
    'http://books.google.com/clean-code-canonical'),
('UbfnTcmkaKkC', 'The Stand', NULL, 'Anchor', '2008-06-24', 
    '#1 BESTSELLER • NOW A PARAMOUNT+ LIMITED SERIES • Stephen King’s apocalyptic vision of a world blasted by plague and tangled in an elemental struggle between good and evil remains as riveting—and eerily plausible—as when it was first published. One of The Atlantic’s Great American Novels of the Past 100 Years! This edition includes all of the new and restored material first published in The Stand: The Complete and Uncut Edition. A patient escapes from a biological testing facility, unknowingly carrying a deadly weapon: a mutated strain of super-flu that will wipe out 99 percent of the world’s population within a few weeks. Those who remain are scared, bewildered, and in need of a leader. Two emerge—Mother Abagail, the benevolent 108-year-old woman who urges them to build a peaceful community in Boulder, Colorado; and Randall Flagg, the nefarious “Dark Man,” who delights in chaos and violence. As the dark man and the peaceful woman gather power, the survivors will have to choose between them—and ultimately decide the fate of all humanity.', 
    1408, 'BOOK', 'en', 
    'http://books.google.com/books?id=UbfnTcmkaKkC&printsec=frontcover', 
    'https://play.google.com/store/books/details?id=UbfnTcmkaKkC', 
    'https://play.google.com/store/books/details?id=UbfnTcmkaKkC'),
('iCWgDwAAQBAJ', 'It', 'A Novel', 'Scribner', '2019-07-30', 
    'Stephen King''s classic #1 New York Times bestseller and the basis for the massively successful films It: Chapter One and It: Chapter Two as well as inspiration for HBO Max’s upcoming Welcome to Derry—about seven adults who return to their hometown to confront a nightmare they had first stumbled upon as teenagers...an evil without a name: It. Welcome to Derry, Maine. It’s a small city, a place as hauntingly familiar as your own hometown. Only in Derry the haunting is real. They were seven teenagers when they first stumbled upon the horror. Now they are grown-up men and women who have gone out into the big world to gain success and happiness. But the promise they made twenty-eight years ago calls them reunite in the same place where, as teenagers, they battled an evil creature that preyed on the city’s children. Now, children are being murdered again and their repressed memories of that terrifying summer return as they prepare to once again battle the monster lurking in Derry’s sewers.', 
    1184, 'BOOK', 'en', 
    'http://books.google.com/books?id=iCWgDwAAQBAJ&printsec=frontcover', 
    'http://books.google.com/books?id=iCWgDwAAQBAJ&dq=inauthor:Stephen+King', 
    'https://books.google.com/books/about/It.html?hl=&id=iCWgDwAAQBAJ'),
('d999Z2KbZJYC', 'On Writing', NULL, 'Simon and Schuster', '2002-06-25', 
    'The author shares his insights into the craft of writing and offers a humorous perspective on his own experience as a writer.', 
    321, 'BOOK', 'en', 
    'http://books.google.com/books?id=d999Z2KbZJYC&printsec=frontcover', 
    'http://books.google.com/books?id=d999Z2KbZJYC&dq=inauthor:Stephen+King', 
    'https://books.google.com/books/about/On_Writing.html?hl=&id=d999Z2KbZJYC'),
('iSKGDwAAQBAJ', 'The Institute', 'A Novel', 'Simon and Schuster', '2019-09-10', 
    'From #1 New York Times bestselling author Stephen King whose “storytelling transcends genre” comes “another winner: creepy and touching and horrifyingly believable” about a group of kids confronting evil. In the middle of the night, in a house on a quiet street in suburban Minneapolis, intruders silently murder Luke Ellis’s parents and load him into a black SUV. The operation takes less than two minutes. Luke will wake up at The Institute, in a room that looks just like his own, except there’s no window. And outside his door are other doors, behind which are other kids with special talents—telekinesis and telepathy—who got to this place the same way Luke did.', 
    672, 'BOOK', 'en', 
    'http://books.google.com/books?id=iSKGDwAAQBAJ&printsec=frontcover', 
    'http://books.google.com/books?id=iSKGDwAAQBAJ', 
    'https://play.google.com/store/books/details?id=iSKGDwAAQBAJ'),
('fVWQDQAAQBAJ', 'It', 'A Novel', 'Simon and Schuster', '2016-11-29', 
    'It began--and ended--in 1958 when seven desperate children searched in the drains beneath Derry for an evil creature, but in 1985, Mike Hanlon, once one of those children, makes six phone calls and disinters an unremembered promise that sets off the ultimate terror.', 
    1488, 'BOOK', 'en', 
    'http://books.google.com/books?id=fVWQDQAAQBAJ&printsec=frontcover', 
    'http://books.google.com/books?id=fVWQDQAAQBAJ', 
    'https://books.google.com/books/about/It.html?hl=&id=fVWQDQAAQBAJ');

INSERT INTO VolumeAuthors (volume_id, author_id)
VALUES 
('1cVqswEACAAJ', 1),
('hjEFCAAAQBAJ', 2),
('UbfnTcmkaKkC', 3),
('iCWgDwAAQBAJ', 3),
('d999Z2KbZJYC', 3),
('iSKGDwAAQBAJ', 3),
('fVWQDQAAQBAJ', 3);

SELECT * FROM Volumes;
SELECT * FROM Authors;
SELECT * FROM VolumeAuthors;
