# QuantityMeasurementApp
UC2: Feet and Inches measurement equality
-

**Description**


This Use Case extends UC1 to accommodate the Equality Check for Inches along with Feet. This use case is in no way trying to compare two entities, Feet and Inches. They are still treated separately. Please ensure like UC1 the test cases ensure complete test coverage to accurately compare and handle various edge cases


**Preconditions**


The QuantityMeasurementApp class is instantiated.

Two numerical values in feet and inches are hard-coded for comparison.

**Main Flow**


The main method calls the static method, which validates two numerical values in feet.

The main method calls the static method, which validates two numerical values in inches.

These static methods internally instantiate the Feet and Inches class and then call the equality method.

Both classes validate the input values to ensure they are numeric.

Both classes compare the two values for equality.

The result of the comparison is returned to the user.

**Postconditions**


The equality result (true or false) is returned based on the comparison of the converted values.
Both inch-to-inch and feet-to-inch comparisons are supported.
