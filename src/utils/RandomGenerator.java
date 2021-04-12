package utils;
import java.util.Random;

public class RandomGenerator {
    public static Random rand = new Random();

    public static int getInt(int bound){
        return rand.nextInt(bound);
    }
    public static double getDouble(double bound){
        return bound * rand.nextDouble();

    }
}
