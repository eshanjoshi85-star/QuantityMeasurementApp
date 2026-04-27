//Version 1.0
//Eshan Pankaj Joshi
//UC1: Feet measurement equality
    public class QuantityMeasurementApp {

        // Inner immutable class
        static class Feet {
            private final double value;

            // Constructor
            public Feet(double value) {
                this.value = value;
            }

            // Getter (optional, for debugging or extension)
            public double getValue() {
                return value;
            }

            // Override equals method
            @Override
            public boolean equals(Object obj) {
                // Same reference check (reflexive)
                if (this == obj) {
                    return true;
                }

                // Null or type check
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }

                // Safe cast
                Feet other = (Feet) obj;

                // Compare using Double.compare
                return Double.compare(this.value, other.value) == 0;
            }

            // Override hashCode (important when equals is overridden)
            @Override
            public int hashCode() {
                return Double.hashCode(value);
            }
        }

        // Main method to test
        public static void main(String[] args) {
            Feet feet1 = new Feet(1.0);
            Feet feet2 = new Feet(1.0);

            boolean result = feet1.equals(feet2);

            System.out.println("Are values equal? " + result);
        }
    }
}
