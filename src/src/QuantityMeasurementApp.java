// Version 8.0
//Eshan Pankaj Joshi
// UC1: Feet measurement equality
// UC2: Feet and Inches measurement equality
// UC3: Generic Quantity Class for DRY Principle
// UC4: Extended Unit Support
// UC5: Unit-to-Unit Conversion
// UC7: Addition with Target Unit Specification
// UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility


public class QuantityMeasurementApp {

    // ---------------- UC1 ----------------
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ---------------- UC2 ----------------
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ---------------- UC3 → UC8 ----------------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid Quantity");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.convertToBaseUnit(value);
        }

        public Quantity convertTo(LengthUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = this.toBaseUnit();
            double converted = target.convertFromBaseUnit(base);

            return new Quantity(converted, target);
        }

        // ---------------- COMMON ADDITION ----------------
        private static Quantity addInternal(Quantity q1, Quantity q2, LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new Quantity(result, targetUnit);
        }

        // UC6
        public Quantity add(Quantity other) {
            return addInternal(this, other, this.unit);
        }

        // UC7
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            return addInternal(q1, q2, targetUnit);
        }

        // ---------------- EQUALITY ----------------
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        System.out.println("=== Quantity Measurement App (UC8) ===");

        // UC3 Equality
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCHES);
        System.out.println("1 ft == 12 inches: " + q1.equals(q2));

        // UC5 Conversion (now delegated)
        System.out.println("1 ft → inches: " + q1.convertTo(LengthUnit.INCHES));

        // UC6 Addition
        System.out.println("1 ft + 12 inches (feet): " + q1.add(q2));

        // UC7 Addition with target unit
        System.out.println("1 ft + 12 inches (yards): " +
                Quantity.add(q1, q2, LengthUnit.YARDS));

        // More examples
        Quantity a = new Quantity(36.0, LengthUnit.INCHES);
        Quantity b = new Quantity(1.0, LengthUnit.YARDS);

        System.out.println("36 inches == 1 yard: " + a.equals(b));
        System.out.println("36 inches + 1 yard (feet): " +
                Quantity.add(a, b, LengthUnit.FEET));

        Quantity c = new Quantity(2.54, LengthUnit.CENTIMETERS);
        Quantity d = new Quantity(1.0, LengthUnit.INCHES);

        System.out.println("2.54 cm → inches: " + c.convertTo(LengthUnit.INCHES));
        System.out.println("2.54 cm + 1 inch (cm): " +
                Quantity.add(c, d, LengthUnit.CENTIMETERS));

        System.out.println("\nProgram completed.");
    }
}