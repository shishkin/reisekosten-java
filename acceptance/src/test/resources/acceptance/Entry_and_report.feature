Feature: Entry and report

  Scenario: enter travel and create report
    Given accounting exists
    And I enter travel to "Leipzig" from "today 16:00" until "tomorrow 20:00"
    When I create report
    Then report contains 1 entry
    And total reported allowance is 18.0