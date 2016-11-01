package donnees;

import principal.LanceurRayons;

public class Triangle extends GeometricObject {
    private Point point1;
    private Point point2;
    private Point point3;

    public Triangle(Point point1, Point point2, Point point3, Material diffuse, ColorRGB specular, int shininess,
            Matrice4x4 m) {
        super(diffuse, specular, shininess, m);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        
        if (getM() != null) {
            this.point1 = getM().mult(new Vec4(point1)).parsePoint();
            this.point2 = getM().mult(new Vec4(point2)).parsePoint();
            this.point3 = getM().mult(new Vec4(point3)).parsePoint();
        }
    }

    @Override
    public double getIntersection(Vec3 d, Point origin) {
        double a, b, t;
        Point p;
        Vec3 n;
        
        // Nous n'avons pas besoin du point d'intersection pour calculer la
        // normale d'un triangle.
        n = this.getNormale(null);

        b = d.dot(n);

        if (b >= 0.0d && b <= EPSILON)
            return -1;

        a = (point1.sub(origin)).dot(n);
        t = a / b;
        p = LanceurRayons.getIntersectionPoint(d, origin, t);

        if (((point2.sub(point1)).cross(p.sub(point1))).dot(n) < 0.0d)
            return -1;
        if (((point3.sub(point2)).cross(p.sub(point2))).dot(n) < 0.0d)
            return -1;
        if (((point1.sub(point3)).cross(p.sub(point3))).dot(n) < 0.0d)
            return -1;

        return t < 0.0d ? -1 : t;
    }

    @Override
    public Vec3 getNormale(Point pointIntersection) {
        Vec3 n = (point2.sub(point1)).cross(point3.sub(point1));
        return n.norm();
    }
}
