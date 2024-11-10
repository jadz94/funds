Feature: Account Creation Rest Endpoint Test

  Background:
    * url serviceBaseUrl
    * def sleep = (millis) => { java.lang.Thread.sleep(millis) }

  Scenario: Set up accounts and test transfer funds
    * def account1 =
    """
        {
          "id": 2,
          "currency": "EUR",
          "balance": 200
        }
    """
    Given path 'accounts'
    And request account1
    And header Content-Type = 'application/vnd.fnd.account-api.v1+json'
    When method post
    Then status 200

    * def account2 =
    """
        {
          "id": 3,
          "currency": "EUR",
          "balance": 200
        }
    """
    Given path 'accounts'
    And request account2
    And header Content-Type = 'application/vnd.fnd.account-api.v1+json'
    When method post
    Then status 200

    * sleep(5000)
    * def transfer =
    """
        {
          "fromAccount": {
            "id": 2,
            "currency": "EUR"
          },
          "toAccount": {
            "id": 3,
            "currency": "EUR"
          },
          "amount": 50,
          "amountCurrency": "EUR"
        }
    """

    Given path 'transfer'
    And request transfer
    And header Content-Type = 'application/vnd.fnd.transfer-api.v1+json'
    When method post
    Then status 200

    # check transferred results
    Given path 'accounts'
    And retry until response.length >= 2
    When method get
    Then status 200
    Then match response contains { "id": 2, "currency": "EUR", "balance": 150 }
    Then match response contains { "id": 3, "currency": "EUR", "balance": 250 }