// Version 3.0
//Eshan Pankaj Joshi
//UC1: Feet measurement equality
//UC2: Feet and Inches measurement equality
//UC3: Generic Quantity Class for DRY Principle
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

    // ---------------- UC3 ----------------

    // Enum for unit conversion
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Generic Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        System.out.println("=== Quantity Measurement App ===");

        // -------- UC1: Feet --------
        System.out.println("\n[UC1] Feet Equality:");
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("1.0 ft vs 1.0 ft: " + f1.equals(f2));

        // -------- UC2: Inches --------
        System.out.println("\n[UC2] Inches Equality:");
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        System.out.println("1.0 inch vs 1.0 inch: " + i1.equals(i2));

        // -------- UC3: Cross Unit --------
        System.out.println("\n[UC3] Cross Unit Equality:");

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println("1.0 ft vs 12.0 inch: " + q1.equals(q2));

        // Same unit (UC3)
        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("1.0 inch vs 1.0 inch: " + q3.equals(q4));

        // Different values
        Quantity q5 = new Quantity(2.0, LengthUnit.FEET);
        System.out.println("1.0 ft vs 2.0 ft: " + q1.equals(q5));

        System.out.println("\nProgram completed.");
    }
}}