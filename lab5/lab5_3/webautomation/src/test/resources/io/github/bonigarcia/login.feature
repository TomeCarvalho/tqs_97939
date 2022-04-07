Feature: BlazeDemo flight purchase

  Scenario: Successful purchase
    When I navigate to "https://blazedemo.com/"
    And I select a flight from "Boston" to "London"
    And I click Find Flights
    And I click Choose This Flight
    And I write "John Darksoul" as my "Name"
    And I write "High Wall of Lothric" as my "Address"
    And I write "Lothric Castle" as my "City"
    And I write "Hollow" as my "State"
    And I write "12345" as my "Zip Code"
    And I select "American Express" as my "Credit Card Type"
    And I write "123" as my "Credit Card Number"
    And I write "03" as my "Month"
    And I write "2016" as my "Year"
    And I write "Chosen Undead" as my "Name on Card"
    And I click Purchase Flight
    Then I should see my receipt