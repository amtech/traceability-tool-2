###################################################################################
# file Check.feature describe a scenario to test entry checking                   #
###################################################################################

Feature: Algorithm checking

  Scenario: Check algo 1 

    Given I do something 
    And I enter incorrect data 
    When I validate 
    Then An error message is displayed
    Then Reference SD : "[REQ-FULL-ALG-010]"
    When I enter correct data 
    And I validate 
    Then No error message is displayed
    Then Reference SD : "[REQ-FULL-ALG-020]"


