package donnees;

public class Point extends Triplet {

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    public Point add(Vec3 v2) {
        return new Point(this.getX() + v2.getX(), this.getY() + v2.getY(), this.getZ() + v2.getZ());
    }

    public Vec3 sub(Point point) {
        return new Vec3(this.getX() - point.getX(), this.getY() - point.getY(), this.getZ() - point.getZ());
    }

    public Point mul(double d) {
        return new Point(this.getX() * d, this.getY() * d, this.getZ() * d);
    }

    public String toString() {
        return "(" + getX() + " " + getY() + " " + getZ() + ")";
    }

}
