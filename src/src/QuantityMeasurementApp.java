// Version 4.0
//Eshan Pankaj Joshi
//UC1: Feet measurement equality
//UC2: Feet and Inches measurement equality
//UC3: Generic Quantity Class for DRY Principle
//UC4: Extended Unit Support

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

    // ---------------- UC3 + UC4 ----------------

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0), // 1 yard = 3 feet
        CENTIMETERS(0.0328084); // 1 cm = 0.0328084 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

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

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        System.out.println("=== Quantity Measurement App ===");

        // UC1
        System.out.println("\n[UC1] Feet:");
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("1 ft vs 1 ft: " + f1.equals(f2));

        // UC2
        System.out.println("\n[UC2] Inches:");
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        System.out.println("1 inch vs 1 inch: " + i1.equals(i2));

        // UC3
        System.out.println("\n[UC3] Feet ↔ Inches:");
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("1 ft vs 12 inch: " + q1.equals(q2));

        // UC4 - Yards
        System.out.println("\n[UC4] Yards:");
        Quantity y1 = new Quantity(1.0, LengthUnit.YARDS);
        Quantity y2 = new Quantity(3.0, LengthUnit.FEET);
        System.out.println("1 yard vs 3 ft: " + y1.equals(y2));

        Quantity y3 = new Quantity(36.0, LengthUnit.INCH);
        System.out.println("1 yard vs 36 inch: " + y1.equals(y3));

        // UC4 - Centimeters
        System.out.println("\n[UC4] Centimeters:");
        Quantity c1 = new Quantity(1.0, LengthUnit.CENTIMETERS);
        Quantity c2 = new Quantity(0.393701, LengthUnit.INCH);
        System.out.println("1 cm vs 0.393701 inch: " + c1.equals(c2));

        // Mixed check
        System.out.println("\nMixed Comparison:");
        Quantity mix1 = new Quantity(2.0, LengthUnit.YARDS);
        Quantity mix2 = new Quantity(6.0, LengthUnit.FEET);
        System.out.println("2 yard vs 6 ft: " + mix1.equals(mix2));

        System.out.println("\nProgram completed.");
    }
}