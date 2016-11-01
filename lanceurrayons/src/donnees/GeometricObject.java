package donnees;

public abstract class GeometricObject {
    private Material diffuse;
    private ColorRGB specular;
    private int shininess;
    private Matrice4x4 m;

    public static final double EPSILON = 0.00001d;

    public GeometricObject(Material diffuse, ColorRGB specular, int shininess, Matrice4x4 m) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
        if (m != null && m.isIdentite())
            this.m = null;
        else
            this.m = m;
    }

    public ColorRGB getDiffuse(Point p) {
        return diffuse.getColor(p);
    }

    public int getShininess() {
        return shininess;
    }

    public ColorRGB getSpecular() {
        return specular;
    }

    public Matrice4x4 getM() {
        return m;
    }

    public abstract double getIntersection(Vec3 d, Point origin);

    public abstract Vec3 getNormale(Point pointIntersection);
}
