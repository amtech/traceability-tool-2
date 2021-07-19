Feature: Search Task
  Scenario: Search Entries
    When selected number of items per page is "10"
    And user checks that the columns "Mercury ID, Document ID, Document Type, Issued By, Affects, Effective From, Cycle, End Date, Date Created, Document Status, AIM AoR" are sortable
    Then Reference SD "SD-AIM-UC-SEBK-040"
