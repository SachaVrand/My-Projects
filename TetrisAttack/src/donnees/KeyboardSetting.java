package donnees;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Classe représentant une configuration de touches.
 * @author Sacha
 *
 */
public class KeyboardSetting {

	/**
	 * Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers le haut.
	 */
	private int keyUp;
	
	/**
	 * Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers le bas.
	 */
	private int keyDown;
	
	/**
	 * Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers la droite.
	 */
	private int keyRight;
	
	/**
	 * Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers la gauche.
	 */
	private int keyLeft;
	
	/**
	 * Constante de {@link KeyEvent} représentant la touche pour échanger des blocs.
	 */
	private int keyAction;
	
	/**
	 * Constante de {@link KeyEvent} représentant la touche pour demandée une montée de blocs.
	 */
	private int keySecondaryAction;
	
	/**
	 * Constructeur de {@link KeyboardSetting}
	 * @param keyUp Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers le haut.
	 * @param keyDown Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers le bas.
	 * @param keyRight Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers la droite.
	 * @param keyLeft Constante de {@link KeyEvent} représentant la touche pour déplacer le curseur vers la gauche.
	 * @param keyAction Constante de {@link KeyEvent} représentant la touche pour échanger des blocs.
	 * @param keySecondaryAction Constante de {@link KeyEvent} représentant la touche pour demandée une montée de blocs.
	 */
	public KeyboardSetting(int keyUp, int keyDown, int keyRight, int keyLeft, int keyAction, int keySecondaryAction) {
		this.keyAction = keyAction;
		this.keyUp = keyUp;
		this.keyDown = keyDown;
		this.keyLeft = keyLeft;
		this.keyRight = keyRight;
		this.keySecondaryAction = keySecondaryAction;
	}
	
	/**
	 * Méthode qui retourne la touche associé à l'echange de blocs. Entier correspondant à une constante de KeyEvent.
	 * @return la touche associé à l'echange de blocs. Entier correspondant à une constante de KeyEvent.
	 */
	public int getKeyAction() {
		return keyAction;
	}
	
	/**
	 * Méthode qui retourne la touche associé au déplacement du curseur vers le bas. Entier correspondant à une constante de KeyEvent.
	 * @return la touche associé au déplacement du curseur vers le bas. Entier correspondant à une constante de KeyEvent.
	 */
	public int getKeyDown() {
		return keyDown;
	}
	
	/**
	 * Méthode qui retourne la touche associé au déplacement du curseur vers la gauche. Entier correspondant à une constante de KeyEvent.
	 * @return la touche associé au déplacement du curseur vers la gauche. Entier correspondant à une constante de KeyEvent.
	 */
	public int getKeyLeft() {
		return keyLeft;
	}
	
	/**
	 * Méthode qui retourne la touche associé au déplacement du curseur vers la droite. Entier correspondant à une constante de KeyEvent.
	 * @return la touche associé au déplacement du curseur vers la droite. Entier correspondant à une constante de KeyEvent.
	 */
	public int getKeyRight() {
		return keyRight;
	}
	
	/**
	 * Méthode qui retourne la touche associé au déplacement du curseur vers le haut. Entier correspondant à une constante de KeyEvent.
	 * @return la touche associé au déplacement du curseur vers le haut. Entier correspondant à une constante de KeyEvent.
	 */
	public int getKeyUp() {
		return keyUp;
	}
	
	/**
	 * Méthode qui retourne la touche associé à la demande d'une montée. Entier correspondant à une constante de KeyEvent.
	 * @return la touche associé à la demande d'une montée. Entier correspondant à une constante de KeyEvent.
	 */
	public int getKeySecondaryAction() {
		return keySecondaryAction;
	}
	
	/**
	 * Méthode qui set la touche associé à l'echange de blocs. Entier correspondant à une constante de KeyEvent.
	 * @param keyAction la touche associé à l'echange de blocs. Entier correspondant à une constante de KeyEvent.
	 */
	public void setKeyAction(int keyAction) {
		this.keyAction = keyAction;
	}
	
	/**
	 * Méthode qui set la touche associé au déplacement du cuseur vers le bas. Entier correspondant à une constante de KeyEvent.
	 * @param keyDown la touche associé au déplacement du cuseur vers le bas. Entier correspondant à une constante de KeyEvent.
	 */
	public void setKeyDown(int keyDown) {
		this.keyDown = keyDown;
	}
	
	/**
	 * Méthode qui set la touche associé au déplacement du cuseur vers la gauche. Entier correspondant à une constante de KeyEvent.
	 * @param keyLeft la touche associé au déplacement du cuseur vers la gauche. Entier correspondant à une constante de KeyEvent.
	 */
	public void setKeyLeft(int keyLeft) {
		this.keyLeft = keyLeft;
	}
	
	/**
	 * Méthode qui set la touche associé au déplacement du cuseur vers la droite. Entier correspondant à une constante de KeyEvent.
	 * @param keyRight la touche associé au déplacement du cuseur vers la droite. Entier correspondant à une constante de KeyEvent.
	 */
	public void setKeyRight(int keyRight) {
		this.keyRight = keyRight;
	}
	
	/**
	 * Méthode qui set la touche associé à la demande d'une montée. Entier correspondant à une constante de KeyEvent.
	 * @param keySecondaryAction la touche associé à la demande d'une montée. Entier correspondant à une constante de KeyEvent.
	 */
	public void setKeySecondaryAction(int keySecondaryAction) {
		this.keySecondaryAction = keySecondaryAction;
	}
	
	/**
	 * Méthode qui set la touche associé au déplacement du cuseur vers le haut. Entier correspondant à une constante de KeyEvent.
	 * @param keyUp la touche associé au déplacement du cuseur vers le haut. Entier correspondant à une constante de KeyEvent.
	 */
	public void setKeyUp(int keyUp) {
		this.keyUp = keyUp;
	}
	
	/**
	 * Méthode qui retourne toutes les touches de cette configuration sous forme d'une liste d'entier de constantes de {@link KeyEvent}.
	 * @return Toutes les touches de cette configuration sous forme d'une liste d'entier de constantes de {@link KeyEvent}.
	 */
	public ArrayList<Integer> allKeyValues()
	{
		ArrayList<Integer> res = new ArrayList<>();
		res.add(keyUp);
		res.add(keyDown);
		res.add(keyRight);
		res.add(keyLeft);
		res.add(keyAction);
		res.add(keySecondaryAction);
		return res;
	}

}
