###################################################################################
# file Example2.feature describe a scenario in Gherkins                           #
###################################################################################

Feature: Fight Club 

  Rule: Never talk about the Fight Club

    Example: Someone talks about it
       Given I am in a pub
       And there are some peole around
       When I hear someone talking about the Fight Club
       Then I immediately fight the guy

    Example: I talk about it
       Given I am somewhere
       And I am talking
       And some people listen to me
       When I talk about the Fight Club
       Then I must stop immediately
       When I can't stop talking abuot the Fight Club
       Then I must kill myself 

  Rule: Figth often

     Example: I have an opportunity
       Given I am in a place with other people
       And I see an opportunity for a quarrel
       Then I shall start fighlng


  Scenario Outline: Counting victories

    Given I have won <initwin> times already
    When I win a new fight
    Then I have won <updatedwin> fights
 
    Examples:
      | initwin  |  updatewin  | 
      |      12  |        13   | 
      |      20  |        21   | 
      |      50  |        51   | 
