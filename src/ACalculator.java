import java.util.Scanner;

public class ACalculator {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        // -- Variables
        int profile = 0;
        
        double rootChord = 0.0;
        double tipChord = 0.0;
        
        double camMax = 0.0;
        double camPosition = 0.0;
        double maxThickness = 0.0;
        double wingspan = 0.0;

        String calculateLift;

        // -------------------------------------------------------------

        // Asks for NACA code and chord length
        System.out.println("Enter a wing profile (NACA 4-digit code): ");
        profile = scanner.nextInt();

        System.out.println("Enter the root chord length: ");
        rootChord = scanner.nextDouble();

        System.out.println("Enter the tip chord length: ");
        tipChord = scanner.nextDouble();

        System.out.println("Enter the wing span: ");
        wingspan = scanner.nextDouble();

        // Check for the valid length
        System.out.println();
        if (String.valueOf(profile).length() == 4) {

            // -- Gets values from NACA code
            camMax = (profile / 1000) / 100.0 * rootChord;
            camPosition = ((profile / 100) % 10) / 10.0 * rootChord;
            maxThickness = (profile % 100) / 100.0;
            
            // -- Displays NACA parameters
            System.out.println("Wing profile: NACA-" + profile);
            System.out.println("Maximum camber: " + (float) camMax);
            System.out.println("Camber position: " + (float)camPosition);
            System.out.println("Maximum thickness: " + (float) maxThickness * rootChord);
        } 
        else {
            System.out.println("Invalid NACA code. Please enter a 4-digit code.");
        }

        // -- Gives a wing area
        System.out.println();
        System.out.println("Wing area (one/ both wings): " +  (float) getArea(rootChord, tipChord, wingspan) + " / " +  (float) getArea(rootChord, tipChord, wingspan) * 2);
        System.out.println("Wing volume (one/ both wings): " +  (float) getVolume(maxThickness, rootChord, tipChord, wingspan) + " / " +  (float) getVolume(maxThickness, rootChord, tipChord, wingspan) * 2);

        System.out.println();
        System.out.println("Do you want to calculate lift force? (Yes / No): ");
        calculateLift = scanner.next();

        // Calculates lift force if the user wants to
        if (calculateLift.equalsIgnoreCase("Yes")) {

            System.out.println("Enter supposed hight of flight (m): ");
            double height = scanner.nextDouble();
            double airDensity = getAirDensity(height);

            System.out.println("Enter velocity (m/s): ");
            double velocity = scanner.nextDouble();

            System.out.println("Enter mass of an aircraft (kg): ");
            double mass = scanner.nextDouble();

            double liftCoefficient = mass * 9.81 / (airDensity * Math.pow(velocity, 2) * getArea(rootChord, tipChord, wingspan)); // Minimum lift coefficient for level flight

            double isStall = Math.sqrt((mass * 9.81) / (airDensity * getArea(rootChord, tipChord, wingspan)) * liftCoefficient); // Minimum velocity before stall

            //System.out.println(airDensity);
            //System.out.println(velocity);
            //System.out.println(liftCoefficient);
            //System.out.println(isStall);

            if (isStall >= velocity) {
                System.out.println("Warning: The aircraft is at risk of stalling.");
                System.out.println("Minimum appropriate aircraft velocity: " + (float) isStall + " m/s");
            }
            else {
                liftCoefficient = .7;
                double liftForce = 0.5 * airDensity * Math.pow(velocity, 2) * getArea(rootChord, tipChord, wingspan) * liftCoefficient;
                System.out.println("Lift force: " + (float) (liftForce / 1000) + " kN");
            }
        }
        scanner.close();
    }
    

    // Calculates the wing area using integration
    public static double getArea(double rootChord, double tipChord, double wingSpan) {
        int step = 10000;
        double area = 0.0;
        
        // Creates step for new defferenatial
        double delta = (wingSpan / 2.0) / step;

        // Uses previous step to find chord on each interval
        for (int i = 0; i < step; i++) {
            double change = i * delta;
            double y = rootChord + (tipChord - rootChord) * (change / (wingSpan / 2.0));

            // Calculates the area for each interval
            area += y * delta;
        }
        return area;
    }

    public static double getVolume(double maxThickness, double rootChord, double tipChord, double wingSpan) {
        int step = 10000;
        double volume = 0.0;
        
        // Creates step for new defferenatial
        double delta = (wingSpan / 2.0) / step;

        // Uses previous step to find chord on each interval
        for (int i = 0; i < step; i++) {
            double change = i * delta;
            double chordLength = rootChord + (tipChord - rootChord) * (change / (wingSpan / 2.0));

            // Calculates the area for each interval
            volume += getAreaOfProfile(maxThickness, chordLength) * delta;
        }
        return volume * 2;
    }

    // Area = thickness distribution * c(y)
    public static double getAreaOfProfile(double maxThickness, double chord) {
        double thicknessDist = 10 * maxThickness * (0.2969 * (2.0 /3.0) - 0.1260 * (1.0/2.0) - 0.3516 * (1.0/3.0) + 0.2843 * (1.0/4.0) - 0.1015 * (1.0/5.0));
        return  Math.pow(chord, 2) * thicknessDist;
    }

    // Calculates the air density at a given altitude using the barometric formula
    public static double getAirDensity(double hight) {
        double seaLevelTemp = 288.15; // Kelvin
        double lapseRate = 0.0065; // K/m
        double seaLevelPressure = 101325; // Pa
        double R = 287.05; // J/(kg*K)

        double temp = seaLevelTemp - lapseRate * hight; // Temperature at given height
        double pressure = seaLevelPressure * Math.pow(1 - lapseRate * hight / seaLevelTemp, 5.2561); // Pressure at given height

        return pressure / (R * temp); // Air density at given height
    }

    public static double getCdi(double aspectRatio, double liftCoefficient) {
        return Math.pow(liftCoefficient, 2) / ((Math.PI * 1.78 * (1 - .045 * Math.pow(aspectRatio, .68)) - .64) * aspectRatio); // Induced drag coefficient
    }

    public static double getCd0(double wingSpan, double area) {
        
        return 0.0;
    }
}