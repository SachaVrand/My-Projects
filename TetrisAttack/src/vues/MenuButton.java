package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import principal.Ressources;

/**
 * Classe représentant les boutons du {@link MenuPrincipal}.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class MenuButton extends JButton{

	/**
	 * Taille par defaut d'un bouton.
	 */
	private final static Dimension PREFDIM = new Dimension(200, 60);
	
	/**
	 * Police par défaut du texte affiché dans le bouton.
	 */
	private final static String FONTNAME = Ressources.FONTNAMEMENU;
	
	/**
	 * Taille de la police par défaut
	 */
	private final static int FONTSIZE = 32;
	
	/**
	 * Style de la police par défaut.
	 */
	private final static int FONTSTYLE = Font.PLAIN;
	
	/**
	 * Couleur de fond par défaut du bouton.
	 */
	private final static Color BACKGROUNDCOLOR = Ressources.BACKGROUNDBTNCOLOR;
	
	/**
	 * Timer permettant de faire l'animation du clignotement du curseur.
	 */
	private Timer timerClignotement;
	
	/**
	 * Image du curseur de selection.
	 */
	private Image selectIcon;
	
	/**
	 * Constructeur de la classe MenuButton.
	 * @param text Texte à afficher sur le bouton.
	 * @param foreground Couleur de du texte affiché sur le bouton.
	 * @param border Bordure du bouton. 
	 */
	public MenuButton(String text, Color foreground, MenuButtonBorder border) {
		super(text.toUpperCase());
		this.selectIcon = null;
		initComposant(foreground, border);
		initTimer();
	}
	
	/**
	 * Méthode qui permet de d'initialiser les composants graphiques du MenuButton
	 * @param foreground Couleur de du texte affiché sur le bouton.
	 * @param border Bordure du bouton.
	 */
	private void initComposant(Color foreground, MenuButtonBorder border)
	{
		this.setBorder(border);
		Font font = new Font(FONTNAME,FONTSTYLE,FONTSIZE);
		this.setFont(font);
		this.setForeground(foreground);
		this.setPreferredSize(PREFDIM);
		this.setMinimumSize(PREFDIM);
		this.setBackground(BACKGROUNDCOLOR);
		this.setContentAreaFilled(false);
	}
	
	/**
	 * Méthode qui permet d'initialiser le timer d'animation du curseur.
	 */
	private void initTimer()
	{
		this.timerClignotement = new Timer(250, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectIcon = (selectIcon == null) ? Ressources.SELECTICON.getImage() : null;
				repaint();
			}
		});
	}
	
	/**
	 * Fonction qui permet de peindre ou d'effacer l'icone de selection affiché à coté du bouton.
	 * @param b Vrai si l'icone doit être peint et faux si il doit être effacer.
	 */
	public void paintSelectIcon(boolean b)
	{
		if(b)
		{
			this.selectIcon = Ressources.SELECTICON.getImage();
			this.timerClignotement.start();
			this.repaint();
		}
		else
		{
			this.selectIcon = null;
			this.timerClignotement.stop();
			this.repaint();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(this.getBackground());
		g2d.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 45);
		super.paintComponent(g);
			
		if(selectIcon != null)
		{
			int y = this.getHeight()/2 - Ressources.SELECTICON.getIconHeight()/2;
			g.drawImage(selectIcon, 15, y, null);
		}
		
	}
}
