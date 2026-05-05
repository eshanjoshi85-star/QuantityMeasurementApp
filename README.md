# QuantityMeasurementApp
UC9: Weight Measurement Equality, Conversion, and Addition (Kilogram, Gram, Pound)
-
**Description**


UC9 extends the Quantity Measurement Application to support weight measurements alongside length measurements. This use case introduces a new measurement category—weight—that operates independently from length. Similar to how length measurements (feet, inches, yards, centimeters) are compared for equality, converted between units, and added together, weight measurements in different units (kilograms, grams, pounds) will support the same operations.


The application will support three weight units:


Kilogram (kg): Base unit for weight conversions

Gram (g): 1 kg = 1000 g

Pound (lb): 1 lb ≈ 0.453592 kg

UC9 demonstrates that the generic design patterns established in UC1–UC8 scale seamlessly to multiple measurement categories. The WeightUnit enum and QuantityWeight class mirror the LengthUnit and QuantityLength design, reinforcing consistency and maintainability across the application.



**Preconditions**


The QuantityMeasurementApp class is instantiated.

Two or more numerical values with their respective weight unit types (kilogram, gram, pound) are provided for comparison, conversion, or addition.

The conversion factors between supported weight units are defined as constants relative to kilogram (base unit).

The WeightUnit enum exists as a standalone class with conversion responsibility (mirroring UC8 refactoring for LengthUnit).

Length functionality from UC1–UC8 remains fully operational and unaffected.

Weight and length measurements are treated as separate, incomparable categories.


**Main Flow**


Equality Comparison:

User inputs two numerical values with their respective weight unit types.

QuantityWeight class validates the input values to ensure they are numeric and units are valid.

Both values are converted to the common base unit (kilogram) using WeightUnit conversion methods.

The converted values are compared for equality using the overridden equals() method.

The result of the comparison (true or false) is returned.

Unit Conversion:

User inputs a numerical value, source unit, and target unit.

QuantityWeight.convertTo(targetUnit) converts the measurement to the target unit.

The method normalizes through the base unit (kilogram) and applies appropriate conversion factors.

A new QuantityWeight object is returned with the converted value and target unit.

Addition Operations:

User inputs two QuantityWeight objects and optionally a target unit.

Both measurements are converted to the base unit (kilogram).

The converted values are summed.

The result is converted to the target unit (either first operand's unit or explicitly specified unit).

A new QuantityWeight object representing the sum is returned.


**Postconditions**


Weight measurements of the same unit and value are considered equal.

Weight measurements of different units but equivalent values are considered equal (e.g., 1 kg = 1000 g = 2.20462 lb).

Unit conversions between weight units produce mathematically accurate results within floating-point precision.

Addition of two weight measurements produces a new QuantityWeight object without modifying originals (immutability).

All previous functionality from UC1–UC8 for length measurements is preserved and works correctly.

Length and weight measurements are treated as separate, incomparable categories (1 foot ≠ 1 kilogram).

The architectural pattern established supports straightforward addition of new measurement categories (temperature, volume, etc.).
