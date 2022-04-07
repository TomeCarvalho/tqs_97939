# Colleague: Lucius Vinicius 96123
Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search books by publication year
    Given I have the following books in the store
      | title              | author          | category | published  |
      | One good book      | Anonymous       | Good     | 2013-03-14 |
      | Some other book    | Tim Tomson      | Mediocre | 2014-08-23 |
      | How to cook a dino | Fred Flintstone | Cooking  | 2012-01-01 |
    When the customer searches for books published between 2013-01-01 and 2014-01-01
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'One good book'

  Scenario: Search books by name
    Given I have the following books in the store
      | title                                    | author            | category | published  |
      | The Last Wish                            | Andrzej Sapkowski | Fantasy  | 2007-09-17 |
      | Blood of Elves                           | Andrzej Sapkowski | Fantasy  | 2008-10-10 |
      | Harry Potter and the Prisoner of Azkaban | J. K. Rowling     | Fiction  | 1999-07-08 |
    When the customer searches for books published by 'Andrzej Sapkowski'
    Then 2 books should have been found
    And Book 1 should have the title 'The Last Wish'
    And Book 2 should have the title 'Blood of Elves'

  Scenario: Search books by category
    Given I have the following books in the store
      | title                                    | author            | category | published  |
      | The Last Wish                            | Andrzej Sapkowski | Fantasy  | 2007-09-17 |
      | Blood of Elves                           | Andrzej Sapkowski | Fantasy  | 2008-10-10 |
      | Harry Potter and the Prisoner of Azkaban | J. K. Rowling     | Fiction  | 1999-07-08 |
    When the customer searches for books with the category 'Fantasy'
    Then 2 books should have been found
    And Book 1 should have the title 'The Last Wish'
    And Book 2 should have the title 'Blood of Elves'

  Scenario: Search books by title
    Given I have the following books in the store
      | title                                    | author            | category | published  |
      | The Last Wish                            | Andrzej Sapkowski | Fantasy  | 2007-09-17 |
      | Blood of Elves                           | Andrzej Sapkowski | Fantasy  | 2008-10-10 |
      | Harry Potter and the Prisoner of Azkaban | J. K. Rowling     | Fiction  | 1999-07-08 |
    When the customer searches for books with the title 'Last Wish'
    Then 1 books should have been found
    And Book 1 should have the title 'The Last Wish'