package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import principal.Ressources;

/**
 * Classe représentant l'écran titre du début du jeu.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class TitleScreenView extends JPanel{
	
	/**
	 * image de fond de cette vue.
	 */
	private final static Image TITLEGIF = Ressources.TITLEGIF.getImage();
	
	/**
	 * Hauteur de cette vue.
	 */
	private static final int PREFHEIGHT = Ressources.TITLEGIF.getIconHeight();
	
	/**
	 * Largeur de cette vue.
	 */
	private static final int PREFWIDTH = Ressources.TITLEGIF.getIconWidth();
	
	/**
	 * Dimension de cette vue.
	 */
	private static final Dimension PREFDIM = new Dimension(PREFWIDTH,PREFHEIGHT);
	
	/**
	 * Constructeur de {@link TitleScreenView}.
	 */
	public TitleScreenView() {
		super(new BorderLayout());
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		this.setPreferredSize(PREFDIM);
		this.setMinimumSize(PREFDIM);
		this.setBackground(Color.black);
		this.setFocusable(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension sourceDim = new Dimension(TITLEGIF.getWidth(null), TITLEGIF.getHeight(null));
		Dimension destinationDim = this.getSize();
		KeepRatioSize k = KeepRatioSize.getSizeKeepingRatio(sourceDim, destinationDim);
		g.drawImage(TITLEGIF, k.getX(), k.getY(), k.getWidth(), k.getHeight(), this);
		g.dispose();
	}
}
