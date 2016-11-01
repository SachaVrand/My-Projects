package vues;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

import principal.Ressources;

/**
 * Classe représentant le curseur sur un bloc.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class CursorBorder extends AbstractBorder{
	
	/**
	 * Epaisseur de la bordure.
	 */
	private static int BORDERLENGTH = 7 * Ressources.multResolution;
	private static int EPAISSEUR5 = 3 * Ressources.multResolution;
	private static int EPAISSEUR4 = 2 * Ressources.multResolution;
	private static int EPAISSEUR3 = 1 * Ressources.multResolution;
	private static int EPAISSEUR2 = 2 * Ressources.multResolution;
	private static int EPAISSEUR1 = 1 * Ressources.multResolution;
	
	/**
	 * Couleur de la bordure extérieur.
	 */
	private final static Color OUTSIDECOLOR = Color.black;
	
	/**
	 * Couleur de la bordure intérieur.
	 */
	private Color borderColor;
	
	
	/**
	 * Constructeur de {@link CursorBorder}.
	 * @param borderColor Couleur de la bordure intérieur
	 */
	public CursorBorder(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x, y, width, height);
		
		//coin sup gauche
		g.setColor(OUTSIDECOLOR);
		g.fillRect(0, 0, BORDERLENGTH, EPAISSEUR5);
		g.fillRect(0, 0, EPAISSEUR5, BORDERLENGTH);
		g.setColor(borderColor);
		g.fillRect(EPAISSEUR1, EPAISSEUR1, BORDERLENGTH - EPAISSEUR2, EPAISSEUR3);
		g.fillRect(EPAISSEUR1, EPAISSEUR1, EPAISSEUR3, BORDERLENGTH - EPAISSEUR2);
		
		//coin inf gauche
		g.setColor(OUTSIDECOLOR);
		g.fillRect(0, height - EPAISSEUR5, BORDERLENGTH, EPAISSEUR5);
		g.fillRect(0, height - BORDERLENGTH, EPAISSEUR5, BORDERLENGTH);
		g.setColor(borderColor);
		g.fillRect(EPAISSEUR1, height - EPAISSEUR4, BORDERLENGTH - EPAISSEUR2, EPAISSEUR3);
		g.fillRect(EPAISSEUR1, height - BORDERLENGTH + EPAISSEUR1, EPAISSEUR3, BORDERLENGTH - EPAISSEUR2);
		
		//coin sup droit
		g.setColor(OUTSIDECOLOR);
		g.fillRect(width - BORDERLENGTH, 0, BORDERLENGTH, EPAISSEUR5);
		g.fillRect(width - EPAISSEUR5, 0, EPAISSEUR5, BORDERLENGTH);
		g.setColor(borderColor);
		g.fillRect(width - BORDERLENGTH + EPAISSEUR1, EPAISSEUR1, BORDERLENGTH - EPAISSEUR2, EPAISSEUR3);
		g.fillRect(width - EPAISSEUR4, EPAISSEUR1, EPAISSEUR3, BORDERLENGTH - EPAISSEUR2);
		
		//coin inf droit
		g.setColor(OUTSIDECOLOR);
		g.fillRect(width - BORDERLENGTH, height - EPAISSEUR5, BORDERLENGTH, EPAISSEUR5);
		g.fillRect(width - EPAISSEUR5, height - BORDERLENGTH, EPAISSEUR5, BORDERLENGTH);
		g.setColor(borderColor);
		g.fillRect(width - BORDERLENGTH + EPAISSEUR1, height - EPAISSEUR4, BORDERLENGTH - EPAISSEUR2, EPAISSEUR3);
		g.fillRect(width - EPAISSEUR4, height - BORDERLENGTH + EPAISSEUR1, EPAISSEUR3, BORDERLENGTH - EPAISSEUR2);
	}
	
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}
	
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		return new Insets(0,0,0,0);
	}

}
