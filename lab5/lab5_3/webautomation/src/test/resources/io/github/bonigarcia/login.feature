Feature: BlazeDemo flight purchase

  Scenario: Successful purchase
    When I navigate to "https://blazedemo.com/"
    And I select a flight from "Boston" to "London"
    And I click Find Flights
    And I click Choose This Flight
    And I write "John Darksoul" as my "inputName"
    And I write "High Wall of Lothric" as my "address"
    And I write "Lothric Castle" as my "city"
    And I write "Hollow" as my "state"
    And I write "12345" as my "zipCode"
    And I select "American Express" as my "cardType"
    And I write "123" as my "creditCardNumber"
    And I write "03" as my "creditCardMonth"
    And I write "2016" as my "creditCardYear"
    And I write "Chosen Undead" as my "nameOnCard"
    And I click Purchase Flight
    Then I should see my receipt