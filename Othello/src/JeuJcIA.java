import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Classe abstraite représentant une partie face à l'ordinateur
 * @author Vrand
 *
 */
public abstract class JeuJcIA extends Jeu{
	
	private static final long serialVersionUID = -3469734302067676768L;
	
	/**
	 * Couleur jouée par l'IA
	 */
	protected Couleur couleurIA = null;
	
	/**
	 * Timer permettant de faire jouer l'IA dans un temps humainement possible.
	 */
	protected Timer t = null;
	
	/**
	 * Constructeur de la classe JeuJcIA
	 * @param couleurIA couleur jouée par l'ordinateur.
	 */
	public JeuJcIA(Couleur couleurIA)
	{
		super();
		this.couleurIA = couleurIA;
		this.loadTimer();
	}
	
	/**
	 * Méthode qui gère l'instanciation du timer de jeu de l'IA.
	 */
	private void loadTimer()
	{
			this.t = new Timer(600,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				t.stop();
				glass.setEnabled(false);
				glass.setVisible(false);
				timerJeuIAisRunning = false;
				jouerCoupIA();
			}
		});
	}

	/**
	 * Méthode gérant le pion joué par l'ordinateur.
	 */
	public abstract void jouerCoupIA();
	
	/**
	 * Méthode de mise à jour du jeu contre l'IA. Elle fait joué l'IA si c'est son tour.
	 */
	public void update()
	{
		super.update();
		if(!getPlateauRempli())
		{
			if(plateau.getCouleurJouant().equals(couleurIA))
			{
				if(getTourJouable())
				{
					if(super.t.isRunning())
					{
						this.t.setInitialDelay(1600);
					}
					else
					{
						this.t.setInitialDelay(600);
					}
					t.start();
					timerJeuIAisRunning = true;
					glass.setEnabled(true);
					glass.setVisible(true);
				}
			}
		}
	}
	
	public String toString()
	{
		return "Joueur Contre IA";
	}
}
