Feature: Statistics search
  Scenario: Fetch statistics
    Given the user opens Firefox and navigates to the application
    When the user searches for statistics for the country USA on the date 2021-06-17
    Then the table should show '+9743' new cases
    And the table should show '+276' new deaths