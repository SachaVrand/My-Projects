package controleurs;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.Timer;

import donnees.EtatPartie;
import donnees.Grille;
import donnees.Humain;
import donnees.KeyboardSetting;
import donnees.PartieVersus;
import principal.RessourcesAudio;
import vues.GrilleView;
import vues.JoueurView;

/**
 * Controleur d'un joueur humain.
 * @author Sacha
 *
 */
public class HumainControlleur extends JoueurController implements Runnable,KeyListener,ActionListener{
	
	/**
	 * Constante de {@link KeyEvent} de la dernière touche appuyé.
	 */
	private int lastKeyPressed;
	
	/**
	 * timer permettant de savoir si le joueur maintient toujours une touche ou pas.
	 */
	private Timer timer;
	
	/**
	 * Booléen permettant de savoir si le deplacement du curseur est au pas a pas ou non.
	 */
	private volatile boolean stepByStep;
	
	/**
	 * Dictionnaire avec comme clés les touches du joueurs et comme valeur si la touche est appuyé ou non.
	 */
	private volatile HashMap<Integer, Boolean> keysPressed;
	
	/**
	 * Thread permettant de gérer les actions du joueur.
	 * Utile pour que le joueur puissent maintenir une touche que le curseur continue de bouger 
	 * sans que l'autre joueur l'arrete en appuyant sur une touche.
	 * Résolution du problème où quand l'on maintient une touche, l'EDT nous envoie en boucle des evenements avec la touche appuyée.
	 * Mais dès que l'on appuie sur une autre touche en restant appuyé sur l'ancienne, l'EDT nous envoie la nouvelle touche en event
	 * mais arrete d'envoyer pour celle qui est restée appuyée(l'ancienne).
	 * Ce qui en temps normal empèche de maintenir par exemple la touche de montée du curseur pour les deux joueurs en même temps,
	 * et que les deux curseurs montent.
	 */
	private Thread threadControles;
	
	/**
	 * Booléén qui permet d'arreter le thread de controles.
	 */
	private boolean stopThread;
	
	/**
	 * Configuration de touches qu'utilise le joueur.
	 */
	private KeyboardSetting keyboardSetting;
	
	/**
	 * Constructeur de {@link HumainControlleur}. Initialise les threads, timers et autres.
	 * @param joueur modele du joueur que l'on controle.
	 * @param joueurView vue du joueur que l'on controle.
	 */
	public HumainControlleur(Humain joueur, JoueurView joueurView) {
		super(joueur,joueurView);
		super.keyListener = this;
		this.keyboardSetting = joueur.getKeyboardSetting();
		stepByStep = true;
		initKeysPressed();
		timer = new Timer(40000, this);
		timer.setInitialDelay(500);
		stopThread = false;
	}
	
	/**
	 * Méthode qui permet d'initialiser le dictionnaire des touches appuyées ou non du joueur.
	 */
	private void initKeysPressed()
	{
		keysPressed = new HashMap<>();
		for(Integer keyId : keyboardSetting.allKeyValues())
		{
			keysPressed.put(keyId, false);
		}
	}

	@Override
	public void run() {
		GrilleView grilleView = super.grilleController.getGrilleView();
		Grille grille = super.grilleController.getGrille();
		
		while(!stopThread)
		{
			Point tmpCursorPosition = grilleView.getCursorPosition();
			if(keysPressed.get(keyboardSetting.getKeyUp()))
			{	
				if(grilleController.moveCursorUp())
					RessourcesAudio.lancerSonDeplacement();
				if(stepByStep)
				{
					keysPressed.put(keyboardSetting.getKeyUp(), false);
				}
				lastKeyPressed = keyboardSetting.getKeyUp();
			}
			else if(keysPressed.get(keyboardSetting.getKeyDown()))
			{
				if(grilleController.moveCursorDown())
					RessourcesAudio.lancerSonDeplacement();
				if(stepByStep)
				{
					keysPressed.put(keyboardSetting.getKeyDown(), false);
				}
				lastKeyPressed = keyboardSetting.getKeyDown();	
			}
			else if(keysPressed.get(keyboardSetting.getKeyLeft()))
			{
				if(grilleController.moveCursorLeft())
					RessourcesAudio.lancerSonDeplacement();
				if(stepByStep)
				{
					keysPressed.put(keyboardSetting.getKeyLeft(), false);
				}
				lastKeyPressed = keyboardSetting.getKeyLeft();	
			}
			else if(keysPressed.get(keyboardSetting.getKeyRight()))
			{
				if(grilleController.moveCursorRight())
					RessourcesAudio.lancerSonDeplacement();
				if(stepByStep)
				{
					keysPressed.put(keyboardSetting.getKeyRight(), false);
				}
				lastKeyPressed = keyboardSetting.getKeyRight();
			}
			else if(keysPressed.get(keyboardSetting.getKeyAction()))
			{
				if(echangeAutorise)
				{
					if(grille.exchange(tmpCursorPosition.x, tmpCursorPosition.y, tmpCursorPosition.x + 1, tmpCursorPosition.y))
					{
						RessourcesAudio.lancerSonSwitch();
						super.checkCombosThread();
					}
				}
				keysPressed.put(keyboardSetting.getKeyAction(), false);
			}
			else if(keysPressed.get(keyboardSetting.getKeySecondaryAction()))
			{
				if(echangeAutorise)
				{
					grilleController.finishClimb();
				}
				keysPressed.put(keyboardSetting.getKeySecondaryAction(), false);
			}
			
			try { 
				Thread.sleep(1000/60);
			}
	        catch (InterruptedException ie) {
	        	break;
	        }
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(keysPressed.containsKey(keyCode))
		{
			keysPressed.put(keyCode, true);
			timer.restart();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(keysPressed.containsKey(keyCode))
		{
			keysPressed.put(keyCode, false);
			timer.stop();
			stepByStep = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stepByStep = false;
		keysPressed.put(lastKeyPressed, true);
	}

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		if(o instanceof PartieVersus)
		{
			EtatPartie etat = ((PartieVersus)o).getEtatPartie();
			if(etat.equals(EtatPartie.TRANSITIONMANCHE))
			{
				stopThread = true;
				timer.stop();
			}
			else if(etat.equals(EtatPartie.COMPTEAREBOURS))
			{
				stopThread = false;
				initKeysPressed();
				stepByStep = true;
				threadControles = new Thread(this, "Thread Controles " + joueur.getIdJoueur());
				threadControles.start();
			}
			else if(etat.equals(EtatPartie.INTERRUPTED))
			{
				stopThread = true;
				timer.stop();
			}
		}
	}

}
