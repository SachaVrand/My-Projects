package donnees;

import principal.LanceurRayons;

public class Intersection {

    private Point intersectionPoint;
    private Point o;
    private Vec3 d;
    private double t;
    private GeometricObject intersectedObject;

    public Intersection(GeometricObject intersectedObject, Point o, Vec3 d, double t) {
        this.o = o;
        this.d = d;
        this.t = t;
        this.intersectedObject = intersectedObject;
        this.intersectionPoint = LanceurRayons.getIntersectionPoint(d, o, t);
    }

    public GeometricObject getIntersectedObject() {
        return intersectedObject;
    }

    public Point getIntersectionPoint() {
        return intersectionPoint;
    }

    public Vec3 getD() {
        return d;
    }

    public double getT() {
        return t;
    }

    public Point getO() {
        return o;
    }

}
