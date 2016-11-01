package vues;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import donnees.Changement;

/**
 * Classe représentant une sous option pour un raccourci.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class SORaccourcis extends PanelSousOption{

	/**
	 * Constante de {@link KeyEvent} représentant la touche associé au raccourci.
	 */
	private int keyCode;
	
	/**
	 * Constructeur de {@link SORaccourcis}.
	 * @param label Titre de la sous option.
	 * @param c Fonction de changement changeant la raccouci lié à cette sous option.
	 * @param keyCode Constante de {@link KeyEvent} représentant la touche associé au raccourci.
	 */
	public SORaccourcis(String label, Changement c, int keyCode) {
		super(label, c);
		this.keyCode = keyCode;
		initComposants(label);
	}
	
	/**
	 * Méthode qui retourne la constante de {@link KeyEvent} représentant la touche associé au raccourci.
	 * @return Constante de {@link KeyEvent} représentant la touche associé au raccourci.
	 */
	public int getKeyCode() {
		return keyCode;
	}

	@Override
	public String getTextValue() {
		return KeyEvent.getKeyText(keyCode);
	}
	
	/**
	 * Méthode qui permet set la couleur de lblValue à la couleur d'érreur. Pour indiquer que le raccourci est déjà pris.
	 */
	public void setErrorColor()
	{
		this.lblTxtValue.setForeground(Color.red);
	}
	
	/**
	 * Méthode qui permet de remettre à la couleur normale la couleur de lblValue.
	 */
	public void setNormalColor()
	{
		this.lblTxtValue.setForeground(null);
	}
	
	/**
	 * Méthode qui permet d'ajouter un listener à ce panel.
	 * @param MAPanel un listener à ce panel.
	 */
	public void ajouterListeners(MouseAdapter MAPanel)
	{
		this.addMouseListener(MAPanel);
	}

	@Override
	public void setValue(Object o) {
		this.keyCode = (int)o;
		this.lblTxtValue.setText(getTextValue());
	}
}
