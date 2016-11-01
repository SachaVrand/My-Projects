import java.util.ArrayList;
import java.util.Random;

/**
 * Classe representant le jeu contre l'IA en difficult� moyenne.
 * @author Vrand
 *
 */
public class JeuJcIAMoyen extends JeuJcIA{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur de la classe JeuJcIAMoyen
	 * @param couleurIA couleur jou� par l'IA.
	 */
	public JeuJcIAMoyen(Couleur couleurIA)
	{
		super(couleurIA);
	}

	/**
	 * M�thode g�rant le pion jou� par l'ordinateur. 
	 * La strat�gie de l'IA en moyen est de maximiser le nombre de pions retourn�s apr�s chaque coup.
	 */
	public void jouerCoupIA()
	{
		ArrayList<int[]> couplesJouable = new ArrayList<int[]>();
		Pion[][] tableauPion = plateau.getTableauPion();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tableauPion[i][j].getEstJouable())
				{
					int[] tmp = {i,j};
					couplesJouable.add(tmp);
				}
			}
		}
		
		int[] max = null;
		int tmp;
		int nbMaxRet = 0;
		Random rand = new Random();
		for(int[] couple : couplesJouable)
		{
			tmp = plateau.nombreDeRetournements(couleurIA, tableauPion[couple[0]][couple[1]]);
			if(tmp > nbMaxRet)
			{
				if(tmp == nbMaxRet)
				{
					if(rand.nextBoolean())
					{
						max = couple;
						nbMaxRet = tmp;
					}
				}
				else
				{
					max = couple;
					nbMaxRet = tmp;
				}
			}
		}
		if(max != null)
		{
			tableauPion[max[0]][max[1]].doClick();
		}
	}
	
	/**
	 * M�thode de mise � jour du jeu contre l'IA. Elle fait jou� l'IA si c'est son tour.
	 */
	public void update()
	{
		super.update();
	}
	
	public String toString()
	{
		return super.toString() + " : Moyen";
	}

}
