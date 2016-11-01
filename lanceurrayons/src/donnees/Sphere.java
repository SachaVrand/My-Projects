package donnees;

public class Sphere extends GeometricObject {

    private Point centre;
    private double rayon;

    public Sphere(Point centre, double rayon, Material diffuse, ColorRGB specular, int shininess, Matrice4x4 m) {
        super(diffuse, specular, shininess, m);
        this.centre = centre;
        this.rayon = rayon;
    }

    @Override
    public String toString() {
        return "centre : " + centre.toString() + ", rayon : " + rayon;
    }

    @Override
    public double getIntersection(Vec3 d, Point origin) {
        if (getM() != null) {
            Point invO = getM().inverser().mult(new Vec4(origin)).parsePoint();
            Vec3 invD = getM().inverser().mult(new Vec4(d)).parseVec3();
            double t = getOldIntersection(invD, invO);
            if (t < 0.0d)
                return -1;
            Point p = invO.add(invD.mul(t));
            Point pPrime = getM().mult(new Vec4(p)).parsePoint();
            if (d.getX() != 0)
                return pPrime.sub(origin).getX() / d.getX();
            else if (d.getY() != 0)
                return pPrime.sub(origin).getY() / d.getY();
            else
                return pPrime.sub(origin).getZ() / d.getZ();
        } else {
            return getOldIntersection(d, origin);
        }
    }

    private double getOldIntersection(Vec3 d, Point origin) {
        double a, b, c, disc;
        Vec3 originSubCenter = origin.sub(centre);

        a = d.dot(d);
        b = (originSubCenter.mul(2)).dot(d);
        c = originSubCenter.dot(originSubCenter) - (rayon * rayon);
        disc = b * b - 4 * a * c;

        if (disc < 0.0d) {
            return -1;
        } else if (disc <= EPSILON) {
            double tmp = (-b) / (2 * a);
            return tmp < 0 ? -1 : tmp;
        } else {
            double t1, t2;
            t1 = ((-b) + Math.sqrt(disc)) / (2 * a);
            t2 = ((-b) - Math.sqrt(disc)) / (2 * a);
            if (t2 > 0)
                return t2;
            else
                return t1 > 0 ? t1 : -1;
        }
    }

    @Override
    public Vec3 getNormale(Point pointIntersection) {
        Vec3 res;

        if (getM() != null) {
            // p
            Point p = getM().inverser().mult(new Vec4(pointIntersection)).parsePoint();
            // n
            res = p.sub(this.centre);
            // n'
            res = getM().inverser().transposer().mult(new Vec4(res)).parseVec3();
        } else {
            res = pointIntersection.sub(this.centre);
        }

        res = res.norm();

        return res;

    }
}
