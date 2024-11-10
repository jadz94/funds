Feature: Account Creation Rest Endpoint Test

  Background:
    * url serviceBaseUrl

  Scenario: Create Account and check if it is saved
    * def body =
    """
        {
          "id": 1,
          "currency": "EUR",
          "balance": 200
        }
    """
    Given path 'accounts'
    And request body
    And header Content-Type = 'application/vnd.fnd.account-api.v1+json'
    When method post
    Then status 200

  Scenario: Fetch saved account in previous scenario
    Given path 'accounts'
    And retry until response.length >= 3
    When method get
    Then status 200
    Then match response contains [{ "id": 1, "currency": "EUR", "balance": 200 }]
