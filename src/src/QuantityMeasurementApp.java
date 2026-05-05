// Version 12.0
// Eshan Pankaj Joshi
// UC1: Feet measurement equality
// UC2: Feet and Inches measurement equality
// UC3: Generic Quantity Class for DRY Principle
// UC4: Extended Unit Support
// UC5: Unit-to-Unit Conversion
// UC6: Addition
// UC7: Addition with Target Unit Specification
// UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility
// UC9: Weight Measurement Support
// UC10: Generic Quantity Class with Unit Interface for Multi-Category Support
// UC11: Volume Measurement Support
// UC12: Subtraction and Division Operations

public class QuantityMeasurementApp {

    // =========================================================
    // UC1: Feet measurement equality
    // =========================================================
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

    // =========================================================
    // UC2: Inches measurement equality
    // =========================================================
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

    // =========================================================
    // UC10: IMeasurable Interface
    // =========================================================
    interface IMeasurable {
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    // =========================================================
    // UC10: Length Unit
    // =========================================================
    enum LengthUnit implements IMeasurable {
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

        public String getUnitName() {
            return name();
        }
    }

    // =========================================================
    // UC10: Weight Unit
    // =========================================================
    enum WeightUnit implements IMeasurable {
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

        public String getUnitName() {
            return name();
        }
    }

    // =========================================================
    // UC11: Volume Unit
    // =========================================================
    enum VolumeUnit implements IMeasurable {
        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    // =========================================================
    // UC10–UC12 Generic Quantity Class
    // =========================================================
    static class QuantityV2<U extends IMeasurable> {

        private final double value;
        private final U unit;

        public QuantityV2(double value, U unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid Quantity");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.convertToBaseUnit(value);
        }

        public QuantityV2<U> convertTo(U target) {
            double base = toBaseUnit();
            double converted = target.convertFromBaseUnit(base);
            return new QuantityV2<>(converted, target);
        }

        // =========================================================
        // UC6–UC7 ADDITION
        // =========================================================
        private static <U extends IMeasurable> QuantityV2<U> addInternal(
                QuantityV2<U> q1, QuantityV2<U> q2, U target) {

            double sum = q1.toBaseUnit() + q2.toBaseUnit();
            return new QuantityV2<>(target.convertFromBaseUnit(sum), target);
        }

        public QuantityV2<U> add(QuantityV2<U> other) {
            return addInternal(this, other, this.unit);
        }

        public static <U extends IMeasurable> QuantityV2<U> add(
                QuantityV2<U> q1, QuantityV2<U> q2, U target) {
            return addInternal(q1, q2, target);
        }

        // =========================================================
        // UC12 SUBTRACTION
        // =========================================================
        public QuantityV2<U> subtract(QuantityV2<U> other) {
            double result = this.toBaseUnit() - other.toBaseUnit();
            return new QuantityV2<>(this.unit.convertFromBaseUnit(result), this.unit);
        }

        public QuantityV2<U> subtract(QuantityV2<U> other, U target) {
            double result = this.toBaseUnit() - other.toBaseUnit();
            return new QuantityV2<>(target.convertFromBaseUnit(result), target);
        }

        // =========================================================
        // UC12 DIVISION
        // =========================================================
        public double divide(QuantityV2<U> other) {
            if (other.toBaseUnit() == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return this.toBaseUnit() / other.toBaseUnit();
        }

        // =========================================================
        // EQUALITY
        // =========================================================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityV2)) return false;
            QuantityV2<?> other = (QuantityV2<?>) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    // =========================================================
    // MAIN METHOD (UC1–UC12 DEMO)
    // =========================================================
    public static void main(String[] args) {

        // UC1
        System.out.println("UC1");
        Feet f1 = new Feet(5);
        Feet f2 = new Feet(5);
        System.out.println(f1.equals(f2));

        // UC2
        System.out.println("\nUC2");
        Inches i1 = new Inches(60);
        System.out.println(f1.equals(i1));

        // UC6–UC7 LENGTH
        System.out.println("\nUC6–UC7");
        QuantityV2<LengthUnit> q1 = new QuantityV2<>(1, LengthUnit.FEET);
        QuantityV2<LengthUnit> q2 = new QuantityV2<>(12, LengthUnit.INCHES);

        System.out.println("Equality: " + q1.equals(q2));
        System.out.println("Conversion: " + q1.convertTo(LengthUnit.INCHES));
        System.out.println("Addition: " + q1.add(q2));

        // UC9 WEIGHT
        System.out.println("\nUC9");
        QuantityV2<WeightUnit> w1 = new QuantityV2<>(1, WeightUnit.KILOGRAM);
        QuantityV2<WeightUnit> w2 = new QuantityV2<>(1000, WeightUnit.GRAM);
        System.out.println("Equality: " + w1.equals(w2));

        // UC11 VOLUME
        System.out.println("\nUC11");
        QuantityV2<VolumeUnit> v1 = new QuantityV2<>(1, VolumeUnit.LITRE);
        QuantityV2<VolumeUnit> v2 = new QuantityV2<>(1000, VolumeUnit.MILLILITRE);
        System.out.println("Equality: " + v1.equals(v2));

        // =========================================================
        // UC12 SUBTRACTION
        // =========================================================
        System.out.println("\nUC12 SUBTRACTION");
        System.out.println(q1.subtract(q2));
        System.out.println(q1.subtract(q2, LengthUnit.INCHES));

        // =========================================================
        // UC12 DIVISION
        // =========================================================
        System.out.println("\nUC12 DIVISION");
        System.out.println(q1.divide(q2));

        System.out.println("\nProgram Completed");
    }
}