package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import principal.Ressources;

/**
 * Classe représentant la vue du menu d'aide.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class MenuAide extends JPanel{
	
	/**
	 * Largeur de cette vue.
	 */
	private static final int PREFWIDTH = (int)(Ressources.BGMENUGIF.getIconWidth()*1.5);
	
	/**
	 * Hauteur de cette vue.
	 */
	private static final int PREFHEIGHT = (int)(Ressources.BGMENUGIF.getIconHeight()*1.5);
	
	/**
	 * Dimension de cette vue.
	 */
	private static final Dimension PREFDIM = new Dimension(PREFWIDTH,PREFHEIGHT);
	
	/**
	 * Image de fond de cette vue.
	 */
	private static final Image BACKGROUND = Ressources.BGMENUGIF.getImage();
	
	/**
	 * Image de la fleche gauche permettant de revenir en arrière sur les aides.
	 */
	private static final ImageIcon FLECHEG = Ressources.FLECHEOPTG;
	
	/**
	 * Image de la fleche gauche permettant d'avancer sur les aides.
	 */
	private static final ImageIcon FLECHED = Ressources.FLECHEOPTD;
	
	
	/**
	 * Label de la fleche gauche.
	 */
	private JLabel flecheGauche;
	
	/**
	 * Label de la fleche droite.
	 */
	private JLabel flecheDroite;
	
	/**
	 * Panel centrale qui contient les aides.
	 */
	private JPanel panelCentral;
	
	/**
	 * Label de l'aide affichée.
	 */
	private JLabel lblAide;
	
	/**
	 * Indice de l'aide à afficher.
	 */
	private int indAide;
	
	/**
	 * Toutes les images des aides.
	 */
	private ImageIcon[] images;
	
	/**
	 * Constructeur de {@link MenuAide}.
	 * @param images Toutes les images à dérouler.
	 */
	public MenuAide(ImageIcon[] images) {
		super();
		this.indAide = 0;
		this.images = images;
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		this.flecheGauche = new JLabel(FLECHEG);
		this.flecheGauche.setPreferredSize(new Dimension(20, 30));
		this.flecheDroite = new JLabel(FLECHED);
		this.flecheDroite.setPreferredSize(new Dimension(20, 30));
		this.lblAide = new JLabel(images[indAide]);
		
		this.panelCentral = new RoundedPanel();
		this.panelCentral.setLayout(new BorderLayout());
		this.panelCentral.add(flecheGauche, BorderLayout.WEST);
		this.panelCentral.add(flecheDroite, BorderLayout.EAST);
		this.panelCentral.add(lblAide, BorderLayout.CENTER);
		this.panelCentral.setBorder(Ressources.YELLOWBORDER);
		this.panelCentral.setBackground(Color.white);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(60, 60, 60, 60);
		this.add(panelCentral, gbc);
		this.setMinimumSize(PREFDIM);
		this.setPreferredSize(PREFDIM);
		this.setBackground(Color.black);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension sourceDim = new Dimension(BACKGROUND.getWidth(null), BACKGROUND.getHeight(null));
		KeepRatioSize k = KeepRatioSize.getSizeKeepingRatio(sourceDim, this.getSize());
		g.drawImage(BACKGROUND, k.getX(), k.getY(), k.getWidth(), k.getHeight(), this);
	}
	
	/**
	 * Méthode qui permet d'afficher l'image suivante.
	 */
	public void afficherImageSuiv()
	{
		indAide = indAide + 1 == images.length ? indAide : indAide + 1;
		this.lblAide.setIcon(images[indAide]);
	}
	
	/**
	 * Méthode qui permet d'afficher l'image precedente.
	 */
	public void afficherImagePrec()
	{
		indAide = indAide - 1 == -1 ? indAide : indAide - 1;
		this.lblAide.setIcon(images[indAide]);
	}
	
	/**
	 * Méthode qui retourne la fleche droite.
	 * @return la fleche droite.
	 */
	public JLabel getFlecheDroite() {
		return flecheDroite;
	}
	
	/**
	 * Méthode qui retourne la fleche gauche.
	 * @return La fleche gauche.
	 */
	public JLabel getFlecheGauche() {
		return flecheGauche;
	}

}
