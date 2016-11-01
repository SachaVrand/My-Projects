package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import principal.NumberFont;
import principal.Ressources;

/**
 * Classe représentant un chiffre en jeu. Pour le temps et les score.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class NumberPanel extends JPanel{
	
	/**
	 * Hauteur de cette vue.
	 */
	public static int NUMBERHEIGHT = NumberFont.TZERO.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de cette vue.
	 */
	public static int NUMBERWIDTH = NumberFont.TZERO.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension de cette vue.
	 */
	public static Dimension PREFDIM = new Dimension(NUMBERWIDTH, NUMBERHEIGHT);
	
	/**
	 * Image de cette vue.
	 */
	private Image image;
	
	/**
	 * Constructeur de {@link NumberPanel}.
	 * @param image Image du chiffre à afficher.
	 */
	public NumberPanel(Image image) {
		this.image = image;
		this.setOpaque(false);
		this.setPreferredSize(PREFDIM);
	}
	
	/**
	 * Méthode qui permet de set l'image du chiffre à afficher.
	 * @param image Image du chiffre à afficher.
	 */
	public void setImage(Image image) {
		this.image = image;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, NUMBERWIDTH, NUMBERHEIGHT,null);
	}

}
