package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.Timer;

import donnees.EtatPartie;
import donnees.Joueur;
import donnees.PartieVersus;
import principal.RessourcesAudio;
import vues.FenetrePrincipal;
import vues.PartieVersusView;

/**
 * Controleur d'un partie versus.
 * @author Sacha
 *
 */
public class PartieVersusController implements Observer{

	/**
	 * Temps, de départ,que met une ligne a montée sur la grille.
	 */
	public final static int DELAYNEWLINE = 12000;
	
	/**
	 * Réduction appliquée au temps d'une montée à chaque "tour" du timer.
	 */
	public final static int TIMERPROGRESSION = 500;
	
	/**
	 * Temps minimum que peut atteindre le temps d'une montée au cours de la partie.
	 */
	public final static int MINDELAY = 6000;
	
	/**
	 * Controleur de temps en jeu, que ce controleur possède.
	 */
	private TimeController timeController;
	
	/**
	 * Controleur du joueur 1 que ce controleur possède.
	 */
	private JoueurController jcJ1;
	
	/**
	 * Controleur du joueur 2 que ce controleur possède.
	 */
	private JoueurController jcJ2;
	
	/**
	 * Modele de la partie que l'on controle.
	 */
	private PartieVersus partieVersus;
	
	/**
	 * Vue de la partie que l'on controle.
	 */
	private PartieVersusView partieVersusView;
	
	/**
	 * Temps que doit mettre une montée de ligne.
	 */
	private int tempsAvantMontee;
	
	/**
	 * Timer gérant la progression du temps d'une montée.
	 */
	private Timer timerTempsAvantMontee;
	
	/**
	 * Booléén permettant de bloquer les actions de l'utilisateur tant que le son de selection se joue.
	 */
	private boolean enAttente;
	
	/**
	 * Constructeur de {@link PartieVersusController}. Instancie un {@link PartieVersusController} avec
	 * l'intialisation des listeners et timers neccessaire à son fonctionnement. Ainsi que le controleur de chaque joueur.
	 * Set aussi le controleur advserse de chaque controleur de joueur.
	 * @param partieVersus modele de la partie que l'on controle.
	 * @param partieVersusView vue de la partie que l'on controle.
	 */
	public PartieVersusController(PartieVersus partieVersus, PartieVersusView partieVersusView) {
		super();
		this.partieVersus = partieVersus;
		this.partieVersusView = partieVersusView;
		this.jcJ1 = JoueurController.createNewJoueurControlleur(partieVersus.getJoueur1(), partieVersusView.getJoueurViewJ1());
		this.jcJ2 = JoueurController.createNewJoueurControlleur(partieVersus.getJoueur2(), partieVersusView.getJoueurViewJ2());
		this.jcJ1.setOpponentJC(jcJ2);
		this.jcJ2.setOpponentJC(jcJ1);
		this.timeController = new TimeController(partieVersusView.getTimeView(), partieVersus.getTime());
		tempsAvantMontee = DELAYNEWLINE;
		this.enAttente = false;
		initListeners();
		initObservers();
	}
	
