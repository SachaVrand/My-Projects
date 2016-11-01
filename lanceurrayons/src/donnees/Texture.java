package donnees;

public class Texture implements Material {

    private ColorRGB color1;
    private ColorRGB color2;
    private double length;

    public Texture(ColorRGB color1, ColorRGB color2, double length) {
        this.color1 = color1;
        this.color2 = color2;
        this.length = length;
    }

    @Override
    public ColorRGB getColor(Point p) {

        ColorRGB res1 = color2, res2 = color1;
        double x, z;
        x = Math.abs(p.getX() % (2.0d * length));
        z = Math.abs(p.getZ() % (2.0d * length));

        if (p.getX() > 0 && p.getZ() > 0 || p.getX() < 0 && p.getZ() < 0) {
            res1 = color1;
            res2 = color2;
        }

        if ((x < length && z < length) || (x > length && z > length)) {
            return res1;
        } else {
            return res2;
        }
    }

}
