# QuantityMeasurementApp
UC5: Unit-to-Unit Conversion (Same Measurement Type)
-
**Description**


UC5 extends UC4 by providing explicit conversion operations between length units (e.g., feet → inches, yards → inches, centimeters → feet). Instead of only comparing equality, the Quantity Length API exposes a conversion method that returns a numeric value converted from a source unit to a target unit using the centralized conversion factors.



**Preconditions**


Quantity Length class (from UC3/UC4) and LengthUnit enum exist and include FEET, INCHES, YARDS, CENTIMETERS.

The conversionFactor for each LengthUnit is defined relative to the chosen base unit (feet or another consistent base).

Input: numeric value, a valid source LengthUnit, and a valid target LengthUnit.

**Main Flow**


Client calls Quantity Length.convert(value, sourceUnit, targetUnit) or uses an instance method to request conversion.

**The method validates:**

value is a finite number (Double.isFinite or equivalent).
sourceUnit and targetUnit are non-null and members of LengthUnit.

Convert the input value to the common base unit (e.g., feet) using sourceUnit.getConversionFactor().

Convert from the base unit to the target unit by dividing by targetUnit.getConversionFactor() (or multiplying by appropriate reciprocal).

Apply optional rounding or precision handling (caller-specified or a default epsilon).

Return the converted numeric value to the caller.


**Postconditions**


A numeric value representing the original measurement expressed in the target unit is returned.

Invalid inputs (null unit, unsupported unit, NaN, infinite) result in a documented exception (e.g., IllegalArgumentException) or a well-defined error response.

Conversion preserves mathematical equivalence within floating-point precision limits.