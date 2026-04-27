# QuantityMeasurementApp
UC7: Addition with Target Unit Specification
-
**Description**


UC7 extends UC6 by providing flexibility in specifying the unit for the addition result. Instead of defaulting to the unit of the first operand, this use case allows the caller to explicitly specify any supported unit as the target unit for the result. This provides greater flexibility in use cases where the result must be expressed in a specific unit regardless of the operands' units. For example, adding 1 foot and 12 inches with a target unit of yards should yield approximately 0.667 yards.



**Preconditions**


Quantity Length class (from UC3/UC4/UC5/UC6) and LengthUnit enum exist with FEET, INCHES, YARDS, CENTIMETERS.

The conversionFactor for each LengthUnit is defined relative to a consistent base unit.

Two Quantity Length objects or raw values with their respective units are provided.

A target unit (distinct or same as operand units) is explicitly specified.

All units belong to the same measurement category (length).


**Main Flow**


Client calls Quantity Length.add(length1, length2, targetUnit) with an explicit target unit parameter.

**The method validates:**

Both length1 and length2 are non-null and have valid LengthUnits.

targetUnit is non-null and a valid LengthUnit.

All values are finite numbers (Double.isFinite or equivalent).

Convert both length1 and length2 to a common base unit (feet).

Add the converted values.

Convert the sum from the base unit to the explicitly specified targetUnit.

Return a new Quantity Length object representing the result in the target unit.


**Postconditions**


A new Quantity Length object is returned with the sum of the two measurements expressed in the explicitly specified target unit.

The original Quantity Length objects remain unchanged (immutability principle).

The result unit is always the specified target unit, not inferred from operands.

Invalid inputs (null units, unsupported units, NaN, infinite, or mismatched categories) result in a documented exception (e.g., IllegalArgumentException).

Addition is mathematically accurate within floating-point precision limits.

Addition remains commutative: add(A, B, targetUnit) equals add(B, A, targetUnit).