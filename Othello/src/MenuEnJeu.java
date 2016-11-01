import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Classe représentant le menu en jeu lors de parties JCJ ou JCIA.
 * Panel composé des différents boutons et labels.
 * @author Vrand
 *
 */
public class MenuEnJeu extends JPanel {
	
	private static final long serialVersionUID = -2903915625885527039L;
	
	/**
	 * Bouton permettant de revenir au menu principal.
	 */
	private JButton boutonRetour;
	
	/**
	 * Bouton permettant de rejouer la partie.
	 */
	private JButton boutonRejouer;
	
	/**
	 * Label affichant le score de NOIR.
	 */
	private JLabel lblScoreNoir;
	
	/**
	 * Label affichant le score de BLANC.
	 */
	private JLabel lblScoreBlanc;
	
	/**
	 * Label affichant la couleur jouant ce tour.
	 */
	private JLabel lblCouleurJouant;
	
	/**
	 * Label affichant le message "Tour de : "
	 */
	private JLabel lblMessageTour;
	
	/**
	 * Label affaichant le temps écoulé depuis le début de la partie.
	 */
	private JLabel lblChrono;
	
	/**
	 * Timer permettant de mettre à jour le temps écoulé.
	 */
	private Timer timerChrono;
	
	/**
	 * Entier permettant de savoir quand à débuter la partie pour calculer le temps écoulé.
	 */
	private long startTime;
	
	/**
	 * Crée un panel avec comme layout manager un BorderLayout. 
	 * Il initialise le temps de départ et charge les composants graphiques.
	 */
	public MenuEnJeu()
	{
		super(new BorderLayout());
		startTime = System.currentTimeMillis();
		this.load();
		this.ajoutListenersBoutons();
		this.loadTimer();
	}
	
	/**
	 * Méthode qui gère la création et le chargement des différents composants graphiques.
	 */
	private void load()
	{
		lblCouleurJouant = new JLabel();
		lblMessageTour = new JLabel("Tour de : ");
		lblScoreNoir = new JLabel("2",new ImageIcon(Couleur.NOIR.getImagePath()),SwingConstants.CENTER);
		lblScoreNoir.setForeground(Color.WHITE);
		lblScoreNoir.setHorizontalTextPosition(JLabel.CENTER);
		lblScoreNoir.setVerticalTextPosition(JLabel.CENTER);
		lblScoreBlanc = new JLabel("2",new ImageIcon(Couleur.BLANC.getImagePath()),SwingConstants.CENTER);
		lblScoreBlanc.setForeground(Color.BLACK);
		lblScoreBlanc.setHorizontalTextPosition(JLabel.CENTER);
		lblScoreBlanc.setVerticalTextPosition(JLabel.CENTER);
		lblChrono = new JLabel(getTime());
		JPanel panelChrono = new JPanel();
		panelChrono.add(lblChrono);
		boutonRetour = new JButton("Retour");
		boutonRejouer = new JButton("Rejouer");
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(boutonRetour);
		panelBoutons.add(boutonRejouer);
		JPanel panelTour = new JPanel();
		panelTour.add(lblMessageTour,BorderLayout.EAST);
		panelTour.add(lblCouleurJouant,BorderLayout.WEST);
		this.add(panelTour,BorderLayout.CENTER);
		this.add(panelBoutons,BorderLayout.NORTH);
		this.add(lblScoreBlanc,BorderLayout.WEST);
		this.add(lblScoreNoir,BorderLayout.EAST);
		this.add(panelChrono,BorderLayout.SOUTH);
	}
	
	/**
	 * Méthode qui gère la création du timer de temps de jeu. Et démarre ce timer.
	 */
	private void loadTimer()
	{
		timerChrono = new Timer(1000,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblChrono.setText(getTime());
				
			}
		});
		timerChrono.start();
	}
	
	/**
	 * Méthode qui gère l'ajout et la création des listeners des différents boutons du panel.
	 */
	private void ajoutListenersBoutons()
	{
		boutonRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherMenuPrincipal();	
			}
		});
		boutonRejouer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.rejouer();
				
			}
		});
	}
	
	/**
	 * Méthode qui set les label de scores avec le tableau passé en paramètre.
	 * @param points tab[0] -> score noir, tab[1] -> score blanc.
	 */
	public void setPoints(int[] points)
	{
		this.lblScoreNoir.setText("" + points[0]);
		this.lblScoreBlanc.setText("" + points[1]);
	}
	
	/**
	 * Méthode qui set le label affichant la couleur qui doit jouer pendant ce tour, avec la couleur passée en paramètre.
	 * @param couleur Couleur jouant ce tour.
	 */
	public void setLabelCouleurJouant(Couleur couleur)
	{
		if(couleur.equals(Couleur.NOIR))
		{
			lblCouleurJouant.setIcon(new ImageIcon(Couleur.NOIR.getImagePath()));
		}
		else if(couleur.equals(Couleur.BLANC))
		{
			lblCouleurJouant.setIcon(new ImageIcon(Couleur.BLANC.getImagePath()));
		}
	}
	
	/**
	 * Méthode qui retourne sous la forme HH:MM:SS le temps de jeu depuis le début de la partie.
	 * @return chaine représentant le temps joué sous la forme HH:MM:SS
	 */
	public String getTime()
	{
		long time = System.currentTimeMillis();
		long secondes = ((time - startTime)/1000)%60;
		long minutes = (((time - startTime)/1000)/60)%60;
		long heures = ((time - startTime)/1000)/3600;
		
		
		String minutesChaine = "" + minutes;
		if(minutesChaine.length() <= 1)
		{
			minutesChaine = "0" + minutesChaine;
		}
		String secondesChaine = "" + secondes;
		if(secondesChaine.length() <= 1)
		{
			secondesChaine = "0" + secondesChaine;
		}
		return heures + ":" +  minutesChaine + ":" + secondesChaine;
	}
	
	/**
	 * Méthode qui arrète le temps de jeu.
	 */
	public void arreterChrono()
	{
		timerChrono.stop();
	}

}
