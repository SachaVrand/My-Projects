package controleurs;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import donnees.EtatPartie;
import donnees.Grille;
import donnees.PartieVersus;
import vues.BlocView;
import vues.GrilleView;

/**
 * Controleur entre les classes {@link Grille} et {@link GrilleView}
 * @author Sacha
 *
 */
public class GrilleController implements Observer{
	
	/**
	 * Delai du sleep du timer, entre chaque pas de montée de la grille, lors de la demande d'une montée.
	 */
	private final static int FINISHCLIMBDELAY = 4;
	
	/**
	 * hauteur d'un blocView.
	 */
	private final static int BLOCHEIGHT = BlocView.BLOCHEIGHT;
	
	/**
	 * Temps avant qu'un joueur perde.
	 */
	private final static int DELAYCHECKLOOSE = 2000;
	
	/**
	 * Delai du sleep du timer, entre chaque pas de montée du mur, lors de la transition.
	 */
	private final static int TEMPSMONTEETRANSITION = 5;
	
	/**
	 * Instance de {@link GrilleView} dont on fait le controle.(Vue)
	 */
	private GrilleView grilleView;
	
	/**
	 * Instance de {@link Grille} dont on fait le controle.(Modele)
	 */
	private Grille grille;
	
	/**
	 * Timer qui gère la montée de la grille.
	 */
	private Timer climbTimer;
	
	/**
	 * Temps que doit mettre au total la grille pour montée d'une ligne.
	 */
	private int tempsAvantMontee;
	
	/**
	 * Controleur du joueur possèdant ce controleur.
	 */
	private JoueurController jc;
	
	/**
	 * Entier permettant de bloqué le timer de montée. Si à 0, alors il peut poursuivre, sinon non.
	 * Est utile pour qu'un thread qui arrive après un autre, et finnisse plus vite que ceui d'avant, 
	 * et remettent en route la montée alors que l'ancien n'as pas fini.
	 */
	private volatile int timersLocked;
	
	/**
	 * Timer gérant si le joueur perd ou non.
	 */
	private Timer loseTimer;
	
	/**
	 * Timer gérant l'animation de montée du mur pendant la transition.
	 */
	private Timer timerTransition;

	/**
	 * Constructeur de {@link GrilleController}. Instancie un nouveau controleur pour la grille. Initialise les timers et autres.
	 * @param grille modele que ce controleur controle.
	 * @param grilleView vue que ce controleur controle.
	 * @param jc Controleur de joueur possèdant ce controleur.
	 */
	public GrilleController(Grille grille, GrilleView grilleView, JoueurController jc) {
		this.grille = grille;
		this.grilleView = grilleView;
		this.jc = jc;
		this.tempsAvantMontee = PartieVersusController.DELAYNEWLINE;
		initClimbTimer();
		initLoseTimer();
		initTransitionTimer();
		this.timersLocked = 0;
	}

	/**
	 * Méthode qui retourne le modele de grille.
	 * @return le modele de grille.
	 */
	public Grille getGrille() {
		return grille;
	}
	
	/**
	 * Méthode qui retourne la vue de la grille.
	 * @return la vue de la grille.
	 */
	public GrilleView getGrilleView() {
		return grilleView;
	}
	
	/**
	 * Méthode qui permet de set le temps que met une montée de ligne.
	 * @param tempsAvantMontee le temps que met une montée de ligne.
	 */
	public void setTempsAvantMontee(int tempsAvantMontee) {
		this.tempsAvantMontee = tempsAvantMontee;
	}
	
	/**
	 * Bouge le curseur vers la gauche si possible.
	 * @return Vrai si il a pu etre bougé, faux sinon.
	 */
	public boolean moveCursorLeft()
	{
		Point tmpCursorPosition = grilleView.getCursorPosition();
		if(tmpCursorPosition.x != 0)
		{
			grilleView.paintCursor(new Point(tmpCursorPosition.x - 1, tmpCursorPosition.y));
			return true;
		}
		return false;
	}
	
	/**
	 * Bouge le curseur vers la drote si possible.
	 * @return vrai si il a pu être bougé, faux sinon.
	 */
	public boolean moveCursorRight()
	{
		Point tmpCursorPosition = grilleView.getCursorPosition();
		if(tmpCursorPosition.x != Grille.MAXBLOCPERLINE - 2)
		{
			grilleView.paintCursor(new Point(tmpCursorPosition.x + 1, tmpCursorPosition.y));
			return true;
		}
		return false;
	}
	
	/**
	 * Bouge le curseur vers le haut si possible.
	 * @return vrai s'il a pu etre montée, faux sinon.
	 */
	public boolean moveCursorUp()
	{
		Point tmpCursorPosition = grilleView.getCursorPosition();
		if(tmpCursorPosition.y != Grille.MAXLINES - 1)
		{
			grilleView.paintCursor(new Point(tmpCursorPosition.x, tmpCursorPosition.y + 1));
			return true;
		}
		return false;
	}
	
