Feature: Set
  Scenario: adding items
    When: adding foo
    Then: it should have a size of 1
    Then: it should contain foo
  Scenario: empty
    Then: should have a size of 0
    Then: should throw when first is invoked
  Scenario: getting the first item
    Given: a non-empty set
    When: getting the first item
    Then: it should return the first item
