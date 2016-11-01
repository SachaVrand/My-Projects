package donnees;

public abstract class Lumiere {
    protected ColorRGB couleur;

    public Lumiere(double r, double g, double b) {
        this.couleur = new ColorRGB(r, g, b);
    }

    public ColorRGB getCouleur() {
        return couleur;
    }

    public abstract Vec3 getLightDirection(Point p);

    public abstract double getLightDistance(Point p);

    public ColorRGB getMatteContribution(Vec3 n, Point p) {
        double tmp = n.dot(getLightDirection(p));
        double max = (tmp > 0) ? tmp : 0;
        return couleur.mul(max);
    }

    public Vec3 getVecH(Point p, Vec3 d) {
        Vec3 ldir = getLightDirection(p);
        return (ldir.add(d.opposite())).norm();
    }

    public ColorRGB getShininessContribution(Vec3 n, Point p, Vec3 d, int shininess) {
        double tmp = n.dot(getVecH(p, d));
        double max = tmp > 0 ? tmp : 0;
        return couleur.mul(Math.pow(max, shininess));
    }

}
