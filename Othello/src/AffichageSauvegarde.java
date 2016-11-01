import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Classe g�rant l'affichage d'une sauvegarde.
 * Repr�sente un panel compos� du menu replay et du plateau.
 * Elle permet d'afficher une sauvegarde et de la visionner.
 * @author Vrand
 *
 */
public class AffichageSauvegarde extends JPanel
{

	private static final long serialVersionUID = 4520475839970460807L;
	
	/**
	 * Panel qui sera afficher en haut de la fen�tre repr�sentant le menu.
	 */
	private MenuReplay menu;
	
	/**
	 * Panel qui sera affich�e en bas de la fen�tre rep�resentant le plateau de jeu.
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
	 * Liste des coups succesifs � jouer pour rejouer la sauvegarde correctement. Doit contenir des Objets de type Pion et Icon.
	 */
	private ArrayList<Object> listeAJouer;
	
	/**
	 * Indice permettant de jouer les differents coups et messages de la liste de coup � jouer. Ne peut pas �tre n�gatif.
	 */
	private int indiceAJouer;
	
	/**
	 * Constructeur de la classe AffichageSauvegarde.
	 * Cr�e un nouveau panel avec comme layout manager un BorderLayout, compos� d'un MenuReplay et d'un Plateau.
	 * Le plateau est instancier avec tous les pions present en fin de partie. Sur chaque pion est affich� le coup et la couleur qui l'a jou�e. 
	 * Recup�re aussi la liste des coups � jouer de la sauvegarde en param�tre pour "rejouer" la partie.
	 * @param save -> Sauvegarde � afficher 
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
	 * M�thode qui g�re le chargement des composants graphiques du panel
	 */
	private void load()
	{
		this.add(menu,BorderLayout.NORTH);
		this.add(plateau,BorderLayout.SOUTH);
		lblTourPasse = new JLabel();
	}
	
	/**
	 * M�thode qui g�re la cr�ation du timer de jeu et du timer de passage de tour.
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
	 * Methode qui g�re la cr�ation et le chargement des composants graphiques du glass pane.
	 */
	private void loadGlass()
	{
		glass = (JPanel)OthelloMain.mainFrame.getGlassPane();
		glass.setLayout(new BorderLayout());
		glass.add(lblTourPasse,BorderLayout.SOUTH);
		glass.setVisible(true);
	}
	
	/**
	 * M�thode qui permet de changer la vitesse du replay en fonction de l'item choisi.
	 * @param itemChoisi Chaine repr�sentant la vitesse qui doit etre prise pour le timer de jeu.
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
	 * M�thode qui lance le replay.
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
	 * M�thode qui g�re l'arret et la lecture de la sauvegarde.
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
	 * M�thode qui compte les points et les retourne sous un tableau d'entier.
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
	 * Met � jour le replay pour qu'il affiche le tour � l'indice en param�tre, ex : indice = 0, l'affichage du plateau sera premier tour.
	 * @param indice du tour � afficher.
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
	 * M�thode qui retourne la taille de la listeAJouer
	 */
	public int getSizeListeAJouer()
	{
		return listeAJouer.size();
	}
	
	/**
	 * M�thode qui retourne la couleur jouant ce tour.
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
	 * M�thode qui retourne l'indice � jouer.
	 * @return l'indice � jouer.
	 */
	public int getIndiceAJouer()
	{
		return this.indiceAJouer;
	}
	
	/**
	 * M�thode qui arrete le replay
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
