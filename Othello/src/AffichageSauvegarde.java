import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Classe gérant l'affichage d'une sauvegarde.
 * Représente un panel composé du menu replay et du plateau.
 * Elle permet d'afficher une sauvegarde et de la visionner.
 * @author Vrand
 *
 */
public class AffichageSauvegarde extends JPanel
{

	private static final long serialVersionUID = 4520475839970460807L;
	
	/**
	 * Panel qui sera afficher en haut de la fenêtre représentant le menu.
	 */
	private MenuReplay menu;
	
	/**
	 * Panel qui sera affichée en bas de la fenêtre repéresentant le plateau de jeu.
	 */
	private Plateau plateau;
	
	/**
	 * Timer qui permet de faire jouer la sauvegarde d'une facon plus ou moins lente.
	 */
	private Timer timerJeu;
	
	/**
	 * Timer qui permet d'afficher le passage des tours.
	 */
	private Timer timerPassageTour;
	
	/**
	 * Label qui affiche le message de passage de tour.
	 */
	private JLabel lblTourPasse = null;
	
	/**
	 * Panel qui correspond au glass pane de la main frame.
	 */
	private JPanel glass = null;
	
	/**
	 * Liste des coups succesifs à jouer pour rejouer la sauvegarde correctement. Doit contenir des Objets de type Pion et Icon.
	 */
	private ArrayList<Object> listeAJouer;
	
	/**
	 * Indice permettant de jouer les differents coups et messages de la liste de coup à jouer. Ne peut pas être négatif.
	 */
	private int indiceAJouer;
	
	/**
	 * Constructeur de la classe AffichageSauvegarde.
	 * Crée un nouveau panel avec comme layout manager un BorderLayout, composé d'un MenuReplay et d'un Plateau.
	 * Le plateau est instancier avec tous les pions present en fin de partie. Sur chaque pion est affiché le coup et la couleur qui l'a jouée. 
	 * Recupère aussi la liste des coups à jouer de la sauvegarde en paramètre pour "rejouer" la partie.
	 * @param save -> Sauvegarde à afficher 
	 */
	public AffichageSauvegarde(Sauvegarde save)
	{
		super(new BorderLayout());
		this.listeAJouer = save.getListeAJouer();
		this.indiceAJouer = 0;
		plateau = new Plateau(save.getTab());
		menu = new MenuReplay(this);
		menu.setScores(save.getScoreIntTab());
		this.load();
		this.loadTimer();
	}
	
	/**
	 * Méthode qui gère le chargement des composants graphiques du panel
	 */
	private void load()
	{
		this.add(menu,BorderLayout.NORTH);
		this.add(plateau,BorderLayout.SOUTH);
		lblTourPasse = new JLabel();
	}
	
