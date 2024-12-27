# SQL Table Documentation

(Formatting Markdown Edits with HackMD.io at
https://hackmd.io/1ckKW1WERVWskDP1vIePwA )
This document outlines the structure and logic of the SQL tables used for storing user accounts, volumes of books, bookshelfs (sp on purpose), and individual bookshelf items tied to users.

---

## **`user_account` Table**
This table stores user account details.

| **Column Name**  | **Description**                                            |
|-------------------|----------------------------------------------------------|
| `id`             | Auto-incremented unique identifier for each user.         |
| `username`       | Unique username chosen by the user.                       |
| `location`       | Location of the user account: <br>0 = Kansas City, <br>1 = Philadelphia, <br>2 = St. Louis. |
| `email`          | User's unique email address.                              |
| `password_hash`  | BCrypt hashed password for storage in MySQL database. Reference values initially inserted into the DB are mock hashes                |

---

## **`volume` Table**
This table contains abstracted book information taken from the Google Books API.

| **Column Name**  | **Description**                                          |
|-------------------|--------------------------------------------------------|
| `id`             | Unique book identifier (Google Books API ID).          |
| `author`         | Author(s) of the book.                                  |
| `title`          | Title of the book.                                      |
| `description`    | Description of the book content. Note: stored as a TEXT not VARCHAR(255), stored outside of table?                        |
| `thumbnail`      | URL of the book's cover image in good resolution.       |

---

## **`bookshelf` Table**
This table represents user-specific bookshelf's (SP on purpose). Each user can have multiple bookshelves.

| **Column Name**      | **Description**                                      |
|-----------------------|----------------------------------------------------|
| `id`                 | Auto-generated unique identifier for each bookshelf. |
| `bookshelf_name`     | User-defined name for the bookshelf.                |
| `bookshelf_user_id`  | Foreign key referencing `user_account(id)` for linking the bookshelf to its owner. |

---

## **`bookshelf_volume` Table**
This table links `bookshelf` table to `volumes` table (books), such that the database associates books and is stored in `bookshelf_volume` table.

| **Column Name**      | **Description**                                                                                                                                               |
|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `bookshelf_id`       | Foreign key referencing `bookshelf(id)` to link a book to a specific bookshelf.                                                                               |
| `volume_id`          | Foreign key referencing `volume(id)` for the associated book.                                                                                                 |
| `has_book`           | Boolean value: `TRUE` if the book is with the user; `FALSE` if moved to different user.                                                                       |
| `unique_book`        | A **stored computed value** (`VARCHAR(255)`) combining `bookshelf_id` and `volume_id` with an underscore (`_`). Used for quick reference to the composite key. |

### **Primary Key**
The primary key of the `bookshelf_volume` table is a composite key consisting of `bookshelf_id` and `volume_id`. This ensures that a single book can be uniquely linked to a specific bookshelf. Primary key is also stored in a column for easily visualizing the composite key in `unique_book`.

---

### Nature of the Relationships Between the Tables

1. **`user_account` → `bookshelf`**:
    - One-to-many relationship: A user can have multiple `bookshelf`s across the rows of table.

2. **`bookshelf` → `bookshelf_volume`**:
    - One-to-many relationship: A `bookshelf_volume` table can hold multiple books.

3. **`volume` → `bookshelf_volume`**:
    - Many-to-one relationship: An abstracted book stored in `volume` can appear in multiple `bookshelf_volume`, across the rows of that table?

4. **`bookshelf_volume`**
    - Many-to-Many relationship: This table is a junction table using the information inside each table and associating `bookshelf` with `user_account` and `volume` tables. Essentially generating the `unique_book` necessary
      for keeping track of things in the book.  with the added ability to track whether the book is currently with that user (`has_book`).

---
#### TLDR
This DOC provides the structural relationships within te SQL database schema.
Please refer back to these docs as we implement new features in our Little Online Library using Spring and Thyme and Hopefully when we eventually figure out Spring Security.