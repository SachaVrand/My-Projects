import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe représentant le menu du choix de la difficulté avant une partie contre l'ordinateur.
 * Elle représente un panel composé de plusieurs boutons de choix de difficulté.
 * @author Vrand
 *
 */
public class MenuChoixDifficulte extends JPanel{
	
	private static final long serialVersionUID = 9037466171383624223L;
	
	/**
	 * Label affichant le titre du menu.
	 */
	private JLabel labelTitreChoix = null;
	
	/**
	 * Bouton permettant de retourner au menu principal.
	 */
	private JButton boutonRetour = null;
	
	/**
	 * Bouton permettant de choisir la difficulté moyenne.
	 */
	private JButton boutonMoyen = null;
	
	/**
	 * Bouton permettant de choisir la difficulté facile.
	 */
	private JButton boutonFacile = null;
	
	/**
	 * Bouton permettant de choisir la difficulté difficile.
	 */
	private JButton boutonDifficile = null;
	
	/**
	 * Constructeur de la classe menu choix difficulté. 
	 * Crée un nouveau panel avec les composants graphiques du menu tel que les boutons de difficulté et leurs listeners.
	 * 
	 */
	public MenuChoixDifficulte()
	{
		super();
		this.load();
		this.ajoutListenerBouton();
	}
	
	/**
	 * Méthode qui gère le chragement des composants graphique du menu.
	 */
	private void load()
	{
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(0,153,75));
		boutonRetour = new JButton("Retour");
		boutonRetour.setPreferredSize(new Dimension(100, 25));
		boutonFacile = new JButton("Facile");
		boutonFacile.setPreferredSize(boutonRetour.getPreferredSize());
		boutonFacile.setForeground(new Color(34,139,34));
		boutonMoyen = new JButton("Moyen");
		boutonMoyen.setPreferredSize(boutonRetour.getPreferredSize());
		boutonMoyen.setForeground(new Color(255,165,0));
		boutonDifficile = new JButton("Difficile");
		boutonDifficile.setPreferredSize(boutonRetour.getPreferredSize());
		boutonDifficile.setForeground(new Color(178, 34, 34));
		labelTitreChoix = new JLabel(Ressources.imageTitreChoixDifficulté);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.ipadx = 25;
		gbc.ipady = 10;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(25, 25, 25, 25);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(labelTitreChoix,gbc);
		gbc.insets = new Insets(10, 25, 10, 25);
		gbc.gridy = 1;
		this.add(boutonFacile,gbc);
		gbc.gridy = 2;
		this.add(boutonMoyen,gbc);
		gbc.gridy = 3;
		this.add(boutonDifficile,gbc);
		gbc.gridy = 4;
		gbc.insets = new Insets(25, 25, 5, 25);
		this.add(boutonRetour,gbc);
	}
	
	/**
	 * Méthode qui gère la création et l'ajout des listener aux boutons de choix de difficultés
	 */
	private void ajoutListenerBouton()
	{
		this.boutonFacile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherJeuJcIAFacile();
			}
		});
		
		this.boutonMoyen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OthelloMain.afficherJeuJcIAMoyen();
				
			}
		});
		
		this.boutonDifficile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OthelloMain.afficherJeuJcIADifficile();
			}
		});
		
		this.boutonRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OthelloMain.afficherMenuPrincipal();
				
			}
		});
	}
}
