###################################################################################
# file InvadliExample2.feature describe a scenario in Gherkins                    #
# This is invalid because the doc string never ends                               #
###################################################################################

Feature: DocSring not ended

  Scenario: Scenario with not closed DocString

    Given I am in initial condition
    When I enter 
    """
My doc string line 1
My doc string line 2
    # Not ended DocStrign
    Then I shall crash 

 
