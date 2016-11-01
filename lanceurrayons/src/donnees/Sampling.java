package donnees;

import java.util.ArrayList;
import java.util.List;

public abstract class Sampling {

    // DLB: ici utiliser List<Point>
    protected ArrayList<Point> listePoints;
    protected int numberOfPoints;

    public static final String TOKENMIDDLE = "middle";
    public static final String TOKENRANDOM = "random";
    public static final String TOKENGRID = "grid";

    public Sampling(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.listePoints = new ArrayList<>();
    }

    public abstract List<Point> getListePoints();

    public int getNumberOfPoints() {
        return listePoints.size();
    }

    // DLB: très bien la méthode fabrique.
    public static Sampling createSampling(String technique, int numberOfPoints) {
        if (technique.equals(TOKENMIDDLE)) {
            return new SamplingMiddle(numberOfPoints);
        } else if (technique.equals(TOKENGRID)) {
            return new SamplingGrid(numberOfPoints);
        } else if (technique.equals(TOKENRANDOM)) {
            return new SamplingRandom(numberOfPoints);
        } else {
            // DLB : lancer plutôt une exception IllegalArgumentException 
            return null;
        }
    }

}
