# Little Online Library
🦔🦔🦔🦔
## Overview
The Little Online Library is a web app built using Spring Boot + Java 17. Users can add books, manage their personal book collections, and search for books using the Google Books API. Includes user authentication, book rating, and grouping by hashtags.

## Features

- **User Accounts:** Register an account, email verification, HTTP Session Management
- **Personal Library:** Stored in mySQL DB via spring data-jpa
- **Search for Books:** Google books API
- **Rate Books:** Add ratings to the books in your library.
- **Tag Books:** Categorize your books with tags.

## Tech Stack
- **Java 17**
- **Spring Boot**
- **MySQL**
- **Google Books API** (API key is freely available from Google https://developers.google.com/books/docs/overview)
- **Bootstrap**

## Recommend Build
- Java 17
- MySQL
- Gradle
- IntelliJ


1. **Download the Code:**
   ```sh
   git clone https://github.com/yourusername/little-online-library.git
   cd little-online-library
   ```
2. **Set Up the Database:**
    - Spin up DB in mysql
    - nano `application.properties` w/ db info
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/library
      spring.datasource.username=root
      spring.datasource.password=password
      spring.jpa.hibernate.ddl-auto=update
      ```
3. **Run the App** in intelliJ

### Folder/File Structure

```
src/
├── main/
│   ├── java/org/launchcode/demo/
│   │   ├── controllers/    # Logic for data
│   │   ├── data/           # Repository
│   │   ├── models/         # Defines how data is organized
│   │   ├── templates/ 
│   │   ├── static/css/
│   ├── resources/
│   │   ├── application.properties  # mySQL db user info
└── build.gradle           
```
Future Enhancements
- Complete UX Overhaul
- Implement internal book borrowing system between users
- Review and Comment system added for books

LaunchCode Liftoff Doyle Hedgehogs Team 

🦔🦔🦔🦔

IA Doyle [https://github.com/dalpiad](https://github.com/dalpiad)
- AJ V  [https://github.com/aj-valerio](https://github.com/aj-valerio)
- Carolyn S  [https://github.com/Caro8923](https://github.com/Caro8923)
- David F. [https://github.com/davidaforbes](https://github.com/davidaforbes)
- Matthew Tracy [https://github.com/metracy](https://github.com/metracy)

## License

Probably MIT license.