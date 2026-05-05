// Version 11.0
// Eshan Pankaj Joshi
// UC1: Feet measurement equality
// UC2: Feet and Inches measurement equality
// UC3: Generic Quantity Class for DRY Principle
// UC4: Extended Unit Support
// UC5: Unit-to-Unit Conversion
// UC6: Addition
// UC7: Addition with Target Unit Specification
// UC8: Refactoring Unit Enum
// UC9: Weight Measurement Support
// UC10: Generic Quantity Class with Unit Interface
// UC11: Volume Measurement Support

public class QuantityMeasurementApp {

    // =========================================================
    // UC1
    // =========================================================
    static class Feet {
        double value;
        Feet(double value) { this.value = value; }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Feet)) return false;
            return Double.compare(value, ((Feet) obj).value) == 0;
        }
    }

    // =========================================================
    // UC2
    // =========================================================
    static class Inches {
        double value;
        Inches(double value) { this.value = value; }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Inches)) return false;
            return Double.compare(value, ((Inches) obj).value) == 0;
        }
    }

    // =========================================================
    // UC8 Interface
    // =========================================================
    interface IMeasurable {
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double value);
        String getUnitName();
    }

    // =========================================================
    // UC8 Length Unit
    // =========================================================
    enum LengthUnit implements IMeasurable {
        FEET(1.0),
        INCHES(1.0 / 12),
        YARD(3.0),
        CM(1.0 / 30.48);

        double factor;
        LengthUnit(double f) { this.factor = f; }

        public double convertToBaseUnit(double v) { return v * factor; }
        public double convertFromBaseUnit(double v) { return v / factor; }
        public String getUnitName() { return name(); }
    }

    // =========================================================
    // UC10 Weight Unit
    // =========================================================
    enum WeightUnit implements IMeasurable {
        KG(1.0),
        GRAM(0.001),
        POUND(0.453592);

        double factor;
        WeightUnit(double f) { this.factor = f; }

        public double convertToBaseUnit(double v) { return v * factor; }
        public double convertFromBaseUnit(double v) { return v / factor; }
        public String getUnitName() { return name(); }
    }

    // =========================================================
    // UC11 Volume Unit
    // =========================================================
    enum VolumeUnit implements IMeasurable {
        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        double factor;
        VolumeUnit(double f) { this.factor = f; }

        public double convertToBaseUnit(double v) { return v * factor; }
        public double convertFromBaseUnit(double v) { return v / factor; }
        public String getUnitName() { return name(); }
    }

    // =========================================================
    // UC10 Generic Quantity
    // =========================================================
    static class Quantity<U extends IMeasurable> {
        double value;
        U unit;

        Quantity(double value, U unit) {
            this.value = value;
            this.unit = unit;
        }

        double base() {
            return unit.convertToBaseUnit(value);
        }

        public Quantity<U> convertTo(U target) {
            return new Quantity<>(target.convertFromBaseUnit(base()), target);
        }

        public Quantity<U> add(Quantity<U> other) {
            double sum = this.base() + other.base();
            return new Quantity<>(unit.convertFromBaseUnit(sum), unit);
        }

        public static <U extends IMeasurable> Quantity<U> add(
                Quantity<U> a, Quantity<U> b, U target) {
            double sum = a.base() + b.base();
            return new Quantity<>(target.convertFromBaseUnit(sum), target);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Quantity)) return false;
            return Double.compare(this.base(), ((Quantity<?>) obj).base()) == 0;
        }

        public String toString() {
            return value + " " + unit.getUnitName();
        }
    }

    // =========================================================
    // MAIN → ALL UC TESTS
    // =========================================================
    public static void main(String[] args) {

        // ================= UC1 =================
        System.out.println("UC1 Feet Equality");
        Feet f1 = new Feet(5);
        Feet f2 = new Feet(5);
        System.out.println(f1.equals(f2));

        // ================= UC2 =================
        System.out.println("\nUC2 Feet vs Inches");
        Inches i1 = new Inches(12);
        System.out.println(f1.equals(i1));

        // ================= UC3–UC6 =================
        System.out.println("\nUC3–UC6 Length Operations");
        Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12, LengthUnit.INCHES);

        System.out.println("Equality: " + l1.equals(l2));
        System.out.println("Conversion: " + l1.convertTo(LengthUnit.INCHES));
        System.out.println("Addition: " + l1.add(l2));

        // ================= UC7 =================
        System.out.println("\nUC7 Target Unit Addition");
        System.out.println(Quantity.add(l1, l2, LengthUnit.FEET));

        // ================= UC9 =================
        System.out.println("\nUC9 Weight");
        Quantity<WeightUnit> w1 = new Quantity<>(1, WeightUnit.KG);
        Quantity<WeightUnit> w2 = new Quantity<>(1000, WeightUnit.GRAM);

        System.out.println("Equality: " + w1.equals(w2));
        System.out.println("Conversion: " + w1.convertTo(WeightUnit.GRAM));
        System.out.println("Addition: " + w1.add(w2));

        // ================= UC11 =================
        System.out.println("\nUC11 Volume");

        Quantity<VolumeUnit> v1 = new Quantity<>(1, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1, VolumeUnit.GALLON);

        System.out.println("Equality L vs mL: " + v1.equals(v2));
        System.out.println("Convert L→mL: " + v1.convertTo(VolumeUnit.MILLILITRE));
        System.out.println("Convert G→L: " + v3.convertTo(VolumeUnit.LITRE));

        System.out.println("Add L + mL: " + v1.add(v2));
        System.out.println("Add L + G in L: " +
                Quantity.add(v1, v3, VolumeUnit.LITRE));

        // ================= CROSS CATEGORY =================
        System.out.println("\nCross Category Check");
        System.out.println(v1.equals(l1));
        System.out.println(v1.equals(w1));

        System.out.println("\nALL UCs EXECUTED SUCCESSFULLY");
    }
}