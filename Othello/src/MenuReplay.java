import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe représentant le menu pendant l'affichage de la sauvegarde.
 * Elle consiste en un panel avec un bouton retour, un bouton de lecture et d'arret de la sauvegarde, et de plusieurs label
 * pour afficher des informations telles que les scores et le tour du joueur.
 * @author Vrand
 *
 */
public class MenuReplay extends JPanel implements ChangeListener{
	
	private static final long serialVersionUID = -5043667680481005443L;
	
	/**
	 * Sliderpermettant de se deplacer rapidement dans le replay. 
	 */
	private JSlider sliderReplay = null;
	
	/**
	 * Bouton qui servira au retour en arriere du raplay, pas à pas.
	 */
	private JButton boutonRetourArriere = null;
	
	/**
	 * Bouton qui servira à l'avance pas à pas.
	 */
	private JButton boutonAvance = null;
	
	/**
	 * Bouton permettant de retourner à l'historique des sauvegardes.
	 */
	private JButton boutonRetour;
	
	/**
	 * Bouton permettant de lancer le replay ou de l'arreter s'il est déjà lancé.
	 */
	private JButton boutonLectureArret;
	
	/**
	 * Label affichant le score de noir.
	 */
	private JLabel lblScoreNoir;
	
	/**
	 * Label affichant le score de blanc.
	 */
	private JLabel lblScoreBlanc;
	
	/**
	 * Label affichant la couleur jouant ce tour.
	 */
	private JLabel lblCouleurJouant;
	
	/**
	 * Label affichant le message "Tour de : ".
	 */
	private JLabel lblMessageTourDe;
	
	/**
	 * AffichageSauvegarde dans lequel ce trouve ce menu.
	 */
	private AffichageSauvegarde jeu;
	
	/**
	 * Booléen qui permet de savoir si le replay est lancé. True si oui, sinon false.
	 */
	private boolean replayLancer = false;
	
	/**
	 * Combobox permettant de choisir la vitesse du replay
	 */
	private JComboBox<String> comboVitesse = null;
	
	/**
	 * Instancie un nouveau panel avec comme layout manager un borderlayout.
	 * @param jeu L'instance de affichage sauvegarde utilisant le menu.
	 */
	public MenuReplay(AffichageSauvegarde jeu)
	{
		super(new BorderLayout());
		this.jeu = jeu;
		this.load();
		this.ajoutListeners();
	}
	
	/**
	 * Méthode qui crée et charge les différents composants graphiques contenu dans le panel.
	 */
	private void load()
	{
		this.boutonRetour = new JButton("Retour");
		this.boutonLectureArret = new JButton("Lecture");
		this.boutonAvance = new JButton(Ressources.imageBtbnAvance);
		this.boutonAvance.setEnabled(false);
		JLabel labelCombo = new JLabel("Vitesse");
		this.comboVitesse = new JComboBox<String>();
		comboVitesse.addItem("Lent");
		comboVitesse.addItem("Normal");
		comboVitesse.addItem("Rapide");
		comboVitesse.setSelectedIndex(1);
		comboVitesse.setEnabled(false);
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(boutonRetour);
		panelBoutons.add(boutonLectureArret);
		panelBoutons.add(labelCombo);
		panelBoutons.add(comboVitesse);
		
		this.boutonRetourArriere = new JButton(Ressources.imageBtbnRetourArriere);
		this.boutonRetourArriere.setEnabled(false);
		this.sliderReplay = new JSlider(0, jeu.getSizeListeAJouer()-1, 0);
		this.sliderReplay.setMajorTickSpacing(jeu.getSizeListeAJouer()-1);
		this.sliderReplay.setMinorTickSpacing(1);
		this.sliderReplay.setPreferredSize(new Dimension(350, 50));
		this.sliderReplay.setPaintLabels(true);
		this.sliderReplay.setPaintTicks(true);
		this.sliderReplay.setEnabled(false);
		JPanel panelSlider = new JPanel();
		panelSlider.add(boutonRetourArriere);
		panelSlider.add(sliderReplay);
		panelSlider.add(boutonAvance);
		
		JPanel panelBoutonsSlider = new JPanel(new BorderLayout());
		panelBoutonsSlider.add(panelBoutons,BorderLayout.NORTH);
		panelBoutonsSlider.add(panelSlider,BorderLayout.SOUTH);
		
		lblScoreNoir = new JLabel(new ImageIcon(Couleur.NOIR.getImagePath()),SwingConstants.CENTER);
		lblScoreNoir.setForeground(Color.WHITE);
		lblScoreNoir.setHorizontalTextPosition(JLabel.CENTER);
		lblScoreNoir.setVerticalTextPosition(JLabel.CENTER);
		lblScoreBlanc = new JLabel(new ImageIcon(Couleur.BLANC.getImagePath()),SwingConstants.CENTER);
		lblScoreBlanc.setForeground(Color.BLACK);
		lblScoreBlanc.setHorizontalTextPosition(JLabel.CENTER);
		lblScoreBlanc.setVerticalTextPosition(JLabel.CENTER);
		lblMessageTourDe = new JLabel("Tour de : ");
		lblCouleurJouant = new JLabel(new ImageIcon(Couleur.NOIR.getImagePath()));

		JPanel panelLabel = new JPanel();
		panelLabel.add(lblMessageTourDe);
		panelLabel.add(lblCouleurJouant);
		this.add(lblScoreBlanc,BorderLayout.WEST);
		this.add(lblScoreNoir,BorderLayout.EAST);
		this.add(panelBoutonsSlider,BorderLayout.NORTH);
		this.add(panelLabel,BorderLayout.CENTER);
	}
	
