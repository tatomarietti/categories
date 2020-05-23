# Java Categories/Sets Assignment

## Assignment Description

Original doc: [Java-Backend-Categories-Coding-Assignment_v2.docx](./Java-Backend-Categories-Coding-Assignment_v2.docx)

**NOTE:** Sentences from original document have been numbered so they can be easily referred as requirements un this document and inline code comments.

  1. In addition to a working program, unit tests should be produced.
  2. Treat this as if it was production code being delivered to a client.
  3. It should be properly packaged and delivered with sufficient documentation and instructions also provided.
  4. You must also describe how to deploy and monitor your service, as well as any changes that would be required to load balance your service.

  5. Develop the following exercise in Java/Springboot.

  6. As part of the system architecture at a data processing company, you need to design a Service to clean data produced by another service (the client). 

  7. The data is a list of category sub-category pairs. For example, one set of data might be:

|     Category     |     Subcategory     |
|------------------|---------------------|
|    PERSON        |    Bob Jones        |
|    PLACE         |    Washington       |
|    PERSON        |    Mary             |
|    COMPUTER      |    Mac              |
|    PERSON        |    Bob Jones        |
|    OTHER         |    Tree             |
|    ANIMAL        |    Dog              |
|    PLACE         |    Texas            |
|    FOOD          |    Steak            |
|    ANIMAL        |    Cat              |
|    PERSON        |    Mac              |

  8. There is a list of valid categories managed by your service.
By default, the valid categories are:

|     Category     |
|------------------|
|    PERSON        |
|    PLACE         |
|    ANIMAL        |
|    COMPUTER      |
|    OTHER         |

  9. When your service receives data from a client, it must process the data, removing duplicate (category, subcategory) pairs and invalid categories.
  10. The order of entries in the input data must be preserved, with the duplicates and invalid categories removed.
  11. The output must also include the count of entries for each valid category, sorted by the number of valid, unique entries.

Sample output for the sample input:

|     Category     |     Subcategory     |
|------------------|---------------------|
|    PERSON        |    Bob Jones        |
|    ANIMAL        |    Cat              |
|    ANIMAL        |    Dog              |
|    COMPUTER      |    Mac              |
|    PERSON        |    Mac              |
|    PERSON        |    Mary             |
|    PLACE         |    Texas            |
|    OTHER         |    Tree             |
|    PLACE         |    Washington       |

|     Category     |     Count        |
|------------------|------------------|
|    PLACE         |    2             |
|    PERSON        |    3             |
|    OTHER         |    1             |
|    COMPUTER      |    1             |
|    ANIMAL        |    2             |

  12. In addition to processing input data, your service must also provide the ability to add to, delete from, and list the valid categories.
  13. Once the category list is modified, subsequent processing requests will apply the new category list to the input data.
  14. While a real-world system would use a permanent datastore for the category information (so the current list of categories would be preserved if the service needs to be restarted), it is sufficient for this exercise to maintain the category list in memory.
  15. Please implement a REST Service that provides the above functionality.
  16. It is up to you to define the input and output data formats, as well as the REST endpoints that are used for data processing and category management.
  17. You must also describe how to deploy and monitor your service, as well as any changes that would be required to load balance your service.

## Solution

### Assumptions

  1. Categories names are case-sensitive and only valid in uppercase format and without blank characters (i.e.: `PERSON` is a valid category while `Person` is not).
  2. Categories names cannot contain blank characters (i.e.: `PER SON` is not a valid category).
  3. SubCategories names are case-sensitive and valid in any casing format (i.e.: `Bob Jones` is a valid sub-category and different than the also valid `bob jones`).
  4. SubCategories can contain blank characters (i.e.: `Bob Jones` is a valid category).
  5. While `Requirement 10` states `The order of entries in the input data must be preserved`, the sample provided is not preserving the input order and ordered by `Subcategory` instead.
This implementation is following Requirement 10 and assuming the given Sample is wrong.
  6. While `Requirement 11` states `The output must also include the count of entries for each valid category, sorted by the number of valid, unique entries`, it is not specifying ether ASCENDING or DESCENDING order.
This implementation assumes that the desired is descending (higher counts first) and that the order in the Sample output is wrong.