	/**
	 * Méthode qui permet d'initialiser les listeners et timers.
	 */
	private void initListeners()
	{
		timerTempsAvantMontee = new Timer(tempsAvantMontee, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tempsAvantMontee - TIMERPROGRESSION > MINDELAY)
				{
					tempsAvantMontee -= TIMERPROGRESSION;
					timerTempsAvantMontee.setDelay(tempsAvantMontee);
					jcJ1.setTempsAvantMontee(tempsAvantMontee);
					jcJ2.setTempsAvantMontee(tempsAvantMontee);
				}
				else if(tempsAvantMontee > MINDELAY)
				{
					tempsAvantMontee = MINDELAY;
					timerTempsAvantMontee.setDelay(tempsAvantMontee);
					jcJ1.setTempsAvantMontee(tempsAvantMontee);
					jcJ2.setTempsAvantMontee(tempsAvantMontee);
				}
			}
		});
		
		partieVersusView.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {	
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(!enAttente)
				{
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
						RessourcesAudio.lancerSonRetour();
						partieVersus.setEtatPartie(EtatPartie.INTERRUPTED);
						FenetrePrincipal.afficherMenuPrincipal();
					}
					else if(partieVersus.getEtatPartie().equals(EtatPartie.WAITING))
					{
						RessourcesAudio.arreterMusiqueResultats();
						RessourcesAudio.lancerSonSelection(new LineListener() {
							
							@Override
							public void update(LineEvent event) {
								if(event.getType() == LineEvent.Type.CLOSE)
								{
									debuterPartie();
									enAttente = false;
								}
							}
						});
						enAttente = true;
					}
				}
			}
		});
	}
	
	/**
	 * Méthode qui permet d'ajouter tous les observateurs aux observables de la partie.
	 */
	private void initObservers()
	{
		this.partieVersus.addObserver(jcJ1);
		this.partieVersus.addObserver(jcJ2);
		this.partieVersus.addObserver(jcJ1.getGrilleController());
		this.partieVersus.addObserver(jcJ2.getGrilleController());
		this.partieVersus.addObserver(timeController);
		this.partieVersus.addObserver(jcJ1.getJoueurView());
		this.partieVersus.addObserver(jcJ2.getJoueurView());
		partieVersus.getJoueur1().addObserver(partieVersusView.getInGameInfos());
		partieVersus.getJoueur2().addObserver(partieVersusView.getInGameInfos());
		this.jcJ1.addObserver(this);
		this.jcJ2.addObserver(this);
		this.partieVersus.addObserver(this.jcJ1.getJoueurView().getPanelPrev());
		this.partieVersus.addObserver(this.jcJ2.getJoueurView().getPanelPrev());
		this.partieVersus.addObserver(this);
	}
	
	/**
	 * Méthode qui permet de lancer une partie. Lance le compte a rebours puis met la partie en jeu.
	 */
	public void debuterPartie()
	{
		Thread debutPartie = new Thread(new Runnable() {
			public void run() {
				partieVersus.setEtatPartie(EtatPartie.COMPTEAREBOURS);
				partieVersusView.addKeyListener(jcJ1.getKeyListener());
				partieVersusView.addKeyListener(jcJ2.getKeyListener());
				try {
					Thread.sleep(4010);
				} catch (InterruptedException e) {
					return;
				}
				if(partieVersus.getEtatPartie().equals(EtatPartie.INTERRUPTED))
					return;
				partieVersus.setEtatPartie(EtatPartie.ENJEU);
				RessourcesAudio.lancerMusiqueJeu();
			}
		});
		debutPartie.start();
	}
	
	/**
	 * Méthode qui permet de vider les deux grilles des joueurs.
	 */
	private void viderGrilles()
	{
		jcJ1.getGrilleController().viderGrille();
		jcJ2.getGrilleController().viderGrille();
	}
	
	/**
	 * Méthode qui permet de faire la transition entre chaque manche.
	 * @param premier joueur ayant perdu la manche
	 * @param deuxieme autre joueur.
	 */
	private synchronized void faireTransition(JoueurController premier, JoueurController deuxieme)
	{
		premier.getGrilleController().faireUnAllerTransition();
		premier.getGrilleController().justWaitForThis();
		RessourcesAudio.lancerSonMur();
		
		deuxieme.getGrilleController().faireUnAllerTransition();
		deuxieme.getGrilleController().justWaitForThis();
		RessourcesAudio.lancerSonMur();
		
		premier.getJoueurView().afficherFinPartie(false);
		deuxieme.getJoueurView().afficherFinPartie(true);
		viderGrilles();
		
		premier.getGrilleController().faireUnAllerTransition();
		deuxieme.getGrilleController().faireUnAllerTransition();
		deuxieme.getGrilleController().justWaitForThis();
		
		deuxieme.getJoueur().ajouterMancheGagnee();
	}

	/**
	 * Méthode qui permet de dire à la partie qu'un joueur a perdu. Lance la transtion et autres. Et passe la partie en attente ou en fin.
	 * @param joueur Joueur qui a perdu.
	 */
	public synchronized void hasLost(Joueur joueur) {
		if(!partieVersus.getEtatPartie().equals(EtatPartie.ENJEU))
			return;
		
		partieVersusView.removeKeyListener(jcJ1.getKeyListener());
		partieVersusView.removeKeyListener(jcJ2.getKeyListener());
		
		RessourcesAudio.arreterMusiqueJeu();
		RessourcesAudio.lancerSonLose();
		partieVersus.setEtatPartie(EtatPartie.TRANSITIONMANCHE);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return;
		}
		
		if(joueur.equals(jcJ1.getJoueur()))
		{
			faireTransition(jcJ1, jcJ2);
		}
		else
		{
			faireTransition(jcJ2, jcJ1);
		}
		
		RessourcesAudio.lancerMusiqueResultat();
		if(jcJ1.getJoueur().getManchesGagnees() == 2 || jcJ2.getJoueur().getManchesGagnees() == 2)
		{
			partieVersus.setEtatPartie(EtatPartie.FIN);
		}
		else
		{
			partieVersus.setEtatPartie(EtatPartie.WAITING);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof JoueurController)
		{
			hasLost((Joueur)arg);
		}
		else if(o instanceof PartieVersus)
		{
			if(((PartieVersus) o).getEtatPartie().equals(EtatPartie.INTERRUPTED))
			{
				RessourcesAudio.arreterMusiqueResultats();
				RessourcesAudio.arreterMusiqueJeu();
				this.timerTempsAvantMontee.stop();
			}
			else if(((PartieVersus) o).getEtatPartie().equals(EtatPartie.COMPTEAREBOURS))
			{
				this.tempsAvantMontee = DELAYNEWLINE;
				timerTempsAvantMontee.setDelay(tempsAvantMontee);
				jcJ1.setTempsAvantMontee(tempsAvantMontee);
				jcJ2.setTempsAvantMontee(tempsAvantMontee);
				this.timerTempsAvantMontee.start();
			}
		}
	}

}