	/**
	 * Méthode qui gère l'ajout et la création des listeners des différents composants du panel.
	 */
	private void ajoutListeners()
	{
		boutonRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.arreterReplay();
				OthelloMain.afficherPartiesPrecedentes();
			}
		});
		
		boutonLectureArret.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(boutonLectureArret.getText().equalsIgnoreCase("lecture"))
				{
					boutonLectureArret.setText("Arret");
					
				}
				else if(boutonLectureArret.getText().equalsIgnoreCase("arret"))
				{
					boutonLectureArret.setText("Lecture");
				}
				if(!replayLancer)
				{
					jeu.lancerReplay();
					replayLancer = true;
					comboVitesse.setEnabled(true);
					boutonRetourArriere.setEnabled(true);
					boutonAvance.setEnabled(true);
					sliderReplay.setEnabled(true);
				}
				else
				{
					jeu.arreterRelancerReplay();
				}
				
			}
		});
		
		comboVitesse.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				jeu.choixVitesseReplay((String)e.getItem());
				
			}
		});
		
		boutonAvance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boutonLectureArret.setText("Lecture");
				sliderReplay.setValue(sliderReplay.getValue()+1);
			}
		});
		
		boutonRetourArriere.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boutonLectureArret.setText("Lecture");
				sliderReplay.setValue(sliderReplay.getValue()-1);
			}
		});
		
		sliderReplay.addChangeListener(this);
	}
	
	
	/**
	 * Méthode qui set les textes des labels à l'aide du tableau passé en paramètre.
	 * @param tab tableau d'enteirs représentant le score (tab[0]->score de noir, tab[1]-> score de blanc).
	 */
	public void setScores(int[] tab)
	{
		this.lblScoreNoir.setText("" + tab[0]);
		this.lblScoreBlanc.setText("" + tab[1]);
	}
	
	/**
	 * Méthode qui set le label de la couleur jouant à l'aide de la couleur passée en paramètre.
	 */
	public void setLabelCouleurJouant(Couleur couleurJouant)
	{
		if(couleurJouant.equals(Couleur.BLANC))
		{
			lblCouleurJouant.setIcon(new ImageIcon(Couleur.BLANC.getImagePath()));
		}
		else if(couleurJouant.equals(Couleur.NOIR))
		{
			lblCouleurJouant.setIcon(new ImageIcon(Couleur.NOIR.getImagePath()));
		}
	}
	
	/**
	 * Méthode qui retourne le slider.
	 * @return le slider du menu.
	 */
	public JSlider getSlider()
	{
		return sliderReplay;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		boutonLectureArret.setText("Lecture");
		jeu.setMomentDuReplay(sliderReplay.getValue());
	}
	
}
