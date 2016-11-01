package vues;

import java.awt.Dimension;

/**
 * Classe permettant de retourner les dimension augmenter d'une image en gardant le ratio de l'image.
 * @author Sacha
 *
 */
public class KeepRatioSize {
	
	/**
	 * Où positionner l'image en x.
	 */
	private int x;
	
	/**
	 * Où positionner l'image en y.
	 */
	private int y;
	
	/**
	 * La largeur de l'image augmenté.
	 */
	private int width;
	
	/**
	 * La hauteur de l'image augmenté.
	 */
	private int height;
	
	/**
	 * Constructeur de {@link KeepRatioSize}.
	 * @param x Où positionner l'image en x.
	 * @param y Où positionner l'image en y.
	 * @param width La largeur de l'image augmenté.
	 * @param heigth La hauteur de l'image augmenté.
	 */
	public KeepRatioSize(int x, int y, int width, int heigth) {
		this.y = y;
		this.x = x;
		this.width = width;
		this.height = heigth;
	}
	
	/**
	 * Méthode qui retourne où positionner l'image en x.
	 * @return Où positionner l'image en x.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Méthode qui retourne où positionner l'image en y.
	 * @return Où positionner l'image en y.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Méthode qui retourne la hauteur augmentée.
	 * @return la hauteur augmentée.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Méthode qui retourne la largeur augmentée.
	 * @return la largeur augmentée.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Fonction qui permet de determiner le {@link KeepRatioSize} a partir de dimensions de l'image de base et la dimension où l'on souhaite afficher l'image.
	 * @param sourceDim dimension de base de l'image.
	 * @param destinationDim dimension de l'endroit où afficher l'image.
	 * @return La {@link KeepRatioSize} pour garder le ratio de l'image.S
	 */
	public static KeepRatioSize getSizeKeepingRatio(Dimension sourceDim, Dimension destinationDim)
	{
		double scaleX, scaleY;
		scaleX = destinationDim .getWidth()  / sourceDim .getWidth();
		scaleY = destinationDim .getHeight() / sourceDim .getHeight();
		double scale = Math.min( scaleX, scaleY );
		int newWidth = (int)(sourceDim.getWidth()*scale), newHeight = (int)(sourceDim.getHeight()*scale);
		int x = (destinationDim.width - newWidth) / 2;
		int y = (destinationDim.height - newHeight) / 2;
		return new KeepRatioSize(x, y,newWidth, newHeight);
	}
}