	/**
	 * Méthode qui gère la création du timer de jeu et du timer de passage de tour.
	 */
	private void loadTimer()
	{
		timerJeu = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(indiceAJouer + 1 < listeAJouer.size())
				{
					indiceAJouer++;
					if(listeAJouer.get(indiceAJouer) instanceof Couleur[][])
					{
						Couleur[][] tab = (Couleur[][])listeAJouer.get(indiceAJouer);
						AffichageSauvegarde.this.remove(plateau);
						plateau = new Plateau(tab);
						AffichageSauvegarde.this.add(plateau,BorderLayout.SOUTH);
						OthelloMain.mainFrame.pack();
						menu.setScores(getPoints());
						menu.setLabelCouleurJouant(getCouleurJouant());
					}
					else if(listeAJouer.get(indiceAJouer) instanceof Icon)
					{
						glass.setVisible(true);
						timerJeu.stop();
						menu.setLabelCouleurJouant(getCouleurJouant());
						lblTourPasse.setIcon((Icon)listeAJouer.get(indiceAJouer));
						timerPassageTour.start();
					}
					//Changer la valeur du slider sans notifier de changement de valeur au changelistener.
					menu.getSlider().removeChangeListener(menu);
					menu.getSlider().setValue(indiceAJouer);
					menu.getSlider().addChangeListener(menu);
				}
				else
				{
					timerJeu.stop();
				}
			}
		});
		
		timerPassageTour = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timerPassageTour.stop();
				lblTourPasse.setText("");
				glass.setVisible(false);
				timerJeu.start();
			}
		});
	}
	
	/**
	 * Methode qui gère la création et le chargement des composants graphiques du glass pane.
	 */
	private void loadGlass()
	{
		glass = (JPanel)OthelloMain.mainFrame.getGlassPane();
		glass.setLayout(new BorderLayout());
		glass.add(lblTourPasse,BorderLayout.SOUTH);
		glass.setVisible(true);
	}
	
	/**
	 * Méthode qui permet de changer la vitesse du replay en fonction de l'item choisi.
	 * @param itemChoisi Chaine représentant la vitesse qui doit etre prise pour le timer de jeu.
	 */
	public void choixVitesseReplay(String itemChoisi)
	{
		if(itemChoisi.equals("Normal"))
		{
			timerJeu.setDelay(1000);
			timerJeu.setInitialDelay(1000);
			timerPassageTour.setDelay(1000);
			timerPassageTour.setInitialDelay(1000);
		}
		else if(itemChoisi.equals("Lent"))
		{
			timerJeu.setDelay(2500);
			timerJeu.setInitialDelay(2500);
			timerPassageTour.setDelay(2500);
			timerPassageTour.setInitialDelay(2500);
		}
		else if(itemChoisi.equals("Rapide"))
		{
			timerJeu.setDelay(300);
			timerJeu.setInitialDelay(300);
			timerPassageTour.setDelay(300);
			timerPassageTour.setInitialDelay(300);
		}
		timerJeu.restart();
		timerPassageTour.restart();
	}
	
	/**
	 * Méthode qui lance le replay.
	 * Instancie un nouveau plateau vide et demarre le timer de jeu.
	 */
	public void lancerReplay()
	{
		this.remove(plateau);
		plateau = new Plateau((Couleur[][])listeAJouer.get(indiceAJouer));
		this.add(plateau,BorderLayout.SOUTH);
		OthelloMain.mainFrame.pack();
		if(glass == null)
		{
			loadGlass();
		}
		timerJeu.start();
	}
	
	/**
	 * Méthode qui gère l'arret et la lecture de la sauvegarde.
	 */
	public void arreterRelancerReplay()
	{
		if(timerJeu.isRunning())
		{
			this.timerJeu.stop();
		}
		else if( timerPassageTour.isRunning())
		{
			this.timerPassageTour.stop();
		}
		else
		{
			if(listeAJouer.get(indiceAJouer) instanceof Icon)
			{
				this.timerPassageTour.start();
			}
			else if(listeAJouer.get(indiceAJouer) instanceof Couleur[][])
			{
				this.timerJeu.start();
			}
		}
	}
	
	/**
	 * Méthode qui compte les points et les retourne sous un tableau d'entier.
	 * @return res[0]->Points de NOIR, res[1]-> Points de BLANC
	 */
	public int[] getPoints()
	{
		int compteurNoir = 0,compteurBlanc = 0;
		Couleur[][] tableauPion = (Couleur[][])listeAJouer.get(indiceAJouer);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tableauPion[i][j].equals(Couleur.NOIR))
				{
					compteurNoir++;
				}
				else if(tableauPion[i][j].equals(Couleur.BLANC))
				{
					compteurBlanc++;
				}
			}
		}
		int[] res = {compteurNoir,compteurBlanc};
		return res;
	}
	
	/**
	 * Met à jour le replay pour qu'il affiche le tour à l'indice en paramètre, ex : indice = 0, l'affichage du plateau sera premier tour.
	 * @param indice du tour à afficher.
	 */
	public void setMomentDuReplay(int indice)
	{
		if(timerJeu.isRunning())
		{
			this.timerJeu.stop();
		}
		else if( timerPassageTour.isRunning())
		{
			this.timerPassageTour.stop();
		}
		
		indiceAJouer = indice;
		
		if(listeAJouer.get(indiceAJouer) instanceof Couleur[][])
		{
			glass.setVisible(false);
			this.remove(plateau);
			plateau = new Plateau((Couleur[][])listeAJouer.get(indiceAJouer));
			this.add(plateau,BorderLayout.SOUTH);
			OthelloMain.mainFrame.pack();
			menu.setScores(getPoints());
		}
		else if(listeAJouer.get(indiceAJouer) instanceof Icon)
		{
			lblTourPasse.setIcon((Icon)listeAJouer.get(indiceAJouer));
			glass.setVisible(true);
		}
		menu.setLabelCouleurJouant(getCouleurJouant());
	}
	
	/**
	 * Méthode qui retourne la taille de la listeAJouer
	 */
	public int getSizeListeAJouer()
	{
		return listeAJouer.size();
	}
	
	/**
	 * Méthode qui retourne la couleur jouant ce tour.
	 */
	public Couleur getCouleurJouant()
	{
		if(indiceAJouer%2 == 0)
		{
			return Couleur.NOIR;
		}
		else
		{
			return Couleur.BLANC;
		}
	}
	
	/**
	 * Méthode qui retourne l'indice à jouer.
	 * @return l'indice à jouer.
	 */
	public int getIndiceAJouer()
	{
		return this.indiceAJouer;
	}
	
	/**
	 * Méthode qui arrete le replay
	 */
	public void arreterReplay()
	{
		timerJeu.stop();
		timerPassageTour.stop();
		if(glass != null)
		{
			glass.setVisible(false);
			glass.setEnabled(false);
		}
	}
}
