package donnees;

public class PlainColor implements Material {

    private ColorRGB color;

    public PlainColor(ColorRGB color) {
        this.color = color;
    }

    @Override
    public ColorRGB getColor(Point p) {
        return this.color;
    }

}
