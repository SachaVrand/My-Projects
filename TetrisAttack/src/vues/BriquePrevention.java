package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import principal.Ressources;

/**
 * Classe représentant les briques qui avertissent le joueur.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class BriquePrevention extends JPanel{
	
	/**
	 * Hauteur de cette vue.
	 */
	private static int PREFHEIGHT = Ressources.PREVBRIQUE3.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de cette vue.
	 */
	private static int PREFWIDTH = Ressources.PREVBRIQUE3.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension de cette vue.
	 */
	private static Dimension PREFDIM = new Dimension(PREFWIDTH, PREFHEIGHT);
	
	/**
	 * Constante représentant une brique de prevention de 3 de largeur.
	 */
	public static final int BRIQUE3 = 1;
	
	/**
	 * Constante représentant une brique de prevention de 5 de largeur.
	 */
	public static final int BRIQUE5 = 2;
	
	/**
	 * Constante représentant une brique de prevention de 6 de largeur /(et plus de 1 de hauteur).
	 */
	public static final int BRIQUE6P = 3;
	
	/**
	 * Constante représentant une brique de prevention de 4 de largeur.
	 */
	public static final int BRIQUE4 = 4;
	
	/**
	 * Image de cette vue
	 */
	private Image image;
	
	/**
	 * Constructeur de {@link BriquePrevention}.
	 * @param brique Constante de {@link BriquePrevention} représentant la brique de prévention que l'on veut afficher.
	 */
	public BriquePrevention(int brique) {
		super();
		initComposants();
		choisirBrique(brique);
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		this.setPreferredSize(PREFDIM);
		this.setOpaque(false);
	}
	
	/**
	 * Méthode qui retourne l'image correspondant à la constante de {@link BriquePrevention}.
	 * @param choix Constante de {@link BriquePrevention} représentant la brique à afficher.
	 */
	private void choisirBrique(int choix)
	{
		if(choix == BRIQUE3)
		{
			image = Ressources.PREVBRIQUE3.getImage();
		}
		else if(choix == BRIQUE5)
		{
			image = Ressources.PREVBRIQUE5.getImage();
		}
		else if(choix == BRIQUE6P)
		{
			image = Ressources.PREVBRIQUE6P.getImage();
		}
		else if(choix == BRIQUE4)
		{
			image = Ressources.PREVBRIQUE4.getImage();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	/**
	 * Fonction qui retourne la constante de la brique de prévention à afficher en fonction de la brique pasée en paramètre sous forme de hauteur et largeur de la brique.
	 * @param longueur Longueur de la brique à prévenir.
	 * @param hauteur Hauteur de la brique à prévenir.
	 * @return La constante de {@link BriquePrevention} représentant la brique de prévention à afficher.
	 */
	public static int recupTypePrevBrique(int longueur, int hauteur)
	{
		if(hauteur > 1)
		{
			return BRIQUE6P;
		}
		else
		{
			if(longueur == 3)
			{
				return BRIQUE3;
			}
			else if(longueur == 4)
			{
				return BRIQUE4;
			}
			else if(longueur == 5)
			{
				return BRIQUE5;
			}
			else
			{
				return BRIQUE6P;
			}
		}
	}

}
