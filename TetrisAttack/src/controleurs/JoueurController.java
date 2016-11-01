package controleurs;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import donnees.EtatPartie;
import donnees.Humain;
import donnees.Joueur;
import donnees.JoueurIA;
import donnees.PartieVersus;
import donnees.ThreadCombo;
import vues.BriquePrevention;
import vues.JoueurView;

/**
 * Controleur d'un joueur en générale.
 * @author Sacha
 *
 */
public abstract class JoueurController extends Observable implements Observer{
	
	/**
	 * Joueur que l'on controle.
	 */
	protected Joueur joueur;
	
	/**
	 * Vue du joueur que l'on controle.
	 */
	protected JoueurView joueurView;
	
	/**
	 * Controleur de la grille que ce joueur possède.
	 */
	protected GrilleController grilleController;
	
	/**
	 * Controleur du joueur adverse.
	 */
	protected JoueurController opponentJC;
	
	/**
	 * {@link KeyListener} du joueur.
	 */
	protected KeyListener keyListener = null;
	
	/**
	 * Booléén qui permet d'empecher l'échange de blocs et la demande de montée lors du compte à rebours.
	 */
	protected boolean echangeAutorise;
	
	/**
	 * Liste de tous les threads en cours à un instant t.
	 */
	private ArrayList<Thread> threadsLances;

	/**
	 * Constructeur de {@link JoueurController}. Instancie un nouveau {@link JoueurController} 
	 * avec une nouvelle instance de {@link GrilleController}, la liste des threads lancés à vide, et l'echange autorisé à false.
	 * Et un controleur pour le joueur adverse à null.
	 * @param joueur modèle du joueur que l'on controle.
	 * @param joueurView Vue du joueur que l'on controle.
	 */
	public JoueurController(Joueur joueur, JoueurView joueurView) {
		this.joueur = joueur;
		this.joueurView = joueurView;
		this.grilleController = new GrilleController(joueur.getGrille(), joueurView.getGrilleView(), this);
		this.echangeAutorise = false;
		this.threadsLances = new ArrayList<>();
	}
	
	/**
	 * Fonction qui permet de créer le controleur adapté pour le type de joueur.
	 * @param joueur modèle du joueur.
	 * @param joueurView vue du joueur.
	 * @return Controleur pour un humain si ce joueur est un humain, sinon un controleur pour une ia.
	 */
	public static JoueurController createNewJoueurControlleur(Joueur joueur, JoueurView joueurView)
	{
		if(joueur instanceof JoueurIA)
		{
			return new IAController((JoueurIA)joueur, joueurView);
		}
		else if(joueur instanceof Humain)
		{
			return new HumainControlleur((Humain)joueur,joueurView);
		}
		return null;
	}
	
	/**
	 * Méthode qui retourne le {@link KeyListener} du joueur.
	 * @return le {@link KeyListener} du joueur.
	 */
	public KeyListener getKeyListener() {
		return keyListener;
	}
	
	/**
	 * Méthode qui retourne le controleur de la grille du joueur.
	 * @return le controleur de la grille du joueur.
	 */
	public GrilleController getGrilleController() {
		return grilleController;
	}
	
	/**
	 * Méthode qui permet de set le temps que met une montée dans le {@link GrilleController}.
	 * @param tempsAvantMontee nouveau temps avant une montée.
	 */
	public void setTempsAvantMontee(int tempsAvantMontee) {
		this.grilleController.setTempsAvantMontee(tempsAvantMontee);
	}
	
	/**
	 * Méthode qui permet de set l'autorisation d'échange.
	 * @param echangeAutorise Vrai si l'on peut, faux sinon.
	 */
	public void setEchangeAutorise(boolean echangeAutorise) {
		this.echangeAutorise = echangeAutorise;
	}
	
	/**
	 * Méthode qui set le controleur du joueur adverse.
	 * @param opponentJC controleur du joueur adverse.
	 */
	public void setOpponentJC(JoueurController opponentJC) {
		this.opponentJC = opponentJC;
	}

	/**
	 * Méthode qui permet de lancer un {@link ThreadCombo}.
	 * L'ajoute aux threads lancés.
	 */
	public synchronized void checkCombosThread()
	{
		ThreadCombo tc = new ThreadCombo(this, opponentJC, this.getJoueur().getScore());
		tc.addObserver(this);
		Thread t = new Thread(tc);
		threadsLances.add(t);
		t.start();
	}
	
	/**
	 * Méthode qui permet de dire que ce joueur à perdu. Notifie les observateurs.
	 */
	public void hasLost()
	{
		super.setChanged();
		super.notifyObservers(this.joueur);
	}
	
	/**
	 * Méthode qui retourne le modele du joueur.
	 * @return le modele du joueur.
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Méthode qui retourne la vue de ce joueur.
	 * @return la vue de ce joueur.
	 */
	public JoueurView getJoueurView() {
		return joueurView;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PartieVersus)
		{
			PartieVersus p = (PartieVersus)o;
			if(p.getEtatPartie().equals(EtatPartie.COMPTEAREBOURS))
			{
				this.echangeAutorise = false;
			}
			else if(p.getEtatPartie().equals(EtatPartie.INTERRUPTED) || p.getEtatPartie().equals(EtatPartie.TRANSITIONMANCHE))
			{
				this.shutdownThreads();
				this.threadsLances.clear();
			}
			else
			{
				this.echangeAutorise = true;
			}
		}
		else if(o instanceof ThreadCombo)
		{
			threadsLances.remove(Thread.currentThread());
		}
	}
	
	/**
	 * Méthode qui interrompt tous les threads lancée à cette instant.
	 */
	public void shutdownThreads()
	{
		for(Thread t : threadsLances)
		{
			t.interrupt();
		}
	}
	
	/**
	 * Méthode qui permet de prévenir d'une brique que le joueur adverse nous envoie.
	 * @param longueur longueur de la brique.
	 * @param hauteur hauteur de la brique.
	 */
	public void prevenirBrique(int longueur, int hauteur)
	{
		joueurView.getPanelPrev().prevenirBrique(BriquePrevention.recupTypePrevBrique(longueur, hauteur));
	}
	
	/**
	 * Méthode qui permet de finir la "prévention" d'une brique.
	 * @param longueur longueur de la brique.
	 * @param hauteur hauteur de la brique.
	 */
	public void finPrevention(int longueur, int hauteur)
	{
		joueurView.getPanelPrev().finPreventionBrique(BriquePrevention.recupTypePrevBrique(longueur, hauteur));
	} 
}
