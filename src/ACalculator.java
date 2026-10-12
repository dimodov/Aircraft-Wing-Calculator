public class ACalculator {

    // Gives the maximum camber
    public static double getMaxCamber(int profile, double rootChord) {
        return (profile / 1000) / 100.0 * rootChord;
    }

    // Gives the camber position
    public static double getCamberPosition(int profile, double rootChord) {
        return ((profile / 100) % 10) / 10.0 * rootChord;
    }

    // Gives the maximum thickness in percentage of the chord length
    public static double getMaximumThickness(int profile, double rootChord) {
        return (profile % 100) / 100.0;
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