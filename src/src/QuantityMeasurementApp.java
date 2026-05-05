// Version 13.0
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
// UC13: Centralized Arithmetic Logic (DRY Refactor)

public class QuantityMeasurementApp {

    // =========================================================
    // UC1: Feet
    // =========================================================
    static class Feet {
        private final double value;
        public Feet(double value) { this.value = value; }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Feet)) return false;
            return Double.compare(value, ((Feet)obj).value) == 0;
        }
    }

    // =========================================================
    // UC2: Inches
    // =========================================================
    static class Inches {
        private final double value;
        public Inches(double value) { this.value = value; }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Inches)) return false;
            return Double.compare(value, ((Inches)obj).value) == 0;
        }
    }

    // =========================================================
    // UC10: IMeasurable
    // =========================================================
    interface IMeasurable {
        double toBase(double v);
        double fromBase(double v);
        String name();
    }

    // =========================================================
    // Units
    // =========================================================
    enum LengthUnit implements IMeasurable {
        FEET(1), INCHES(1.0/12), CM(1.0/30.48);

        double f;
        LengthUnit(double f){this.f=f;}

        public double toBase(double v){return v*f;}
        public double fromBase(double v){return v/f;}
        public String name(){return this.name();}
    }

    enum WeightUnit implements IMeasurable {
        KG(1), G(0.001);

        double f;
        WeightUnit(double f){this.f=f;}

        public double toBase(double v){return v*f;}
        public double fromBase(double v){return v/f;}
        public String name(){return this.name();}
    }

    enum VolumeUnit implements IMeasurable {
        L(1), ML(0.001);

        double f;
        VolumeUnit(double f){this.f=f;}

        public double toBase(double v){return v*f;}
        public double fromBase(double v){return v/f;}
        public String name(){return this.name();}
    }

    // =========================================================
    // UC13: CENTRALIZED ARITHMETIC LOGIC (DRY)
    // =========================================================
    enum Op { ADD, SUB, DIV }

    static class QuantityV2<U extends IMeasurable> {

        private final double value;
        private final U unit;

        public QuantityV2(double v, U u){
            if(u==null || Double.isNaN(v)) throw new IllegalArgumentException();
            value=v; unit=u;
        }

        private double base(){ return unit.toBase(value); }

        // =====================================================
        // UC13 CENTRAL HELPER (ONLY PLACE WITH LOGIC)
        // =====================================================
        private static <U extends IMeasurable> double compute(
                QuantityV2<U> a,
                QuantityV2<U> b,
                Op op) {

            double x = a.base();
            double y = b.base();

            switch(op){
                case ADD: return x + y;
                case SUB: return x - y;
                case DIV:
                    if(y==0) throw new ArithmeticException();
                    return x / y;
            }
            return 0;
        }

        // =====================================================
        // ADD
        // =====================================================
        public QuantityV2<U> add(QuantityV2<U> o){
            double r = compute(this,o,Op.ADD);
            return new QuantityV2<>(unit.fromBase(r), unit);
        }

        public static <U extends IMeasurable> QuantityV2<U> add(
                QuantityV2<U> a, QuantityV2<U> b, U target){
            double r = compute(a,b,Op.ADD);
            return new QuantityV2<>(target.fromBase(r), target);
        }

        // =====================================================
        // SUB
        // =====================================================
        public QuantityV2<U> subtract(QuantityV2<U> o){
            double r = compute(this,o,Op.SUB);
            return new QuantityV2<>(unit.fromBase(r), unit);
        }

        public QuantityV2<U> subtract(QuantityV2<U> o, U target){
            double r = compute(this,o,Op.SUB);
            return new QuantityV2<>(target.fromBase(r), target);
        }

        // =====================================================
        // DIV (scalar)
        // =====================================================
        public double divide(QuantityV2<U> o){
            return compute(this,o,Op.DIV);
        }

        // =====================================================
        // EQUALITY
        // =====================================================
        public boolean equals(Object obj){
            if(!(obj instanceof QuantityV2)) return false;
            return Double.compare(base(), ((QuantityV2<?>)obj).base())==0;
        }

        public String toString(){
            return "Quantity("+value+","+unit+")";
        }
    }

    // =========================================================
    // MAIN DEMO (ALL USE CASES)
    // =========================================================
    public static void main(String[] args) {

        System.out.println("UC1/2");
        Feet f = new Feet(5);
        Inches i = new Inches(60);
        System.out.println(f.equals(f));

        System.out.println("\nUC6/7");
        QuantityV2<LengthUnit> a = new QuantityV2<>(1, LengthUnit.FEET);
        QuantityV2<LengthUnit> b = new QuantityV2<>(12, LengthUnit.INCHES);

        System.out.println(a.equals(b));
        System.out.println(a.add(b));
        System.out.println(a.subtract(b));

        System.out.println("\nUC9");
        QuantityV2<WeightUnit> w1 = new QuantityV2<>(1, WeightUnit.KG);
        QuantityV2<WeightUnit> w2 = new QuantityV2<>(1000, WeightUnit.G);

        System.out.println(w1.equals(w2));

        System.out.println("\nUC11");
        QuantityV2<VolumeUnit> v1 = new QuantityV2<>(1, VolumeUnit.L);
        QuantityV2<VolumeUnit> v2 = new QuantityV2<>(1000, VolumeUnit.ML);

        System.out.println(v1.equals(v2));

        System.out.println("\nUC12 SUB");
        System.out.println(a.subtract(b));

        System.out.println("\nUC12 DIV");
        System.out.println(a.divide(b));

        System.out.println("\nUC13 CENTRALIZED LOGIC WORKING");
        System.out.println("Add: " + a.add(b));
        System.out.println("Sub: " + a.subtract(b));
        System.out.println("Div: " + a.divide(b));
    }
}