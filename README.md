# QuantityMeasurementApp
UC6: Addition of Two Length Units (Same Category)
-
**Description**


UC6 extends UC5 by introducing addition operations between length measurements. This use case enables the Quantity Length API to add two lengths of potentially different units (but same category—length) and return the result in the unit of the first operand. Essentially adding another length to the current length. For example, adding 1 foot and 12 inches should yield 2 feet (based on the unit of the first operand).



**Preconditions**


Quantity Length class (from UC3/UC4/UC5) and LengthUnit enum exist with FEET, INCHES, YARDS, CENTIMETERS.

The conversionFactor for each LengthUnit is defined relative to a consistent base unit.

Two Quantity Length objects or raw values with their respective units are provided.

A target unit is the unit of the first operand.

All units belong to the same measurement category (length).


**Main Flow**


Client calls Quantity Length.add(length1, length2, targetUnit) or uses an instance method to add two length measurements.

**The method validates:**

Both length1 and length2 are non-null and have valid LengthUnits.

All values are finite numbers (Double.isFinite or equivalent).

Convert both length1 and length2 to a common base unit (feet).

Add the converted values.

Convert the sum from the base unit to the unit of the first operand.

Return a new Quantity Length object (or numeric value) representing the result in the unit of first operand.

**Postconditions**


A new Quantity Length object is returned with the sum of the two measurements expressed in the unit of the first operand.

The original Quantity Length objects remain unchanged (immutability principle).

Invalid inputs (null units, unsupported units, NaN, infinite) result in a documented exception (e.g., IllegalArgumentException).

Addition is mathematically accurate within floating-point precision limits.

Addition is commutative: add(A, B) equals add(B, A).