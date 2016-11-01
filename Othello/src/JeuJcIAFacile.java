import java.util.ArrayList;
import java.util.Random;

/**
 * Classe représentant le jeu contre L'IA en difficulté facile.
 * @author Vrand
 *
 */
public class JeuJcIAFacile extends JeuJcIA{

	private static final long serialVersionUID = -5406629280984027714L;

	/**
	 * Constructeur de la classe JeuJcIAFacile
	 * @param couleurIA couleur jouée par l'IA.
	 */
	public JeuJcIAFacile(Couleur couleurIA)
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
	 * La stratégie de l'IA en facile est de jouer un pion totalement au hasard parmis ceux disponibles.
	 */
	@Override
	public void jouerCoupIA() {
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
		Random rand = new Random();
		int[] res = couplesJouable.get(rand.nextInt(couplesJouable.size()));
		tableauPion[res[0]][res[1]].doClick();
	}

	public String toString()
	{
		return super.toString() + " : Facile";
	}
}
