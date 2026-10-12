import java.util.Scanner;

public class GUI {
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

            // -- Displays NACA parameters
            System.out.println("Wing profile: NACA-" + profile);
            System.out.println("Maximum camber: " + (float) ACalculator.getMaxCamber(profile, rootChord));
            System.out.println("Camber position: " + (float) ACalculator.getCamberPosition(profile, rootChord));
            System.out.println("Maximum thickness: " + (float) ACalculator.getMaximumThickness(profile, rootChord) * rootChord);
        } 
        else {
            System.out.println("Invalid NACA code. Please enter a 4-digit code.");
        }

        // -- Gives a wing area
        System.out.println();
        System.out.println("Wing area (one/ both wings): " +  (float) ACalculator.getArea(rootChord, tipChord, wingspan) + " / " +  (float) ACalculator.getArea(rootChord, tipChord, wingspan) * 2);
        System.out.println("Wing volume (one/ both wings): " +  (float) ACalculator.getVolume(ACalculator.getMaximumThickness(profile, rootChord), rootChord, tipChord, wingspan) + " / " +  (float) ACalculator.getVolume(ACalculator.getMaximumThickness(profile, rootChord), rootChord, tipChord, wingspan) * 2);

        System.out.println();
        System.out.println("Do you want to calculate lift force? (Yes / No): ");
        calculateLift = scanner.next();

        // Calculates lift force if the user wants to
        if (calculateLift.equalsIgnoreCase("Yes")) {

            System.out.println("Enter supposed hight of flight (m): ");
            double height = scanner.nextDouble();
            double airDensity = ACalculator.getAirDensity(height);

            System.out.println("Enter velocity (m/s): ");
            double velocity = scanner.nextDouble();

            System.out.println("Enter mass of an aircraft (kg): ");
            double mass = scanner.nextDouble();

            double liftCoefficient = mass * 9.81 / (airDensity * Math.pow(velocity, 2) * ACalculator.getArea(rootChord, tipChord, wingspan)); // Minimum lift coefficient for level flight

            double isStall = Math.sqrt((mass * 9.81) / (airDensity * ACalculator.getArea(rootChord, tipChord, wingspan)) * liftCoefficient); // Minimum velocity before stall

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
                double liftForce = 0.5 * airDensity * Math.pow(velocity, 2) * ACalculator.getArea(rootChord, tipChord, wingspan) * liftCoefficient;
                System.out.println("Lift force: " + (float) (liftForce / 1000) + " kN");
            }
        }
        scanner.close();
    }
}
