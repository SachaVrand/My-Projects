import java.util.ArrayList;
import java.util.Random;

/**
 * Classe representant le jeu contre l'IA en difficulté difficile.
 * @author Vrand
 *
 */
public class JeuJcIADifficile extends JeuJcIA{

	private static final long serialVersionUID = -1395807313720712009L;

	/**
	 * Constructeur de la classe JeuJcIADifficile
	 * @param couleurIA couleur joué par l'IA.
	 */
	public JeuJcIADifficile(Couleur couleurIA)
	{
		super(couleurIA);
	}

	/**
	 * Méthode de mise à jour du jeu contre l'IA. Elle fait joué l'IA si c'est son tour.
	 */
	public void update()
	{
		super.update();
	}
	
	/**
	 * Méthode gérant le pion joué par l'ordinateur. 
	 * La stratégie de l'IA en difficile est que si un coin est disponible on le joue direct,
	 * si c'est une case près du coin on ne la joue qu'en dernier recours.
	 * Sinon on joue la case qui rapporte le plus de retournements.
	 */
	@Override
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
		
		ArrayList<int[]> presDuCoinJouables = new ArrayList<int[]>();
		int[] max = null;
		int tmp;
		int nbMaxRet = 0;
		Random rand = new Random();
		for(int[] couple : couplesJouable)
		{
			//si c'est un coin on le joue direct.
			if((couple[0] == 0 && couple[1] == 0) || (couple[0] == 0 && couple[1] == 7) || (couple[0] == 7 && couple[1] == 0) || (couple[0] == 7 && couple[1] == 7))
			{
				max = couple;
				break;
			}
			
			//s'il est près du coin on le stock et on continue la boucle for.
			if(couple[0] == 0)
			{
				if(couple[1] == 1 || couple[1] == 6)
				{
					presDuCoinJouables.add(couple);
					continue;
				}
			}
			else if(couple[0] == 1)
			{
				if(couple[1] == 0 || couple[1] == 1 || couple[1] == 6 || couple[1] == 7)
				{
					presDuCoinJouables.add(couple);
					continue;
				}
			}
			else if(couple[0] == 6)
			{
				if(couple[1] == 0 || couple[1] == 1 || couple[1] == 6 || couple[1] == 7)
				{
					presDuCoinJouables.add(couple);
					continue;
				}
			}
			else if(couple[0] == 7)
			{
				if(couple[1] == 1 || couple[1] == 6)
				{
					presDuCoinJouables.add(couple);
					continue;
				}
			}
			
			
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
		else
		{
			if(!presDuCoinJouables.isEmpty())
			{
				int r = rand.nextInt(presDuCoinJouables.size());
				tableauPion[presDuCoinJouables.get(r)[0]][presDuCoinJouables.get(r)[1]].doClick();
			}
		}
	}
	
	public String toString()
	{
		return super.toString() + " : Difficile";
	}
}
