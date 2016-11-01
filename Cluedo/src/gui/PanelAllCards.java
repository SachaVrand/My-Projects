package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import principal.Arme;
import principal.Carte;
import principal.Lieu;
import principal.Suspect;


/**
 * Clase repr�sentant un panel compos�e de toutes les cartes du jeu. Implemente l'interface ActionListener utilis�e pour �couter les cartes(JButton).
 * @author Sacha
 *
 */
public class PanelAllCards extends JPanel implements ActionListener{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilis�.
	 */
	private static final long serialVersionUID = 160299452614029965L;
	
	/**
	 * Liste de boutons. Chaque bouton repr�sente un carte suspect du jeu.
	 */
	private List<JButton> lstBtnCartesSuspect;
	
	/**
	 * Liste de boutons. Chaque bouton repr�sente un carte arme du jeu.
	 */
	private List<JButton> lstBtnCartesArme;
	
	/**
	 * Liste de boutons. Chaque bouton repr�sente un carte lieu du jeu.
	 */
	private List<JButton> lstBtnCartesLieu;
	
	/**
	 * Boolean permettant de savoir si les boutons du panel doivent �tre �cout�s.
	 */
	private boolean areListened;
	
	/**
	 * Bouton repr�sentant la carte de type arme selectionn�e.
	 */
	private JButton highlightedCardArme = null;
	
	/**
	 * Bouton repr�sentant la carte de type suspect selectionn�e.
	 */
	private JButton highlightedCardSuspect = null;
	
	/**
	 * Bouton repr�sentant la carte de type lieu selectionn�e.
	 */
	private JButton highlightedCardLieu = null;
	
	
	/**
	 * Constructeur de la classe PanelAllCards. Instancie un nouveau panel. Charge ses composants, et listeners si la valeur areListened est � true.
	 * @param areListened true si les composants doivent �tre �cout�s, false sinon.
	 */
	public PanelAllCards(boolean areListened)
	{
		super(new GridBagLayout());
		this.areListened = areListened;
		this.lstBtnCartesArme = new ArrayList<JButton>();
		this.lstBtnCartesLieu = new ArrayList<JButton>();
		this.lstBtnCartesSuspect = new ArrayList<JButton>();
		this.load();
	}
	
	/**
	 * M�thode permettant de charger les diff�rents composants graphique.
	 */
	private void load()
	{
		loadJButtonCartes();
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel lblSuspects = new JLabel("Suspects");
		JLabel lblArmes = new JLabel("Armes");
		JLabel lblLieux = new JLabel("Lieux");
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.insets = new Insets(10, 15, 5, 15);
		this.add(lblSuspects,gbc);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sepSuspect = new JSeparator();
		sepSuspect.setPreferredSize(new Dimension(100, 1));
		this.add(sepSuspect, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(5, 2, 5, 2);
		for(JButton btn : lstBtnCartesSuspect)
		{
			this.add(btn, gbc);
			gbc.gridx++;
		}
		gbc.gridy++;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.gridx = 0;
		this.add(lblArmes,gbc);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sepArmes = new JSeparator();
		sepArmes.setPreferredSize(new Dimension(100, 1));
		this.add(sepArmes, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(5, 2, 5, 2);
		for(JButton btn : lstBtnCartesArme)
		{
			this.add(btn, gbc);
			gbc.gridx++;
		}
		gbc.gridy++;
		gbc.insets = new Insets(5, 10, 5, 15);
		gbc.gridx = 0;
		this.add(lblLieux,gbc);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sepLieux = new JSeparator();
		sepLieux.setPreferredSize(new Dimension(100, 1));
		this.add(sepLieux, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(5, 2, 5, 2);
		for(JButton btn : lstBtnCartesLieu)
		{
			this.add(btn, gbc);
			gbc.gridx++;
		}
	}
	
	/**
	 * M�thode permettant de charger les diff�rents boutons repr�sentant les cartes du jeu.
	 */
	private void loadJButtonCartes()
	{
		List<Carte> listeCartes = Carte.creerPaquetDeCartes();
		for(Carte c : listeCartes)
		{
			JButton tmp = new JButton(c.getImage());
			tmp.setName(c.getNom());
			tmp.setContentAreaFilled(false);
			
			tmp.setBorderPainted(false);
			if(!areListened)
				tmp.setFocusPainted(false);
			if(c instanceof Suspect)
			{
				tmp.setBorder(BorderFactory.createLineBorder(Color.black, 3, true));
				if(areListened) 
					tmp.addActionListener(this);
				lstBtnCartesSuspect.add(tmp);
			}
			else if(c instanceof Arme)
			{
				tmp.setBorder(BorderFactory.createLineBorder(Color.red, 3, true));
				if(areListened)
					tmp.addActionListener(this);
				lstBtnCartesArme.add(tmp);
			}
			else if(c instanceof Lieu)
			{
				tmp.setBorder(BorderFactory.createLineBorder(Color.blue, 3, true));
				if(areListened)
					tmp.addActionListener(this);
				lstBtnCartesLieu.add(tmp);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(lstBtnCartesArme.contains((JButton)e.getSource()))
		{
			if(highlightedCardArme != null)
				highlightedCardArme.setBorderPainted(false);
			highlightedCardArme = (JButton)e.getSource();
			highlightedCardArme.setBorderPainted(true);
		}
		else if(lstBtnCartesLieu.contains((JButton)e.getSource()))
		{
			if(highlightedCardLieu != null)
				highlightedCardLieu.setBorderPainted(false);
			highlightedCardLieu = (JButton)e.getSource();
			highlightedCardLieu.setBorderPainted(true);
		}
		else if(lstBtnCartesSuspect.contains((JButton)e.getSource()))
		{
			if(highlightedCardSuspect != null)
				highlightedCardSuspect.setBorderPainted(false);
			highlightedCardSuspect = (JButton)e.getSource();
			highlightedCardSuspect.setBorderPainted(true);
		}
	}
	
	/**
	 * M�thode qui retourne la carte arme slectionn�e. null si aucune.
	 * @return la carte arme slectionn�e. null si aucune.
	 */
	public JButton getHighlightedCardArme() {
		return highlightedCardArme;
	}
	
	/**
	 * M�thode qui retourne la carte lieu slectionn�e. null si aucune.
	 * @return la carte lieu slectionn�e. null si aucune.
	 */
	public JButton getHighlightedCardLieu() {
		return highlightedCardLieu;
	}

	/**
	 * M�thode qui retourne la carte suspect slectionn�e. null si aucune.
	 * @return la carte suspect slectionn�e. null si aucune.
	 */
	public JButton getHighlightedCardSuspect() {
		return highlightedCardSuspect;
	}
}
