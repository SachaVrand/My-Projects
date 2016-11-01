package donnees;

public class Plane extends GeometricObject {
    private Point passePar;
    private Vec3 laNormale;

    public Plane(Point passePar, Vec3 laNormale, Material diffuse, ColorRGB specular, int shininess) {
        super(diffuse, specular, shininess, null);
        this.passePar = passePar;
        this.laNormale = laNormale.norm();
    }

    @Override
    public double getIntersection(Vec3 d, Point origin) {
        double a, b;

        b = d.dot(laNormale);

        if (b >= 0.0d && b <= EPSILON)
            return -1;

        a = (passePar.sub(origin)).dot(laNormale);
        return a / b < 0.0d ? -1 : a / b;
    }

    @Override
    public Vec3 getNormale(Point pointIntersection) {
        return laNormale;
    }
}
