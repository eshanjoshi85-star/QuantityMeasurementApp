# QuantityMeasurementApp
UC10: Generic Quantity Class with Unit Interface for Multi-Category Support
-
**Description**


UC10 addresses the architectural and design disadvantages introduced by UC9 by refactoring the design into a single, generic Quantity<U> class that works with any measurement category through a common IMeasurable interface. This use case eliminates code duplication across parallel QuantityLength and QuantityWeight classes, consolidates unit enum patterns, and simplifies the QuantityMeasurementApp class to adhere to the Single Responsibility Principle.


The refactoring maintains all functionality from UC1–UC9 while establishing a scalable, maintainable architecture that supports seamless addition of new measurement categories (volume, temperature, etc.) without code duplication.


Disadvantages of UC9 Implementation


UC9 introduced several architectural flaws that UC10 addresses:


Duplicate Unit Enum Structures

LengthUnit and WeightUnit enums contain nearly identical code:

Same method signatures: getConversionFactor(), convertToBaseUnit(), convertFromBaseUnit()

Same immutability guarantees

Redundant implementation patterns

Violates DRY principle; future units (VolumeUnit, TemperatureUnit) would amplify duplication.

Duplicate Quantity Class Logic

QuantityLength and QuantityWeight classes are nearly identical:

Same constructor validation

Identical equals() method structure (with different unit types)

Duplicate convertTo() logic

Replicated add() method implementations

Bug fixes or improvements must be applied separately to each class, increasing error risk.

Each new category requires writing identical boilerplate code.

QuantityMeasurementApp Single Responsibility Principle Violation

Class handles multiple, unrelated domains (length, weight) with separate methods:

demonstrateLengthEquality(), demonstrateWeightEquality()

demonstrateLengthConversion(), demonstrateWeightConversion()

demonstrateLengthAddition(), demonstrateWeightAddition()

Method duplication reflects identical underlying logic applied to different types.

Class becomes increasingly complex and harder to maintain as categories grow.

Mixed concerns: demonstration logic intertwined with category-specific handling.

Exponential Code Growth

Adding each new unit type requires:

New Unit enum (e.g., VolumeUnit)

New Quantity class (e.g., QuantityVolume)

New demonstration methods in QuantityMeasurementApp (minimum 3 methods)

New comprehensive test suite

Total codebase grows exponentially; maintenance burden escalates rapidly.

Pattern becomes unsustainable beyond 3-4 categories.

Inconsistency Risk

Refactoring comparison logic in QuantityLength without updating QuantityWeight creates subtle bugs.

Different categories may inadvertently have different precision, rounding, or validation logic.

No single source of truth for core operations.

Limited API Flexibility

Methods accepting Quantity<?> or generic comparisons are difficult to implement.

Factory methods or utility functions cannot work uniformly across categories.

The system becomes increasingly fragmented.

**Preconditions**


All functionality from UC1–UC9 is fully operational and tested.

An IMeasurable interface is defined to standardize unit behavior across all categories.

Both LengthUnit and WeightUnit enums are refactored to implement IMeasurable.

A generic Quantity<U extends IMeasurable> class is created to replace category-specific Quantity classes.

Type safety is maintained through generics; compile-time checking prevents category mismatches.

All existing test cases from UC1–UC9 continue to pass without modification.

The refactored design serves as a template for future measurement categories.


**Main Flow**


Define IMeasurable Interface

Create interface with methods required for unit conversions:

double getConversionFactor() - returns conversion factor relative to base unit

double convertToBaseUnit(double value) - converts value to base unit

double convertFromBaseUnit(double baseValue) - converts from base unit to this unit

String getUnitName() - returns readable unit name

Refactor LengthUnit Enum:

Implement IMeasurable interface

Keep all existing constants (FEET, INCHES, YARDS, CENTIMETERS) and conversion factors

Implement all interface methods with existing logic

No external API changes; fully backward compatible.

Refactor WeightUnit Enum:

Implement IMeasurable interface

Ensure same structure and method implementations as refactored LengthUnit

Consistency across enums improves maintainability

Create Generic Quantity Class:

Replaces both QuantityLength and QuantityWeight

Holds private final fields: double value and U unit

Constructor validates that unit is non-null and value is finite

Implements equals() method:

Checks object identity and null

Verifies unit types match (prevents cross-category comparison)

Converts both to base unit and compares using Double.compare()

Implements convertTo(U targetUnit) method:

Delegates to unit's conversion methods

Returns new Quantity<U> instance (immutability)

Rounds result to two decimal places

Implements add() methods (overloaded):

Add (Quantity<U> other) - result in first operand's unit

Add (Quantity<U> other, U targetUnit) - result in specified unit

Overrides hashCode() for collection support

Overrides toString() for readable output

Simplify QuantityMeasurementApp

Remove all category-specific demonstration methods

Create single generic demonstration methods accepting Quantity<?>

Consolidate comparison, conversion, and addition demonstration logic

Reduce class to orchestration and testing responsibilities only

Eliminate method duplication

Cross-Category Type Safety

equals() method checks this.unit.getClass() != that.unit.getClass()

Prevents invalid comparisons (e.g., 1 foot ≠ 1 kilogram)

Compiler enforces type constraints through generics

Runtime checks provide additional safety layer

Backward Compatibility

Type aliases or factory methods can maintain familiar APIs if needed

Existing test cases pass without modification

Generic implementation is transparent to callers

Scalability Pattern Establishment

Document process for adding new categories:

Create new enum implementing IMeasurable

Reuse Quantity<U> class with new enum

No new Quantity classes or demonstration methods needed

Pattern proven with length and weight categories


**Postconditions**


A single, type-safe Quantity<U extends IMeasurable> class replaces multiple category-specific Quantity classes.

All unit enums implement IMeasurable interface, eliminating structural duplication.

QuantityMeasurementApp is simplified with significantly fewer methods and reduced complexity.

DRY principle is upheld; logic is implemented once and reused across all categories.

Single Responsibility Principle is restored; each class has a clear, singular purpose.

All functionality from UC1–UC9 is preserved; behavior is identical.

Adding new measurement categories requires only:

New enum implementing IMeasurable interface

No changes to Quantity<U>, test infrastructure, or QuantityMeasurementApp

Maintenance burden is significantly reduced; changes are localized.

Code complexity scales linearly rather than exponentially.

Type safety is enhanced through generics and bounded type parameters.