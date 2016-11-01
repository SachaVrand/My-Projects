package donnees;

public class ImageSize {
    private int largeur;
    private int hauteur;

    public ImageSize(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }
}
