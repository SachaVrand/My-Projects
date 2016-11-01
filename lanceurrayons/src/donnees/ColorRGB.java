package donnees;

import java.awt.Color;

public class ColorRGB {

    // DLB: ces attributs devraient être non modifiables (final)
    private double r;
    private double g;
    private double b;

    public static final ColorRGB NULLCOLOR = new ColorRGB(0, 0, 0);

    public ColorRGB(double r, double g, double b) {
        this.r = r;
        this.b = b;
        this.g = g;
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public int getRGB() {
        double r1, g1, b1;
        r1 = r > 1.0d ? 1.0d : r;
        b1 = b > 1.0d ? 1.0d : b;
        g1 = g > 1.0d ? 1.0 : g;
        Color tmp = new Color((float) r1, (float) g1, (float) b1);
        return tmp.getRGB();
    }

    /**
     * Produit de schur
     * 
     * @param color2
     * @return
     */
    public ColorRGB times(ColorRGB color2) {
        return new ColorRGB(this.r * color2.r, this.g * color2.g, this.b * color2.b);
    }

    public ColorRGB add(ColorRGB c2) {
        double r1 = this.getR() + c2.getR();
        double g1 = this.getG() + c2.getG();
        double b1 = this.getB() + c2.getB();
        return new ColorRGB(r1, g1, b1);
    }

    public ColorRGB mul(double d) {
        double r1 = this.getR() * d;
        double g1 = this.getG() * d;
        double b1 = this.getB() * d;
        return new ColorRGB(r1, g1, b1);
    }

    public ColorRGB div(double d) {
        double r1 = this.getR() / d;
        double g1 = this.getG() / d;
        double b1 = this.getB() / d;
        return new ColorRGB(r1, g1, b1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ColorRGB) {
            ColorRGB color2 = (ColorRGB) obj;
            boolean rEqual, gEqual, bEqual;
            rEqual = r > color2.getR() - GeometricObject.EPSILON && r < color2.getR() + GeometricObject.EPSILON;
            gEqual = g > color2.getG() - GeometricObject.EPSILON && g < color2.getG() + GeometricObject.EPSILON;
            bEqual = b > color2.getB() - GeometricObject.EPSILON && b < color2.getB() + GeometricObject.EPSILON;
            return rEqual && bEqual && gEqual;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) ((r + 1) * 1000 + (g + 1) * 100 + (b + 1) * 10);
    }
}
