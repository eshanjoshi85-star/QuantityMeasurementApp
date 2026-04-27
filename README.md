# QuantityMeasurementApp
UC3: Generic Quantity Class for DRY Principle
-

**Description**


UC3 is designed to overcome the Disadvantage of using Feet and Inches which starts violating the DRY principle, where both Feet and Inches classes contain nearly identical code, having the same constructor pattern, Identical equals() method implementation.


This Use Case refactors the existing Feet and Inches classes into a single generic Quantity Length class that eliminates code duplication while maintaining all functionality from UC1 and UC2. The Quantity Length class represents any measurement with a value and unit type, applying the DRY (Don't Repeat Yourself) principle. This reduces maintenance burden and makes the codebase more scalable for adding new units in the future.


**Preconditions**


The QuantityMeasurementApp class is instantiated.

Two numerical values with their respective unit types (feet, inches, etc.) are provided for comparison.

The conversion factors between supported units are defined as constants.

**Main Flow**


User inputs two numerical values with their respective unit types. 

The Quantity Length class validates the input values to ensure they are numeric.

The Quantity Length class validates the unit type against supported units.

Both values are converted to a common base unit (e.g., feet) using conversion factors.

The converted values are compared for equality.

The result of the comparison is returned to the user.

**Postconditions**


The equality result (true or false) is returned based on the comparison of the converted values.

All previous functionality from UC1 and UC2 is preserved and works correctly.

Code duplication is eliminated; maintenance is simplified.