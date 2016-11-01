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
 * Classe représentant une sous option de son.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class SOSon extends PanelSousOption{

	/**
	 * Image de la fleche de selection gauche.
	 */
	private static final ImageIcon FLECHEG = Ressources.FLECHEOPTG;
	
	/**
	 * Image de la fleche de selection droite.
	 */
	private static final ImageIcon FLECHED = Ressources.FLECHEOPTD;
	
	/**
	 * Valeur du volume.
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
	 * valeur possibles pour le son sous forme de texte. valeurs servant seulement pour l'affichage.
	 */
	private String[] valeursSon = new String[]{"0","1","2","3","4","5","6","7","8","9","10"};
	
	/**
	 * Constructeur de {@link SOSon}.
	 * @param label Titre de la sous option.
	 * @param c Fonction de changement permettant de changer le volume.
	 * @param valeur indice de la valeur du volume actuelle, dans les valeurs de volume possible en chaine de carac.
	 */
	public SOSon(String label, Changement c, int valeur) {
		super(label, c);
		this.valeur = valeur;
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
	 * Méthode qui permet d'ajouter des listeners à ce panel.
	 * @param MAFleche Listener des fleches de selection.
	 */
	public void ajouterListeners(MouseAdapter MAFleche)
	{
		this.flecheDroite.addMouseListener(MAFleche);
		this.flecheGauche.addMouseListener(MAFleche);
	}
	
	@Override
	public void setCurseurPresent(boolean curseurPresent) {
		super.setCurseurPresent(curseurPresent);
	}

	@Override
	public String getTextValue() {
		return String.valueOf(valeursSon[valeur]);
	}

	@Override
	public void setValue(Object o) {
		this.valeur = (int)o;
		this.lblTxtValue.setText(getTextValue());
	}
}
