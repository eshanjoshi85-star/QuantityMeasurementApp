// Version 2.0
//Eshan Pankaj Joshi
//UC1: Feet measurement equality
//UC2: Feet and Inches measurement equality
public class QuantityMeasurementApp {

    // Feet class
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

    // Inches class
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

    public static void main(String[] args) {

        // Welcome message
        System.out.println("=== Quantity Measurement App ===");

        // ---------------- UC1: Feet Equality ----------------
        System.out.println("\nChecking Feet Equality...");

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Comparing 1.0 ft and 1.0 ft");
        System.out.println("Result: " + f1.equals(f2));

        // ---------------- UC2: Inches Equality ----------------
        System.out.println("\nChecking Inches Equality...");

        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);

        System.out.println("Comparing 1.0 inch and 1.0 inch");
        System.out.println("Result: " + i1.equals(i2));

        // ---------------- Additional Checks ----------------

        System.out.println("\nChecking different values...");
        Feet f3 = new Feet(2.0);
        System.out.println("1.0 ft vs 2.0 ft: " + f1.equals(f3));

        Inches i3 = new Inches(2.0);
        System.out.println("1.0 inch vs 2.0 inch: " + i1.equals(i3));

        System.out.println("\nChecking null comparison...");
        System.out.println("Feet vs null: " + f1.equals(null));
        System.out.println("Inches vs null: " + i1.equals(null));

        System.out.println("\nProgram completed.");
    }
}