### Implementation notes

  1. Using latest release version (2.3.0.RELEASE) of SpringBoot https://github.com/spring-projects/spring-boot as of May 22nd 2020.
  2. Using latest release version (1.18.12) of Project Lombok https://projectlombok.org/ as of May 22nd 2020, to speed-up Java development and reduce boilerplate code.
  3. Using latest release version (3.10) of Apache Commons Lang https://commons.apache.org/proper/commons-lang/ as of May 22nd 2020, to speed-up Java development and reduce boilerplate code.
  4. Using h2 in-memory database as allowed by `Requirement 14`.
  5. Trying to add (POST) or delete (DELETE) a Category with an invalid name will result in a response with Http error code 400 (Bad request).
  6. Trying to add (POST) a Category with the same name of an already registered Category will result in a response with Http error code 409 (Conflict).
  7. Trying to add (DELETE) a Category with a non registered Category name will result in a response with Http error code 409 (Conflict). 

### Project Layout

- Source code mainly located under [src/main/java/com/tatomarietti/categories/service/](./categories-service/src/main/java/com/tatomarietti/categories/service/) directory:
  - `api` contains the code responsible of exposing the functionality through a REST API.
  - `app` contains the basic building blocks of the application.
  - `service` contains the logic orchestrating the received requests.
  - `storage` contains the code to support persistent storage (in memory by default, but can be easily configured to be persisted on disk).


![Project Layout](./categories-service/doc-img/project-layout.jpg?raw=true "Project Layout")

### Build and execute the solution

Steps to compile the application (from the repository root):
  1. Change to categories-service directory
     ```bash
     cd categories-service
     ```
  2. Compile and run tests (assuming maven 3 is installed on the system)
     ```bash
     mvn clean package
     ```
  3. Start the server in localhost at 8080
     ```bash
     java -jar target/categories-service-0.0.1-SNAPSHOT.jar
     ```
  4. After a couple of seconds the application will be started and listening at localhost on port 8080, and can be tested as described in the following section of this document.
  5. Errors and exceptions will be logged to standard output, optionally the output can be also redirected to a file with:
     ```bash
     java -jar target/categories-service-0.0.1-SNAPSHOT.jar | tee -a categories-service.log
     ```

### Tests

#### With Postman

- Request collection can be found [here](https://documenter.getpostman.com/view/4785998/Szt8dVRa?version=latest)
- Or downloaded/executed whith Postman with the following button
 
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/152a3fa3c20b96acc197)

#### With curl

- Get All Categories
```bash
curl --request GET 'localhost:8080/category'
```

- Add Category
```bash
curl --request POST 'localhost:8080/category' \
--header 'Content-Type: application/json' \
--data-raw '{
 "name": "FOOD"
}'
```

- Remove Category
```bash
curl --request DELETE 'localhost:8080/category' \
--header 'Content-Type: application/json' \
--data-raw '{
 "name": "FOOD"
}'
```

- Clean Items
```bash
curl --request POST 'localhost:8080/cleaner' \                                                                                                                                                                                                                            [23:05:47]
--header 'Content-Type: application/json' \
--data-raw '[
  {"category": "PERSON", "subCategory": "Bob Jones"},
  {"category": "PLACE", "subCategory": "Washington"},
  {"category": "PERSON", "subCategory": "Mary"},
  {"category": "COMPUTER", "subCategory": "Mac"},
  {"category": "PERSON", "subCategory": "Bob Jones"},
  {"category": "OTHER", "subCategory": "Tree"},
  {"category": "ANIMAL", "subCategory": "Dog"},
  {"category": "PLACE", "subCategory": "Texas"},
  {"category": "FOOD", "subCategory": "Steak"},
  {"category": "ANIMAL", "subCategory": "Cat"},
  {"category": "PERSON", "subCategory": "Mac"}
]'
```

**NOTE**: the previous collections assume that the service is running in localhost at port 8080 as described at [#Build-and-execute-the-solution](#Build-and-execute-the-solution) 

The codebase includes two different kind of tests:
 - Unit tests, testing individual classes functionality, especially these under [app](./categories-service/src/main/java/com/tatomarietti/categories/service/app) package.
   - Can be found under  [test/.../app](./categories-service/src/test/java/com/tatomarietti/categories/service/app).
 - Api tests, testing the different exposed endpoints an its interactions.
   - Can be found under  [test/.../api/controller](./categories-service/src/test/java/com/tatomarietti/categories/service/api/controller).

![Tests Run](./categories-service/doc-img/tests.jpg?raw=true "Tests Run")
