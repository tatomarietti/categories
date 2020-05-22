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

## Assumptions

1. Categories names are case-sensitive and only valid in uppercase format and without blank characters (i.e.: `PERSON` is a valid category while `Person` is not).
2. Categories names cannot contain blank characters (i.e.: `PER SON` is not a valid category).
3. SubCategories names are case-sensitive and valid in any casing format (i.e.: `Bob Jones` is a valid sub-category and different than the also valid `bob jones`).
4. SubCategories can contain blank characters (i.e.: `Bob Jones` is a valid category).
5. While `Requirement 10` states `The order of entries in the input data must be preserved`, the sample provided is not preserving the input order and ordered by `Subcategory` instead.
This implementation is following Requirement 10 and assuming the given Sample is wrong.
6. While `Requirement 11` states `The output must also include the count of entries for each valid category, sorted by the number of valid, unique entries`, it is not specifying ether ASCENDING or DESCENDING order.
This implementation assumes that the desired is descending (higher counts first) and that the order in the Sample output is wrong.

## Implementation notes

1. Using latest release version (2.3.0.RELEASE) of SpringBoot https://github.com/spring-projects/spring-boot as of May 22nd 2020.
2. Using latest release version (1.18.12) of Project Lombok https://projectlombok.org/ as of May 22nd 2020, to speed-up Java development and reduce boilerplate code.
3. Using latest release version (3.10) of Apache Commons Lang https://commons.apache.org/proper/commons-lang/ as of May 22nd 2020, to speed-up Java development and reduce boilerplate code.
