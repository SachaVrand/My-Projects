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
 * Classe repr�sentant le menu en jeu lors de parties JCJ ou JCIA.
 * Panel compos� des diff�rents boutons et labels.
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
	 * Label affaichant le temps �coul� depuis le d�but de la partie.
	 */
	private JLabel lblChrono;
	
	/**
	 * Timer permettant de mettre � jour le temps �coul�.
	 */
	private Timer timerChrono;
	
	/**
	 * Entier permettant de savoir quand � d�buter la partie pour calculer le temps �coul�.
	 */
	private long startTime;
	
	/**
	 * Cr�e un panel avec comme layout manager un BorderLayout. 
	 * Il initialise le temps de d�part et charge les composants graphiques.
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
	 * M�thode qui g�re la cr�ation et le chargement des diff�rents composants graphiques.
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
	 * M�thode qui g�re la cr�ation du timer de temps de jeu. Et d�marre ce timer.
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
	 * M�thode qui g�re l'ajout et la cr�ation des listeners des diff�rents boutons du panel.
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
	 * M�thode qui set les label de scores avec le tableau pass� en param�tre.
	 * @param points tab[0] -> score noir, tab[1] -> score blanc.
	 */
	public void setPoints(int[] points)
	{
		this.lblScoreNoir.setText("" + points[0]);
		this.lblScoreBlanc.setText("" + points[1]);
	}
	
	/**
	 * M�thode qui set le label affichant la couleur qui doit jouer pendant ce tour, avec la couleur pass�e en param�tre.
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
	 * M�thode qui retourne sous la forme HH:MM:SS le temps de jeu depuis le d�but de la partie.
	 * @return chaine repr�sentant le temps jou� sous la forme HH:MM:SS
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
	 * M�thode qui arr�te le temps de jeu.
	 */
	public void arreterChrono()
	{
		timerChrono.stop();
	}

}
