package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import principal.Ressources;

/**
 * Classe représentant l'icone d'une manche gagnée ou non.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class WinPointPanel extends JPanel{
	
	/**
	 * Hauteur de cette vue.
	 */
	public static int WPHEIGHT = Ressources.WINNOPOINTJ1.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de cette vue.
	 */
	public static int WPWIDTH = Ressources.WINNOPOINTJ1.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension de cette vue.
	 */
	public static Dimension PREFDIM = new Dimension(WPWIDTH, WPHEIGHT);
	
	/**
	 * Image de cette vue.
	 */
	private Image img;
	
	/**
	 * Image d'une manche non gagnée.
	 */
	private Image imgNoPoint;
	
	/**
	 * Image d'une manche gagnée.
	 */
	private Image imgPoint;
	
	/**
	 * Constructeur de {@link WinPointPanel}. 
	 * @param noJoueur Numéro du joueur auxquelle on attribue les points.
	 */
	public WinPointPanel(int noJoueur) {
		imgNoPoint = (noJoueur == 1)?(Ressources.WINNOPOINTJ1.getImage()):(Ressources.WINNOPOINTJ2.getImage());
		imgPoint = (noJoueur == 1)?(Ressources.WINPOINTJ1.getImage()):(Ressources.WINPOINTJ2.getImage());
		img = imgNoPoint;
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		this.setOpaque(false);
		this.setPreferredSize(PREFDIM);
	}
	
	/**
	 * Méthode qui permet de set cette vue à l'image de manche gagnée.
	 */
	public void setGagne()
	{
		this.img = imgPoint;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, WPWIDTH, WPHEIGHT, this);
	}

}
