# QuantityMeasurementApp
UC4: Extended Unit Support
-

**Description**


UC4 extends UC3 by introducing Yards and Centimeters as additional length units to the QuantityLength class. This use case demonstrates how the generic Quantity class design scales effortlessly to accommodate new units without code duplication. Yards will be added to the LengthUnit enum with the appropriate conversion factor (1 yard = 3 feet) and (1cm = 0.393701in), and all equality comparisons will work seamlessly across feet, inches, yards, and cms.



**Preconditions**


The QuantityMeasurementApp class is instantiated with the refactored QuantityLength class from UC3.

Two numerical values, with their respective units (feet, inches, yards), are provided for comparison.

The conversion factor for yards (1 yard = 3 feet) is defined in the LengthUnit enum.

The conversion factor for yards (1 cm = 0.393701 in) is defined in the LengthUnit enum.

**Main Flow**


Users input two numerical values with their respective unit types (feet, inches, yards or cms).

The QuantityLength class validates the input values to ensure they are numeric.

The QuantityLength class validates the unit type against supported units (feet, inches, yards, cms).

Both values are converted to a common base unit (in or feet) using conversion factors.

The converted values are compared for equality.

The result of the comparison is returned to the user.

**Postconditions**



The equality result (true or false) is returned based on the comparison of the converted values.

All previous functionality from UC1, UC2, and UC3 is preserved and works correctly.

Yard-to-yard, yard-to-feet, and yard-to-inches comparisons are fully supported.

Similarly, all comparisons with respect to centimeters should be supported (cm-to-cm, cm-to-inch, cm-to-feet, etc.)

Code remains free of duplication; adding new units requires only enum modification.