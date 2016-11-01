package donnees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SamplingRandom extends Sampling {

    private static final Random RANDOM = new Random();

    public SamplingRandom(int numberOfPoints) {
        super(numberOfPoints);
    }

    @Override
    public List<Point> getListePoints() {
        ArrayList<Point> res = new ArrayList<>();
        double x, y;
        for (int i = 0; i < numberOfPoints; i++) {
            x = RANDOM.nextDouble();
            y = RANDOM.nextDouble();
            res.add(new Point(x, y, 0));
        }
        return res;
    }

}
