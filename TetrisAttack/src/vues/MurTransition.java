package vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import principal.Ressources;

/**
 * Classe représentant la vue du mur de transition.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class MurTransition extends JPanel{
	
	/**
	 * Hauteur du mur.
	 */
	public static int MURWIDTH = Ressources.MONTEETRANSITIONJ1.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Largeur du mur.
	 */
	public static int MURHEIGHT = Ressources.MONTEETRANSITIONJ1.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Dimension du mur.
	 */
	public static Dimension PREFDIM = new Dimension(MURWIDTH,MURHEIGHT);
	
	/**
	 * Image du mur
	 */
	private Image imgMur;
	
	/**
	 * Constructeur de {@link MurTransition}.
	 * @param noJoueur Numéro du joueur où est affichée le mur.
	 */
	public MurTransition(int noJoueur) {
		super(new BorderLayout());
		this.imgMur = (noJoueur == 1)?(Ressources.MONTEETRANSITIONJ1.getImage()):(Ressources.MONTEETRANSITIONJ2.getImage());
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		this.setOpaque(true);
		this.setPreferredSize(PREFDIM);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imgMur, 0, 0, MURWIDTH, MURHEIGHT, this);
	}

}
