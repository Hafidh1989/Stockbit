Feature: API Test

  Scenario: I want to check amount of returned data
    Given I want to check returned data with this amount '1'
    When I called the API
    Then amount of data is returned correctly

  Scenario: I want to check amount of returned data
    Given I want to check returned data with this amount '5'
    When I called the API
    Then amount of data is returned correctly

  Scenario: I want to check amount of returned data
    Given I want to check returned data with this amount '20'
    When I called the API
    Then amount of data is returned correctly

  Scenario: I want to print name
    When I called the API
    Then I should not get empty data
    Then I can print the name
    Then I can validate the array rules list validation
