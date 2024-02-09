package dev.diamond.ddvorigins.util;

public class DDVOMath {

    public static boolean approxEquals(float a, float b, float tolerance) {
        return (a > b - tolerance) && (a < b + tolerance);
    }

}
