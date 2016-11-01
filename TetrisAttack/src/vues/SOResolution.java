package vues;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import donnees.Changement;
import principal.Ressources;

/**
 * Classe représentant une sous option pour la résolution.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class SOResolution extends PanelSousOption{

	/**
	 * Image de la fleche de selection gauche.
	 */
	private static final ImageIcon FLECHEG = Ressources.FLECHEOPTG;
	
	/**
	 * Image de la fleche de selection droite.
	 */
	private static final ImageIcon FLECHED = Ressources.FLECHEOPTD;
	
	/**
	 * Valeur associée à ce panel. indice permettant dans le tableau de resolution permettant d'afficher la bonne valeur.
	 */
	private int valeur;
	
	/**
	 * Label de la fleche de selection gauche.
	 */
	private JLabel flecheGauche;
	
	/**
	 * Label de la fleche de selection droite.
	 */
	private JLabel flecheDroite;
	
	/**
	 * Tableau des résolutions possibles, sous forme de string.
	 */
	private String[] resolutionsPossibles;
	
	/**
	 * Constructeur de {@link SOResolution}.
	 * @param label Titre de la sous option.
	 * @param c Fonction de changement associée. Permet de changer la valeur de la résolution.
	 * @param valeur indice dans les resolutions correspondant à la résolution actuelle.
	 */
	public SOResolution(String label, Changement c, int valeur) {
		super(label, c);
		this.valeur = valeur;
		this.resolutionsPossibles = new String[]{"256x224","512x448","768x672","1024x896"};
		initComposants(label);
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	@Override
	protected void initComposants(String label) {
		super.initComposants(label);
		
		this.flecheDroite = new JLabel(FLECHED);
		this.flecheDroite.setName("fd");
		this.flecheGauche = new JLabel(FLECHEG);
		this.flecheGauche.setName("fg");
		
		SpringLayout layout = (SpringLayout)this.getLayout();
		layout.removeLayoutComponent(lblTxtValue);
		
		this.add(flecheGauche);
		this.add(lblTxtValue);
		this.add(flecheDroite);
		this.lblSeparateur.setPreferredSize(new Dimension(10, 30));
		this.flecheDroite.setPreferredSize(new Dimension(20, 30));
		this.flecheGauche.setPreferredSize(new Dimension(20, 30));
		this.lblTxtValue.setPreferredSize(new Dimension(100, 30));
		this.lblTxtValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		layout.putConstraint(SpringLayout.WEST, flecheGauche, 40, SpringLayout.EAST, lblSeparateur);
		layout.putConstraint(SpringLayout.WEST, lblTxtValue, 0, SpringLayout.EAST, flecheGauche);
		layout.putConstraint(SpringLayout.WEST, flecheDroite, 0, SpringLayout.EAST, lblTxtValue);
	}
	
	/**
	 * Méthode qui retourne l'indice dans resolutions de la resolution actuelle.
	 * @return l'indice dans resolutions de la resolution actuelle.
	 */
	public int getValeur() {
		return valeur;
	}
	
	/**
	 * Méthode qui permet d'ajouter des listeners à ce panel.
	 * @param MAPanel Listener du panel en entier.
	 * @param MAFleche listener pour les fleches gauche et droite.
	 */
	public void ajouterListeners(MouseAdapter MAPanel, MouseAdapter MAFleche)
	{
		this.flecheDroite.addMouseListener(MAFleche);
		this.flecheGauche.addMouseListener(MAFleche);
		this.addMouseListener(MAPanel);
	}

	@Override
	public String getTextValue() {
		return resolutionsPossibles[valeur-1];
	}

	@Override
	public void setValue(Object o) {
		this.valeur = (int)o;
		this.lblTxtValue.setText(getTextValue());
	}

}
