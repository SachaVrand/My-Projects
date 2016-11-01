package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JPanel;

import principal.Ressources;

/**
 * Class représentant le menu principal.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class MenuPrincipal extends JPanel{
	
	/**
	 * Hauteur de cette vue.
	 */
	private static final int PREFHEIGHT = Ressources.BGMENUGIF.getIconHeight();
	
	/**
	 * Largeur de cette vue.
	 */
	private static final int PREFWIDTH = Ressources.BGMENUGIF.getIconWidth();
	
	/**
	 * Dimension de cette vue.
	 */
	private static final Dimension PREFDIM = new Dimension(PREFWIDTH, PREFHEIGHT);
	
	/**
	 * Bouton versus du menu
	 */
	private MenuButton btnVS;
	
	/**
	 * Bouton aide du menu.
	 */
	private MenuButton btnAide;
	
	/**
	 * Bouton credits du menu.
	 */
	private MenuButton btnCredits;
	
	/**
	 * Bouton quitter du menu.
	 */
	private MenuButton btnQuitter;
	
	/**
	 * Bouton vers les options.
	 */
	private MenuButton btnOptions;
	
	/**
	 * tableau des différents boutons du menu et dans l'ordre. (de haut en bas).
	 */
	private MenuButton[] buttonsOrder;
	
	/**
	 * Image de fond du menu.
	 */
	private Image backgroundImage;
	
	
	/**
	 * Constructeur de MenuPrincipal. Instancie un panel avec les différents composants du menu.
	 */
	public MenuPrincipal() {
		super();
		backgroundImage = Ressources.BGMENUGIF.getImage();
		this.initComposant();
	}
	
	/**
	 * Méthode qui permet d'inialiser les diférents composants du menu.
	 */
	private void initComposant()
	{
		btnVS = new MenuButton("VERSUS",Ressources.ROSEOUTSIDE, Ressources.REDBORDER);
		btnOptions = new MenuButton("OPTIONS", Ressources.BLUEOUTSIDE, Ressources.BLUEBORDER);
		btnAide = new MenuButton("HELP",Ressources.YELLOWOUTSIDE, Ressources.YELLOWBORDER);
		btnCredits = new MenuButton("CREDITS",Ressources.GREENOUTSIDE, Ressources.GREENBORDER);
		btnQuitter = new MenuButton("QUIT",Ressources.ORANGEOUTSIDE, Ressources.ORANGEBORDER);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(60, 60, 10, 60);
		this.add(btnVS,gbc);
		gbc.insets = new Insets(10, 60, 10, 60);
		gbc.gridy++;
		this.add(btnOptions,gbc);
		gbc.gridy++;
		this.add(btnAide,gbc);
		gbc.gridy++;
		this.add(btnCredits,gbc);
		gbc.gridy++;
		gbc.insets = new Insets(10, 60, 60, 60);
		this.add(btnQuitter,gbc);

		buttonsOrder = new MenuButton[]{btnVS,btnOptions,btnAide,btnCredits,btnQuitter};
		
		this.setFocusable(true);
		this.setOpaque(true);
		this.setBackground(Color.black);
		this.setPreferredSize(PREFDIM);
		this.setMinimumSize(PREFDIM);
	}
	
	/**
	 * Méthode qui peint le curseur de selection.
	 * @param menuButton Le bouton ou le curseur doit être peint.
	 * @param b Vrai s'il doit etre peint, faux s'il doit disparaitre.
	 */
	public void paintSelectIconForButton(MenuButton menuButton, boolean b)
	{
		menuButton.paintSelectIcon(b);
	}
	
	/**
	 * Méthode qui retourne les boutons dans l'ordre de ce menu.
	 * @return les botuons dans l'ordre de ce menu.
	 */
	public MenuButton[] getButtonsOrder() {
		return buttonsOrder;
	}
	
	/**
	 * Méthode qui retourne le bouton options
	 * @return le bouton options.
	 */
	public MenuButton getBtnOptions() {
		return btnOptions;
	}
	
	/**
	 * Méthode qui retourne le bouton aide.
	 * @return le bouton aide.
	 */
	public MenuButton getBtnAide() {
		return btnAide;
	}
	
	/**
	 * Méthode qui retourne le bouton versus.
	 * @return le bouton versus.
	 */
	public MenuButton getBtnVS() {
		return btnVS;
	}
	
	/**
	 * Méthode qui retourne le bouton credits.
	 * @return le bouton credits.
	 */
	public MenuButton getBtnCredits() {
		return btnCredits;
	}
	
	/**
	 * Méthode qui retourne le bouton quitter.
	 * @return le bouton quitter.
	 */
	public MenuButton getBtnQuitter() {
		return btnQuitter;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension sourceDim = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		KeepRatioSize k = KeepRatioSize.getSizeKeepingRatio(sourceDim, this.getSize());
		g.drawImage(backgroundImage, k.getX(), k.getY(), k.getWidth(), k.getHeight(), this);
	}
}
