import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe représentant le menu principal de l'othello.
 * Elle consiste en un panel composé de différent boutons.
 * @author Vrand
 *
 */
public class MenuPrincipal extends JPanel{

	private static final long serialVersionUID = -6029464576556678092L;
	
	/**
	 * Label contenant l'image du titre du jeu.
	 */
	private JLabel lblTitre = null;
	
	/**
	 * Bouton permettant de jouer un partie joueur contre joueur.
	 */
	private JButton boutonJoueurContreJoueur;
	
	/**
	 * Bouton permettant de lancer une partie Joueur contre IA.
	 */
	private JButton boutonJoueurContreIA;
	
	/**
	 * Bouton permettant d'afficher l'historique des parties enregistrées.
	 */
	private JButton boutonPrecParties;
	
	/**
	 * Bouton permettant de quitter le jeu.
	 */
	private JButton boutonQuitter;
	

	/**
	 * Instancie un nouveau panel avec comme layout manager un gridbaglayout.
	 */
	public MenuPrincipal()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.ajouterListenerBoutons();
	}
	
	/**
	 * Méthode qui crée et charge les différents composants graphiques du panel.
	 */
	private void load()
	{
		this.setBackground(new Color(0,153,75));
		boutonJoueurContreJoueur = new JButton("Joueur contre Joueur");
		boutonJoueurContreIA = new JButton("Joueur contre IA");
		boutonJoueurContreIA.setPreferredSize(boutonJoueurContreJoueur.getPreferredSize());
		boutonQuitter = new JButton("Quitter");
		boutonQuitter.setPreferredSize(boutonJoueurContreJoueur.getPreferredSize());
		boutonPrecParties = new JButton("Historique des parties");
		boutonPrecParties.setPreferredSize(boutonJoueurContreJoueur.getPreferredSize());
		lblTitre = new JLabel(Ressources.imageTitreJeu);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.ipadx = 25;
		gbc.ipady = 10;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(25, 25, 25, 25);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(lblTitre,gbc);
		gbc.gridy = 1;
		gbc.insets = new Insets(25, 25, 10, 25);
		this.add(boutonJoueurContreJoueur,gbc);
		gbc.insets = new Insets(10, 25, 10, 25);
		gbc.gridy = 2;
		this.add(boutonJoueurContreIA,gbc);
		gbc.gridy = 3;
		this.add(boutonPrecParties,gbc);
		gbc.gridy = 4;
		this.add(boutonQuitter,gbc);
	}
	
	/**
	 * Méthode qui ajoute et crée les listener des différents boutons.
	 */
	private void ajouterListenerBoutons()
	{
		boutonJoueurContreJoueur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherJeuJcj();
				
			}
		});
		
		boutonJoueurContreIA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherMenuChoixDifficultee();
				
			}
		});
		
		boutonPrecParties.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherPartiesPrecedentes();
				
			}
		});
		
		boutonQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
				
			}
		});
	}
}
