import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class représentant la fenetre de victoire permettant aussi de choisir d'enregistrer la partie,
 * et de rejouer ou de revenir au menu principal.
 * @author Vrand
 *
 */
public class PopUpVictoire extends JFrame {
	
	private static final long serialVersionUID = 3433218876115483492L;
	
	/**
	 * Bouton permettant de rejouer la partie.
	 */
	private JButton boutonRejouer = null;
	
	/**
	 * Bouton permettant de revenir au menu principal.
	 */
	private JButton boutonRevenirMainMenu = null;
	
	/**
	 * label affichant quel joueur à gagner la partie.
	 */
	private JLabel messageVictoire = null;
	
	/**
	 * Bouton qui permet d'enregistrer la partie.
	 */
	private JButton boutonEnregistrer = null;
	
	/**
	 * Jeu auquel est associé cette popup.
	 */
	private Jeu jeu = null;
	
	/**
	 * Instancie une nouvelle fenetre avec le message de victoire.
	 * @param message à afficher sur la popup pour la victoire.
	 * @param jeu auquel la popup est relié.
	 */
	public PopUpVictoire(String message,Jeu jeu)
	{
		super("Fin de partie");
		this.jeu = jeu;
		this.load(message);
		ajouterListenerBoutons();
	}
	
	/**
	 * Méthode qui crée et charge les différent panel et composants graphiques de la fenêtre.
	 * @param msg Message à afficher pour montrer le joueur vainqueur.
	 */
	private void load(String msg)
	{
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.boutonRejouer = new JButton("Rejouer");
		this.boutonRevenirMainMenu = new JButton("Revenir au menu principal");
		this.messageVictoire = new JLabel(msg);
		this.boutonEnregistrer = new JButton("Enregistrer");
		
		JPanel panelLabel = new JPanel();
		panelLabel.add(messageVictoire);
		this.getContentPane().add(panelLabel,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(boutonRejouer);
		buttonPanel.add(boutonRevenirMainMenu);
		buttonPanel.add(boutonEnregistrer);
		
		this.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		this.pack();
	}
	
	/**
	 * Méthode qui crée et ajoute les différents listener des boutons présent sur la fenêtre.
	 */
	private void ajouterListenerBoutons()
	{
		this.boutonRejouer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OthelloMain.rejouer();
				JButton button = (JButton)arg0.getSource();
				JFrame frame = (JFrame)button.getTopLevelAncestor();
				frame.dispose();
			}
		});
		
		this.boutonRevenirMainMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherMenuPrincipal();
				JButton button = (JButton)e.getSource();
				JFrame frame = (JFrame)button.getTopLevelAncestor();
				frame.dispose();
			}
		});
		
		this.boutonEnregistrer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boutonEnregistrer.setEnabled(false);
				jeu.enregistrerPartie();
			}
		});
	}

}
