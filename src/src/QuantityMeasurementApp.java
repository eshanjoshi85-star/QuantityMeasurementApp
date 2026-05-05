// Version 9.0
//Eshan Pankaj Joshi
// UC1: Feet measurement equality
// UC2: Feet and Inches measurement equality
// UC3: Generic Quantity Class for DRY Principle
// UC4: Extended Unit Support
// UC5: Unit-to-Unit Conversion
// UC6: Addition
// UC7: Addition with Target Unit Specification
// UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility
// UC9: Weight Measurement Support

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

    // ---------------- UC8 ----------------
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
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

    // ---------------- UC9 ----------------
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }
    }

    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid Quantity");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.convertToBaseUnit(value);
        }

        public QuantityWeight convertTo(WeightUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = this.toBaseUnit();
            double converted = target.convertFromBaseUnit(base);

            return new QuantityWeight(converted, target);
        }

        private static QuantityWeight addInternal(QuantityWeight q1, QuantityWeight q2, WeightUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new QuantityWeight(result, targetUnit);
        }

        public QuantityWeight add(QuantityWeight other) {
            return addInternal(this, other, this.unit);
        }

        public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2, WeightUnit targetUnit) {
            return addInternal(q1, q2, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return "QuantityWeight(" + value + ", " + unit + ")";
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        System.out.println("=== Quantity Measurement App (UC9) ===");

        // Length
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCHES);
        System.out.println("1 ft == 12 inches: " + q1.equals(q2));
        System.out.println("1 ft → inches: " + q1.convertTo(LengthUnit.INCHES));
        System.out.println("1 ft + 12 inches (feet): " + q1.add(q2));
        System.out.println("1 ft + 12 inches (yards): " +
                Quantity.add(q1, q2, LengthUnit.YARDS));

        // Weight
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("1 kg == 1000 g: " + w1.equals(w2));
        System.out.println("1 kg → pound: " + w1.convertTo(WeightUnit.POUND));
        System.out.println("1 kg + 1000 g (kg): " + w1.add(w2));
        System.out.println("1 kg + 1000 g (pound): " +
                QuantityWeight.add(w1, w2, WeightUnit.POUND));

        System.out.println("\nProgram completed.");
    }
}