	/**
	 * Bouge le curseur vers le bas si possible.
	 * @return Vrai si il a pu l'être, faux sinon.
	 */
	public boolean moveCursorDown()
	{
		Point tmpCursorPosition = grilleView.getCursorPosition();
		if(tmpCursorPosition.y != 1)
		{
			grilleView.paintCursor(new Point(tmpCursorPosition.x, tmpCursorPosition.y - 1));
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode qui initialise le timer gérant la montée de la grille.
	 */
	private void initClimbTimer()
	{
		climbTimer = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grilleView.moveUpPanelBlocs();
				if(grilleView.getPanelBlocs().getLocation().y == -BLOCHEIGHT)
				{
					climbTimer.stop();
					timersLocked++;
					grille.addNewLineFromBottom();
					moveCursorUp();
					grilleView.resetLocationPanelBlocs();
					jc.checkCombosThread();
					checkHasLost();
					timersLocked--;
					startClimb();
				}
			}
		});
	}
	
	/**
	 * Méthode qui initialise le timer gérant la defaite d'un joueur.
	 */
	private void initLoseTimer()
	{
		loseTimer = new Timer(DELAYCHECKLOOSE, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						grille.switchToFinishState();
						jc.hasLost();
					}
				});
				t.start();
				((Timer)e.getSource()).stop();
			}
		});
	}
	
	/**
	 * Méthode qui initialise le timer gérant la montée du mur lors de la transition.
	 */
	private void initTransitionTimer()
	{
		this.timerTransition = new Timer(TEMPSMONTEETRANSITION, new ActionListener() {
			
			boolean monte = true;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(monte)
				{
					grilleView.monteeMurTransition();
					if(grilleView.getMurTransition().getLocation().y == 0)
					{
						((Timer)e.getSource()).stop();
						monte = false;
						wakeup();
					}
				}
				else
				{
					grilleView.descendreMurTransition();
					if(grilleView.getMurTransition().getLocation().y == GrilleView.BGHEIGHT)
					{
						((Timer)e.getSource()).stop();
						monte = true;
						wakeup();
					}
				}
			}
		});
	}
	
	/**
	 * Méthode qui réveille tous les threads attendant cette objet.
	 */
	public synchronized void wakeup()
	{
		this.notifyAll();
	}
	
	/**
	 * Méthode qui fait attendre le thread courant sur cette objet.
	 */
	public synchronized void justWaitForThis()
	{
		try {
			this.wait();
		} catch (InterruptedException e) {
			return;
		}
	}
	
	/**
	 * Méthode qui permet de lancer la montée d'une ligne. Met à jour le delai de montée pour le timer.
	 */
	public synchronized void startClimb()
	{
		int step = tempsAvantMontee / BLOCHEIGHT;
		climbTimer.setDelay(step);
		climbTimer.setInitialDelay(step);
		if(timersLocked == 0)
			climbTimer.start();
	}
	
	/**
	 * Méthode qui permet d'arreter le timer de montée et le timer de defaite.
	 */
	public synchronized void suspendTimers()
	{
		timersLocked++;
		climbTimer.stop();
		loseTimer.stop();
	}
	
	/**
	 * Méthode qui redémarre le timer de montée et le timer de défaite.
	 */
	public synchronized void resumeTimers()
	{
		timersLocked--;
		if(timersLocked == 0)
		{
			climbTimer.restart();
			checkHasLost();
		}
	}
	
	/**
	 * Méthode qui permet de finir la montée de la ligne au plus vite.
	 */
	public void finishClimb()
	{
		if(climbTimer.isRunning())
		{
			climbTimer.setInitialDelay(FINISHCLIMBDELAY);
			climbTimer.setDelay(FINISHCLIMBDELAY);
			climbTimer.restart();
		}
	}
	
	/**
	 * Méthode qui permet de démarrer ou d'arreter le timer de défaite si le joueur est sur le point de perdre ou non.
	 * Plusieurs appelle à la suite si joueur est en train de perdre ne redémarre pas le timer, il continue simplement.
	 */
	public void checkHasLost()
	{	
		if(grille.hasLosingBlocs())
		{
			if(!loseTimer.isRunning())
			{
				loseTimer.start();
			}
		}
		else
		{
			loseTimer.stop();
		}
	}
	
	/**
	 * Méthode qui permet de vider le modele de la grille. Ainsi que le curseur sur la vue de la grille.
	 */
	public void viderGrille()
	{
		this.grille.emptyGrille();
		this.grilleView.removeCursor();
	}
	
	/**
	 * Méthode qui permet de faire un aller pour le mur de transition. Un aller correspondant à soit le monter tout en haut,
	 * soit le descendre tout en bas.
	 */
	public void faireUnAllerTransition()
	{
		timerTransition.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PartieVersus)
		{
			PartieVersus p = (PartieVersus)o;
			EtatPartie etat = p.getEtatPartie();
			if(etat.equals(EtatPartie.PAUSE) || etat.equals(EtatPartie.FIN) || etat.equals(EtatPartie.TRANSITIONMANCHE))
			{
				this.climbTimer.stop();
				this.loseTimer.stop();
				this.timersLocked++;
			}
			else if(etat.equals(EtatPartie.COMPTEAREBOURS))
			{
				this.timersLocked = 0;
				this.grille.randomStartGrille();
				this.grilleView.resetCursor();
			}
			else if(etat.equals(EtatPartie.ENJEU))
			{
				this.startClimb();
			}
			else if(etat.equals(EtatPartie.INTERRUPTED))
			{
				this.suspendTimers();
				this.timerTransition.stop();
				this.grille.emptyGrille();
			}
		}
		
	}
}
