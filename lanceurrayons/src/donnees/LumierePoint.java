package donnees;

public class LumierePoint extends Lumiere {
    private Point point;

    public LumierePoint(double x, double y, double z, double r, double g, double b) {
        super(r, g, b);
        this.point = new Point(x, y, z);
    }

    @Override
    public Vec3 getLightDirection(Point p) {
        return (this.point.sub(p)).norm();
    }

    @Override
    public double getLightDistance(Point p) {
        return (point.sub(p)).length();
    }
}
