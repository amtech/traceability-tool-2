Feature: Document Workflow

  Scenario: Create Document Entry
    When I log in to AimTool as "analyst1"
    And I click entry "Create a New Entry"
    And I create "Document"
    And "Entry Management Process - Create Document/Dataset" page is opened
    And "Received By *" select contains all values from file "received_by.csv" from column "Reception method type label" with default value ""
    And "AoR *" select contains all values from file "AOR.csv" from column "Associated AoR" with default value ""
    And "Affects *" select contains all values from file "AOR.csv" from column "Affect value" with default value ""
    And "Type *" select contains all values from file "document_type.csv" from column "Document type label" with default value ""
    And "Document ID *" is empty
    And "Effective From *" is empty
    And "Effective From *" is datepicker
    And "End Date" is empty
    And "End Date" is datepicker
    And "Time zone *" select contains all values from file "timezone.csv" from column "Time zone label" with default value "UTC+00 (Z)"
    And "Mercury ID" contains value in format 'current-date>/number'
    And "Mercury ID" is read only
    And "Issued By *" select contains all values from file "issuer.csv" from column "Issuer identifier" with default value ""
    And "Document Issues *" radiobutton has no default value
    And "Action Complete *" radiobutton has no default value
    And "Comments box" is empty
    And "Actions Pending" is empty
    When I select "01/01/2020 10:10" in date-picker "Effective From *"
    Then "Cycle" input is updated to "1913"

    When I select in "Received By *" values
      | CD | Post |
    And I select in "Type *" values
      | AIC |
    And I type "test_document_entry $TODAYS_DATE" into "Document ID *"
    And I select in "Issued By *" values
      | 8585 |
    And I choose option "No" in "Document Issues *"
    And I choose option "No" in "Action Complete *"
    And I complete entry
    Then validation fails on
      | Affects * | AoR * | Team Assignment * |
    When I select in "Affects *" values
      | Czech Republic | Afghanistan | Algeria | Angola |
    And I choose option "Navigation+ Procedures" in "Team Assignment *"
    And Reference SD "SD-AIM-UC-CCDE-240"
    And I complete entry
    Then assignment is updated
    When I expand "My AIM Tasks"
    When I click entry "My Tasks"
    Then pool doesn't contain "Document ID" equal to "test_document_entry $TODAYS_DATE"

  Scenario: Check document
    When I logout
    And I log in to AimTool as "analyst2"
    When I expand "My AIM Tasks"
    And I click entry "My Tasks"
    And I "Pick up for Check" row with "Document ID" equal to "test_document_entry $TODAYS_DATE"
    Then "Entry Management Process - Pick up for Check" page is opened
    When I assign document to me
    Then "Entry Management Process - Check [1]" page is opened
    When I logout
    And I log in to AimTool as "analyst1"
    When I expand "My AIM Tasks"
    When I click entry "My Tasks"
    Then pool doesn't contain "Document ID" equal to "test_document_entry $TODAYS_DATE"
    When I logout
    And I log in to AimTool as "analyst2"
    When I expand "My AIM Tasks"
    And I click entry "My Tasks"
    And I "Check" row with "Document ID" equal to "test_document_entry $TODAYS_DATE"
    Then "Entry Management Process - Check [1]" page is opened
    And data used to create entry is in readonly mode
    And "The entry contains error(s) *" radiobutton has no default value
    When I choose option "Yes" in "The entry contains error(s) *"
    And I complete entry
    Then validation fails on
      | Form Grid |
    When I open add new error form
    Then "External User Affected *" radiobutton has no default value
    And "Error Category" select contains all values from file "error_codes.csv" from column "Error category" with default value "Actioning errors"
    And Error, Code, Severity contain all values from file "error_codes.csv"
    And "Loop" input is updated to "1"
    And "Workflow Phase" input is updated to "Check"
    And "Comments" is read only
    And "Description" is empty
    And I choose error "Backlog errors" > "Actioned before effective date"
    And I choose option "Yes" in "External User Affected *"
    And I submit new error
    Then error grid contains "Backlog errors,Actioned before effective date,aMD4,Critical,Check,1"
    And I complete entry
    When I logout
    And I log in to AimTool as "analyst1"
    When I expand "My AIM Tasks"
    Then I click entry "My Tasks"
    And I "Correct" row with "Document ID" equal to "test_document_entry $TODAYS_DATE"
    Then "Entry Management Process - Correct" page is opened
    # uncheck team to verify that element is interactive
    When I choose option "Navigation+ Procedures" in "Team Assignment *"
    And I complete entry
    Then validation fails on
      | Team Assignment * |
    # check team again to comply with entry creation process
    When I choose option "Navigation+ Procedures" in "Team Assignment *"
    And Reference SD "SD-AIM-UC-CODE-020"
    Then I complete entry
    When I logout
    And I log in to AimTool as "analyst2"
    When I expand "My AIM Tasks"
    And I click entry "My Tasks"
    And I "Check Corrections" row with "Document ID" equal to "test_document_entry $TODAYS_DATE"
    Then "Entry Management Process - Check Corrections [2]" page is opened
    And "The entry contains error(s) *" radiobutton has no default value
    When I choose option "No" in "The entry contains error(s) *"
    And I "delete" line "Backlog errors,Actioned before effective date,aMD4,Critical,Check,1" from grid
    And I complete entry
    Then validation fails on
      | Form Grid |
    When I open add new error form
    And I choose error "No Error" > "Corrected"
    And I submit new error
    Then I choose option "No" in "The entry contains error(s) *"
    And I complete entry

  Scenario: Awaiting further actions
    When I logout
    And I log in to AimTool as "analyst2"
    And I expand "My AIM Tasks"
    And I click entry "Awaiting further actions"
    And I "Pick up for Further Actions" row with "Document ID" equal to "test_document_entry $TODAYS_DATE"
    Then "Entry Management Process - Pick up for Further Actions" page is opened
    And I assign document to me
    Then "Entry Management Process - Perform Further Actions" page is opened
    And "Team Assignment *" is not interactive
    And Reference SD "SD-AIM-UC-PEAA-030"
    And I complete entry
    And I expand "My AIM Tasks"
    Then I click entry "My Tasks"
    And pool doesn't contain "Document ID" equal to "test_document_entry $TODAYS_DATE"
    And I expand "My AIM Tasks"
    When I click entry "Awaiting further actions"
    Then pool doesn't contain "Document ID" equal to "test_document_entry $TODAYS_DATE"

  Scenario: Launch WMT Navigation Plus Process
    When I expand "Navigation+ Procedures Task"
    And I click entry "Document Analysis"
    And I "Pick-Up For Document Analysis" row with "Document ID" equal to "test_document_entry $TODAYS_DATE"
    And I assign document to me
    Then "Navigation Plus Procedure Sublfow - Document Analysis" page is opened
    When I choose option "Yes" in "Is action required? *"
    And I choose option "Yes" in "AIRAC *"
    And I choose option "High" in "Severity *"
    And I type "1" into "New SID *"
    And I type "1" into "Modified SID *"
    And I type "1" into "Suspended SID *"
    And I type "1" into "New STAR *"
    And I type "1" into "Modified STAR *"
    And I type "1" into "Suspended STAR *"
    And I type "1" into "New Approaches *"
    And I type "1" into "Modified Approaches *"
    And I type "1" into "Suspended Approaches *"
    Then Reference SD "SD-AIM-UC-NPP-CTW-010"
    And I complete entry
    And I expand "Navigation+ Procedures Task"
    And I click entry "Document Analysis"
    Then pool doesn't contain "Document ID" equal to "test_document_entry $TODAYS_DATE"
    Then Reference SD "SD-AIM-UC-NPP-CTW-020"