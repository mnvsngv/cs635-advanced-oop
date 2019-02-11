package models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point anotherPoint) {
        this.x = anotherPoint.getX();
        this.y = anotherPoint.getY();
    }

    public Point() {
        this(0, 0);
    }

    /*
     * Values are truncated to 2 decimals to ensure precision.
     */
    public double getX() {
        return truncate(x);
    }

    public double getY() {
        return truncate(y);
    }


    /*
     * Truncate down to 2 decimals.
     */
    private double truncate(double doubleToTruncate) {
        return BigDecimal.valueOf(doubleToTruncate)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public boolean equals(Object anotherObject) {
        if(anotherObject instanceof Point) {
            Point anotherPoint = (Point) anotherObject;
            return ((getX() == anotherPoint.getX()) &&
                    (getY() == anotherPoint.getY()));
        } else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;

        long longX = Double.doubleToLongBits(x);
        result = (31 * result) + (int) ((int) longX ^ (longX >>> 32));

        long longY = Double.doubleToLongBits(y);
        result = (31 * result) + (int) ((int) longY ^ (longX >>> 32));

        return result;
    }
}
