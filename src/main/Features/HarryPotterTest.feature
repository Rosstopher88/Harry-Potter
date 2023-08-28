Feature: Amazon Book Search

  Scenario: Search for a book and verify details
    Given Navigate to amazon.co.uk
    And Click on Privacy Notice
    And Accept cookies
    When Search for "Harry Potter and the Cursed Child" in the Books section
    Then Verify the first item title is "Harry Potter and the Cursed Child - Parts One and Two: The Official Playscript of the Original West End Production"
    And Verify if the first item has a badge
    When Click on the first book
    And Verify the selected type and price
    And Add the book to the basket
    Then Verify the notification "Added to Basket"
    And there is one item in the basket
    When Open Basket
    Then Verify the book details in the basket