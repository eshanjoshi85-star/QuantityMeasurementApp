// Version 10.0
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
    // UC8: Length Unit Enum (Conversion Responsibility)
    // =========================================================
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

    // =========================================================
    // UC3 → UC8: Quantity (Length)
    // =========================================================
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
            double base = this.toBaseUnit();
            double converted = target.convertFromBaseUnit(base);
            return new Quantity(converted, target);
        }

        private static Quantity addInternal(Quantity q1, Quantity q2, LengthUnit target) {
            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = target.convertFromBaseUnit(sumBase);
            return new Quantity(result, target);
        }

        public Quantity add(Quantity other) {
            return addInternal(this, other, this.unit);
        }

        public static Quantity add(Quantity q1, Quantity q2, LengthUnit target) {
            return addInternal(q1, q2, target);
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

    // =========================================================
    // UC10: IMeasurable Interface
    // =========================================================
    interface IMeasurable {
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    // =========================================================
    // UC10: Refactored LengthUnit
    // =========================================================
    enum LengthUnitV2 implements IMeasurable {
        FEET(1.0),
        INCHES(1.0 / 12),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnitV2(double factor) {
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
    // UC10: Refactored WeightUnit
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
    // UC10: Generic Quantity Class
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

        private static <U extends IMeasurable> QuantityV2<U> addInternal(
                QuantityV2<U> q1, QuantityV2<U> q2, U target) {

            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = target.convertFromBaseUnit(sumBase);

            return new QuantityV2<>(result, target);
        }

        public QuantityV2<U> add(QuantityV2<U> other) {
            return addInternal(this, other, this.unit);
        }

        public static <U extends IMeasurable> QuantityV2<U> add(
                QuantityV2<U> q1, QuantityV2<U> q2, U target) {
            return addInternal(q1, q2, target);
        }

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
    // MAIN METHOD (UC DEMO)
    // =========================================================
    public static void main(String[] args) {

        System.out.println("========== UC1 ==========");
        Feet f1 = new Feet(5.0);
        Feet f2 = new Feet(5.0);
        System.out.println(f1.equals(f2));

        System.out.println("\n========== UC2 ==========");
        Inches i1 = new Inches(12.0);
        System.out.println(f1.equals(i1));

        System.out.println("\n========== UC3–UC8 LENGTH ==========");
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCHES);

        System.out.println("Equality: " + q1.equals(q2));
        System.out.println("Conversion: " + q1.convertTo(LengthUnit.INCHES));
        System.out.println("Addition: " + q1.add(q2));

        System.out.println("\n========== UC10 WEIGHT ==========");
        QuantityV2<WeightUnit> w1 = new QuantityV2<>(1.0, WeightUnit.KILOGRAM);
        QuantityV2<WeightUnit> w2 = new QuantityV2<>(1000.0, WeightUnit.GRAM);

        System.out.println("Equality: " + w1.equals(w2));
        System.out.println("Conversion: " + w1.convertTo(WeightUnit.GRAM));
        System.out.println("Addition: " + QuantityV2.add(w1, w2, WeightUnit.KILOGRAM));

        System.out.println("\nProgram completed.");
    }
}}