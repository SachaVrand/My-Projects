import javax.swing.JButton;

/**
 * Classe repr�sentant les boutons "Regarder" dans le menu du choix des sauvegardes.
 * @author Vrand
 *
 */
public class BoutonRegarder extends JButton{
	
	private static final long serialVersionUID = 4630151939390195598L;
	
	/**
	 * Sauvegarde qui sera lanc�e apr�s avoir cliquer sur ce bouton.
	 */
	private Sauvegarde save = null;
	
	/**
	 * Constructeur de la classe BoutonRegarder
	 * Instancie un nouveau JButton avec le texte sp�cifier en param�tre.
	 * @param text -> Texte � afficher sur le bouton
	 * @param save -> Sauvegarde associ�e au bouton
	 */
	public BoutonRegarder(String text,Sauvegarde save)
	{
		super(text);
		this.save = save;
	}
	
	/**
	 * M�thode qui retourne la sauvegarde associ�e au bouton
	 * @return sauvegarde associ�e au bouton
	 */
	public Sauvegarde getSauvegarde()
	{
		return this.save;
	}

}
