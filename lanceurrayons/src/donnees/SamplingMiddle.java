package donnees;

import java.util.List;

public class SamplingMiddle extends Sampling {

    private static final Point MIDDLEPOINT = new Point(0.5, 0.5, 0);

    public SamplingMiddle(int numberOfPoints) {
        super(numberOfPoints);
        super.listePoints.add(MIDDLEPOINT);
    }

    @Override
    public List<Point> getListePoints() {
        return super.listePoints;
    }

}
