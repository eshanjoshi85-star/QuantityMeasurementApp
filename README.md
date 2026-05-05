# QuantityMeasurementApp

UC13: Centralized Arithmetic Logic to Enforce DRY in Quantity Operations
-
**Description**


UC13 refactors the arithmetic operations (addition, subtraction, division) implemented in UC12 to eliminate code duplication and enforce the DRY (Don't Repeat Yourself) principle. Instead of repeating unit conversion, base-unit normalization, and validation logic across multiple arithmetic methods, this use case introduces a centralized private helper method that encapsulates all common arithmetic logic.


By consolidating the repetitive code into a single, reusable helper method, UC13 improves maintainability, reduces bug risk, and establishes a scalable pattern for adding future arithmetic operations (multiplication, modulo, etc.) without duplicating logic. The public API remains unchanged; all behaviors from UC12 are preserved while the internal implementation is optimized for clarity and consistency.



**Disadvantages of UC12 Implementation**


UC12's direct implementation of arithmetic operations exhibits several architectural flaws:


Code Duplication Across Arithmetic Methods

add(), subtract(), and divide() each contain nearly identical code:

Null checks for operand and unit

Category type compatibility verification via unit.getClass()

Finiteness validation for numeric values

Base-unit conversion via IMeasurable.convertToBaseUnit()

Explicit target unit handling

Explicit target unit handling

Any of these checks present in all three methods with minimal variation.

Future arithmetic operations (multiplication, modulo, etc.) would duplicate this pattern further.

DRY Principle Violation

Common validation logic is copied verbatim across methods.

Error messages and validation checks are not centralized.

Changes to validation rules require updates in multiple locations.

Inconsistencies between methods become possible (e.g., one method uses different null-check behavior).

Increased Maintenance Burden

Bug fixes or improvements to conversion logic must be applied in three+ places.

Risk of partial updates (fixing one method while missing others).

Refactoring becomes complex as changes ripple across multiple methods.

New developers struggle to understand why logic is repeated.

Reduced Code Readability

Length of each arithmetic method obscures the core operation logic.

Readers must parse validation/conversion boilerplate before understanding the actual arithmetic.

Intent of the method is buried in repetitive code.

**Scalability Issues**

Adding multiplication, modulo, or other operations compounds duplication.

Validation and conversion logic would be replicated 5+, 6+, 7+ times.

Codebase grows unnecessarily; complexity increases without adding functionality.

**Inconsistent Error Handling**

Each method might handle errors slightly differently.

Some might throw exceptions; others return special values.

No centralized place to adjust error-handling strategy.

**Testing Complexity**

Validation scenarios must be tested separately for each operation.

Tests for add(), subtract(), and divide() contain nearly identical test cases.

Bug fixes or validation changes require updating tests in multiple locations.


**Preconditions**


All arithmetic operations from UC12 (add, subtract, divide) are fully functional and tested.

All unit enums (LengthUnit, WeightUnit, VolumeUnit, etc.) implement IMeasurable.

Behavior of arithmetic operations must remain unchanged after refactoring.

Existing test cases from UC12 will pass without modification.

The refactoring will be internal; public API signatures remain identical.

A centralized helper method will extract common logic.

Error handling and validation remain consistent across all operations.

Refactor will not change public method signatures or results.


**Main Flow**


**Step 1: Analyze Common Logic**


Identify validation steps shared across add, subtract, divide:

Null check on operand

Category type verification

Finiteness validation for both operands

Optional target unit validation

Identify conversion logic shared across operations:

Convert this to base unit

Convert operand to base unit

Perform arithmetic operation on base values

(For add/subtract) Convert result back to target unit

**Step 2: Design Arithmetic Operation Enum**

Create a private enum for ArithmeticOperation

ADD: Represents addition

SUBTRACT: Represents subtraction

DIVIDE: Represents division

MULTIPLY: (Optional for future use)

**Step 3: Create Centralized Private Helper Method**

**Logic flow:**

Validate inputs (non-null, same category, finite values)

Convert both operands to base unit

Execute arithmetic based on ArithmeticOperation type

Return base-unit result

**Step 4: Refactor Public Arithmetic Methods**

add(Quantity<U> other):

Calls helper with implicit target unit (first operand's unit)

Converts result to target unit

Returns new Quantity<U>

add(Quantity<U> other, U targetUnit):

Calls helper with explicit target unit

Converts result to target unit

Returns new Quantity<U>

subtract(Quantity<U> other):

Calls helper with implicit target unit

Converts result to target unit

Returns new Quantity<U>

subtract(Quantity<U> other, U targetUnit):

Calls helper with explicit target unit

Converts result to target unit

Returns new Quantity<U>

divide(Quantity<U> other):

Calls helper with irrelevant target unit (not used for division)

Returns dimensionless scalar without further conversion

**Step 5: Ensure Backward Compatibility**

All public methods retain original signatures.

All results match UC12 behavior exactly.

Error handling and exceptions remain consistent.

Existing test cases pass without modification.

**Step 6: Validate Consistency**

Verify that validation errors are identical across operations.

Confirm that cross-category checks work uniformly.

Test that division-by-zero handling is correct.

Validate rounding behavior for add/subtract.


**Postconditions**


All arithmetic operations (add, subtract, divide) delegate to a centralized helper.

Validation and conversion logic is implemented once, in the helper.

Code duplication across arithmetic methods is eliminated.

Public method signatures and behavior remain unchanged.

All UC12 test cases pass without modification.

Error handling is consistent across all operations.

Future arithmetic operations (multiplication, modulo, etc.) can reuse the same helper pattern.

Maintainability is significantly improved; changes to validation or conversion affect all operations uniformly.

Codebase complexity is reduced; individual method length is shortened.

DRY principle is enforced; no logic duplication exists.

Subtraction and division work across all measurement categories (length, weight, volume).

Mathematical properties are respected: subtraction is non-commutative, division is non-commutative.
