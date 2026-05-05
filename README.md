# QuantityMeasurementApp
UC11: Volume Measurement Equality, Conversion, and Addition (Litre, Millilitre, Gallon)
-
**Description**


UC11 extends the Quantity Measurement Application to support volume measurements alongside length and weight measurements. This use case introduces a new measurement category—volume—that operates independently from length and weight through the generic Quantity<U> class and IMeasurable interface established in UC10.


Similar to length and weight measurements, volume measurements in different units (litres, millilitres, gallons) will support equality comparison, unit conversion, and arithmetic addition operations. The application will support three volume units:


Litre (L): Base unit for volume conversions

Millilitre (mL): 1 L = 1000 mL

Gallon (gal): 1 gallon ≈ 3.78541 L

UC11 validates that the generic design patterns established in UC10 scale seamlessly to a third measurement category without requiring any modifications to the Quantity<U> class, QuantityMeasurementApp, or existing test infrastructure. This use case demonstrates the true power of the refactored architecture and confirms that adding new measurement categories is straightforward and repeatable.



**Preconditions**


The refactored Quantity<U extends IMeasurable> class from UC10 is fully operational.

The IMeasurable interface is defined with methods for unit conversions.

Both LengthUnit and WeightUnit enums implement IMeasurable and are fully functional.

All functionality from UC1–UC10 is preserved and unaffected by UC11 additions.

A new VolumeUnit enum will be created implementing IMeasurable with LITRE as the base unit.

Conversion factors for all volume units are defined relative to litres (base unit).

Volume measurements are treated as a separate, non-interoperable category from length and weight.

No modifications to existing Quantity<U>, IMeasurable, or QuantityMeasurementApp are required.


**Main Flow**


Create VolumeUnit Enum Implementing IMeasurable

Define an enum with volume units (LITRE, MILLILITRE, GALLON).

Assign conversion factors relative to the base unit (litre):

LITRE: 1.0 (base unit)

MILLILITRE: 0.001 (1 mL = 0.001 L)

GALLON: 3.78541 (1 gallon ≈ 3.78541 L)

Implement all IMeasurable interface methods:

getConversionFactor() - returns the conversion factor

convertToBaseUnit(double value) - converts value to litres

convertFromBaseUnit(double baseValue) - converts from litres to this unit

getUnitName() - returns readable unit name

Equality Comparison

User inputs two numerical values with their respective volume unit types.

Quantity<VolumeUnit> class (inherited from generic Quantity<U>) validates input values.

Both values are converted to the common base unit (litre) using VolumeUnit conversion methods.

The converted values are compared for equality using the generic equals() method.

The result of the comparison (true or false) is returned.

Unit Conversion

User inputs a numerical value, source unit, and target unit (all volume units).

Quantity<VolumeUnit>.convertTo(targetUnit) converts the measurement to the target unit.

The method normalizes through the base unit (litre) and applies appropriate conversion factors.

A new Quantity<VolumeUnit> object is returned with the converted value and target unit.

Addition Operations

User inputs two Quantity<VolumeUnit> objects and optionally a target unit.

Both measurements are converted to the base unit (litre) using the generic add() method.

The converted values are summed.

The result is converted to the target unit (either first operand's unit or explicitly specified unit).

A new Quantity<VolumeUnit> object representing the sum is returned.

Cross-Category Type Safety

Attempting to compare volume with length or weight returns false (different categories).

Compiler prevents mixing Quantity<VolumeUnit> with Quantity<LengthUnit> or Quantity<WeightUnit>.

Runtime type checking in equals() method ensures category isolation.

Integration with Existing System

VolumeUnit enum is used seamlessly with the existing generic Quantity<U> class.

No modifications to QuantityMeasurementApp needed; existing generic methods handle volume quantities.

All existing demonstration and test methods work with volume units automatically.


**Postconditions**


Volume measurements of the same unit and value are considered equal.

Volume measurements of different units but equivalent values are considered equal (e.g., 1 L = 1000 mL = ~0.264172 gallons).

Unit conversions between volume units produce mathematically accurate results within floating-point precision.

Addition of two volume measurements produces a new Quantity<VolumeUnit> object without modifying originals (immutability).

All previous functionality from UC1–UC10 for length and weight measurements is preserved and works correctly.

Volume, length, and weight measurements are treated as separate, non-interoperable categories.

No modifications to Quantity<U>, IMeasurable, QuantityMeasurementApp, or existing test infrastructure are required.

The architectural pattern is validated as truly scalable; new categories integrate effortlessly.

Adding additional measurement categories (temperature, time, etc.) follows the identical pattern.