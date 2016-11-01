package vues;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * Clase représentant la bordure des {@link MenuButton}.
 * Elle est composé de la facon suivante :
 * Une bordure carré au milieu entouré par deux bordures intérieures et le tout entouré par deux bordure carré extérieur. 
 * La bordure la plus extérieur étant arrondies sur les bords.
 * Et ensuite une autre bordure carré arrondie à l'intérieur du tout.
 * @author Sacha
 *
 */

@SuppressWarnings("serial")
public class MenuButtonBorder extends AbstractBorder{

	/**
	 * Couleur de la bordure extérieur qui entoure celle intérieur et celle du milieu.
	 */
	private Color outsideBorderColor;
	
	/**
	 * Couleur de la bordure intérieur qui entoure la couleur du milieu.
	 */
	private Color innerBorderColor;
	
	/**
	 * Couleur de la bordure du milieu.
	 */
	private Color middleBorderColor;
	
	/**
	 * Couleur de la bordure qui est la plus intérieur.
	 */
	private Color otherInnerBorderColor;
	
	/**
	 * Constructeur de MenuButtonBorder.
	 * @param outsideBorderColor Couleur de la bordure extérieur qui entoure celle intérieur et celle du milieu.
	 * @param innerBorderColor Couleur de la bordure intérieur qui entoure la couleur du milieu.
	 * @param middleBorderColor Couleur de la bordure du milieu.
	 * @param otherInnerBorderColor Couleur de la bordure qui est la plus intérieur.
	 */
	public MenuButtonBorder(Color outsideBorderColor, Color innerBorderColor, Color middleBorderColor, Color otherInnerBorderColor)
	{
		this.outsideBorderColor = outsideBorderColor;
		this.innerBorderColor = innerBorderColor;
		this.middleBorderColor = middleBorderColor;
		this.otherInnerBorderColor = otherInnerBorderColor;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x, y, width, height);
		
		g.setColor(outsideBorderColor);
		g.drawRect(x + 9, y + 9, width - 19, height - 19);
		g.drawRect(x + 8, y + 8, width - 17, height - 17);
		
		g.setColor(innerBorderColor);
		g.drawRect(x + 7, y + 7, width - 15, height - 15);
		g.drawRect(x + 6, y + 6, width - 13, height - 13);
		
		g.setColor(middleBorderColor);
		g.drawRect(x + 5, y + 5, width - 11, height - 11);
		g.drawRect(x + 4, y + 4, width - 9, height - 9);
		
		g.setColor(innerBorderColor);
		g.drawRect(x + 3, y + 3, width - 7, height - 7);
		g.drawRect(x + 2, y + 2, width - 5, height - 5);
		
		g.setColor(outsideBorderColor);
		g.drawLine(x + 1, y + 2, x + 1, height - 3);
		g.drawLine(width - 2, y + 2, width  - 2, height - 3);
		g.drawLine(x + 2, y + 1, width - 3, y + 1);
		g.drawLine(x + 2, height - 2, width - 3, height - 2);
		
		g.drawLine(x, y+3, x, height-4);
		g.drawLine(width-1, y+3, width-1, height-4);
		g.drawLine(x + 3, y, width - 4, y);
		g.drawLine(x + 3, height-1, width - 4, height-1);
		
		g.setColor(otherInnerBorderColor);
		g.drawRect(x + 10, y + 10, width - 21, height - 21);
		g.drawRect(x + 11, y + 11, width - 23, height - 23);
		
	}
	
	@Override
	public Insets getBorderInsets(Component c) {
		return getBorderInsets(c, new Insets(12, 12, 12, 12));
	}
	
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.top = insets.right = insets.bottom = 12;
        return insets;
	}
	
	@Override
	public boolean isBorderOpaque() {
		return true;
	}
	
	public Color getOutsideBorderColor() {
		return outsideBorderColor;
	}

}
