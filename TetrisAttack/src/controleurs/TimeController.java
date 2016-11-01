package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import donnees.EtatPartie;
import donnees.PartieVersus;
import donnees.TimeModel;
import vues.TimeView;

/**
 * Controleur entre les classe {@link TimeModel} et {@link TimeView}
 * @author Sacha
 *
 */
public class TimeController implements ActionListener, Observer{
	
	/**
	 * Instance de {@link TimeModel} dont on fait le controle.(modele)
	 */
	private TimeModel timeModel;
	
	/**
	 * Timer controlant le raffraichissement de l'horloge en jeu.(timer)
	 */
	private Timer timer;
	
	/**
	 * Constructeur de {@link TimeController}. Instancie un nouvelle instance avec comme observateur, pour le timeModel, le timeView en paramètre.
	 * Instancie le {@link Timer} à 1 seconde de delai avec comme {@link ActionListener} cette instance.
	 * @param timeView Vue du timer dont on fait le controle.
	 * @param timeModel Modele tu timer dont on fait le controle.
	 */
	public TimeController(TimeView timeView, TimeModel timeModel) {
		this.timeModel = timeModel;
		timeModel.addObserver(timeView);
		timer = new Timer(1000,this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.timeModel.update(1);
	}
	
	/**
	 * Méthode qui permet de lancer le {@link Timer}.
	 */
	public void startTimer()
	{
		this.timer.start();
	}
	
	/**
	 * Méthode qui permet de stopper le {@link Timer}.
	 */
	public void stopTimer()
	{
		this.timer.stop();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PartieVersus)
		{
			PartieVersus p = (PartieVersus)o;
			EtatPartie etat = p.getEtatPartie();
			if(etat.equals(EtatPartie.COMPTEAREBOURS) || etat.equals(EtatPartie.PAUSE) || etat.equals(EtatPartie.FIN))
			{
				this.stopTimer();
				if(etat.equals(EtatPartie.COMPTEAREBOURS))
				{
					this.timeModel.resetTime();
				}
			}
			else if(etat.equals(EtatPartie.TRANSITIONMANCHE))
			{
				this.stopTimer();
			}
			else if(etat.equals(EtatPartie.ENJEU))
			{
				this.startTimer();
			}
			else if(etat.equals(EtatPartie.INTERRUPTED))
			{
				this.timer.stop();
			}
		}
	}

}
