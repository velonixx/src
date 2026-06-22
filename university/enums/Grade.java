package university.enums;

public enum Grade {
    G1(1.0), G2(2.0), G3(3.0), G4(4.0), G5(5.0), G6(6.0),
    G7(7.0), G8(8.0), G9(9.0), G10(10.0), G11(11.0), G12(12.0),
    NA(0.0); 

    private final double points;

    Grade(double points) {
        this.points = points;
    }

    public double getPoints() {
        return points;
    }

    public static Grade fromInteger(int value) {
        if (value >= 1 && value <= 12) {
            return Grade.valueOf("G" + value);
        }
        return NA;
    }
}