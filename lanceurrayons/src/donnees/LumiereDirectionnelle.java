package donnees;

public class LumiereDirectionnelle extends Lumiere {
    private Vec3 direction;

    public LumiereDirectionnelle(double x, double y, double z, double r, double g, double b) {
        super(r, g, b);
        this.direction = new Vec3(x, y, z);
    }

    @Override
    public Vec3 getLightDirection(Point p) {
        return direction.norm();
    }

    @Override
    public double getLightDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
