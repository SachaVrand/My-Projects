package donnees;

import java.util.List;

public class SamplingGrid extends Sampling {

    public SamplingGrid(int numberOfPoints) {
        super(numberOfPoints);
        initListePoint();
    }

    private void initListePoint() {
        double tmp = 1 / (double) (numberOfPoints + 1), x, y;
        int i, j;

        for (i = 0, x = tmp; i < numberOfPoints; i++, x += tmp) {
            for (j = 0, y = tmp; j < numberOfPoints; j++, y += tmp) {
                super.listePoints.add(new Point(x, y, 0));
            }
        }
    }

    @Override
    public List<Point> getListePoints() {
        return super.listePoints;
    }

}
