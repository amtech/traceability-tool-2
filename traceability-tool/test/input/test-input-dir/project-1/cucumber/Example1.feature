###################################################################################
# file Example1.feature describe a scenario in Gherkins                           #
###################################################################################

Feature:  The name of the feature
			Additional feature line 1
			Additional feature line 2

  @Workflow

  Background: Common initializations
  	Given I log in 
  	Given I am connected

  Scenario: Name of scenario 1

    Given  given 1 description part 1
    And  given 2 description part 1
    *  given 3 description part 1
    When when 1 description with " parameter 1 " part 1
    When when 2 description with " parameter 2 " part 1
    Then then 1 descrtiption with " expected 1 " part 1
    Then then 2 descrtiption with " expected 2 " part 1
    Then Reference SD : "[REQ1], [REQ2]"
    # Comment one
    Given  given 1 description part 2
    Given  given 2 description part 2
    When when 1 description with " parameter 1 " part 2
    Then then 1 descrtiption with " expected 1 " part 2
    Then Reference SD : "[REQ1], [REQ3]"
    # Comment two

  Scenario: Name of scenario 2

    Given  given 1 description part 1
    Given  given 2 description part 1
    Given  given 3 description part 1
    When check new row in TT history with Activity , Loop and Duration
      |Activity                  |Loop|Duration|
      |First Capture / Coding    |  1 |        |
      |Corrections Check         |  1 |        |
      |Corrections Check         |  2 |        |
      |Corrections Check         |  3 |        |
      |First Analysis            |  1 |        |
      |Double Checking           |  1 |        |
      |Error Correction          |  1 |        |
      |Error Correction          |  2 |        |
      |Error Correction          |  3 |        |
    When when 1 description with " parameter 1 " part 1
    When when 2 description with " parameter 2 " part 1
    Then then 1 descrtiption with " expected 1 " part 1
    Then then 2 descrtiption with " expected 2 " part 1
    Then Reference SD : "[REQ1], [REQ2]"
    # Comment three
    Given  given 1 description part 2
    Given  given 2 description part 2
    """
My doc string line 1
My doc string line 2
    """
    When when 1 description with " parameter 1 " part 2
    Then then 1 descrtiption with " expected 1 " part 2
    Then Reference SD : "[REQ1], [REQ3]"
    Given close web driver
    ```
Close doc string line 1
Close doc string line 2
    ```
    # Comment four


