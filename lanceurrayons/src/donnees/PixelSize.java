package donnees;

public class PixelSize {

    private double pixelHeight;
    private double pixelWidth;

    public PixelSize(ImageSize imageSize, double fovr) {
        pixelHeight = Math.tan(fovr / 2);
        pixelWidth = pixelHeight * imageSize.getLargeur() / imageSize.getHauteur();
    }

    public double getPixelHeight() {
        return pixelHeight;
    }

    public double getPixelWidth() {
        return pixelWidth;
    }

}
