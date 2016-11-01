package donnees;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Classe représentant les données correspondant aux blocs du jeu TetrisAttack. 
 * Hérite de la classe {@link Observable} pour pouvoir être observer quand celui ci change de couleur.
 * @author Sacha
 *
 */
public class Bloc extends Observable implements IBloc, Comparable<Bloc>{
	/**
	 * Couleur du Bloc.
	 */
	private BlocColor couleur;
	
	/**
	 * Etat du bloc.
	 */
	private EtatBloc etat;
	
	/**
	 * Position en x du {@link Bloc} dans la {@link Grille}.
	 */
	private int x;
	
	/**
	 * Position en y du {@link Bloc} dans la {@link Grille}.
	 */
	private int y;

	/**
	 * Constructeur de la classe {@link Bloc}. Instancie un nouveau {@link Bloc} avec la couleur spécifiée et un état normal.
	 * @param couleur Couleur du bloc.
	 * @param x Position en x du {@link Bloc} dans la {@link Grille}.
	 * @param y Position en y du {@link Bloc} dans la {@link Grille}.
	 */
	public Bloc(BlocColor couleur, int x, int y)
	{
		this.couleur = couleur;
		this.etat = EtatBloc.NORMAL;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Méthode qui retourne la couleur de ce Bloc.
	 * @return la couleur de ce Bloc.
	 */
	public BlocColor getCouleur() {
		return couleur;
	}
	
	/**
	 * Méthode qui permet de retourner le {@link EtatBloc} de ce {@link Bloc}.
	 * @return L'état de ce bloc.
	 */
	public EtatBloc getEtat() {
		return etat;
	}
	
	/**
	 * Méthode qui permet de retourner la position en x de ce bloc dans la {@link Grille}.
	 * @return position en x dans la grille.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Méthode qui permet de retourner la position en y de ce bloc dans la {@link Grille}.
	 * @return position en y de ce bloc dans la grille.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Méthode qui permet de set l'état de ce bloc. Notifie les observateurs avec en paramètre l'ancienne état.
	 * @param etat Nouvelle état du bloc.
	 */
	public void setEtat(EtatBloc etat) {
		EtatBloc prec = this.etat;
		this.etat = etat;
		super.setChanged();
		super.notifyObservers(prec);
	}
	
	/**
	 * Méthode qui permet de reset proprement un bloc en notifiant correctement l'observateur. 
	 * Passe la couleur de ce bloc à vide et son état à normal.
	 */
	public void reset()
	{
		this.etat = EtatBloc.NORMAL;
		this.couleur = BlocColor.Vide;
		super.setChanged();
		super.notifyObservers("reset");
	}
	
	/**
	 * Méthode qui permet d'échanger la couleur de ce bloc avec celle de celui passée en paramètre.
	 * @param bloc Bloc avec lequelle on échange la couleur.
	 */
	public void swap(Bloc bloc)
	{
		BlocColor bc1 = this.couleur;

		this.setCouleur(bloc.couleur);
		
		bloc.setCouleur(bc1);
	}
	
	/**
	 * Méthode qui permet de chaanger la couleur de ce {@link Bloc}. Notifie les observateurs du changement.
	 * @param couleur Nouvelle couleur du {@link Bloc}.
	 */
	public void setCouleur(BlocColor couleur) {
		this.couleur = couleur;
		super.setChanged();
		super.notifyObservers("Color change");
	}
	
	/**
	 * Méthode qui permet de savoir si ce bloc correspont au vide. C'est à dire, qu'il est soit vide ou en train de tomber.
	 * @return Vrai si ce bloc correspond au vide.
	 */
	public boolean isVoid()
	{
		return this.getEtat().equals(EtatBloc.FALLING) || this.getCouleur().equals(BlocColor.Vide);
	}
	
	/**
	 * Méthode qui permet de dire si deux blocs sont égaux. Deux blocs sont égaux si ils ont les mêmes coordonnées x et y.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if(obj instanceof Bloc)
		{
			Bloc o2 = (Bloc)obj;
			return (x == o2.x && y == o2.y);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(y*10 + x);
	}

	/**
	 * Méthode qui permet de comparer deux blocs. Un bloc est inférieur à un autre si il est situé plus en bas a gauche que l'autre dans la grille.
	 */
	@Override
	public int compareTo(Bloc o) {
		if(y > o.y)
		{
			return 1;
		}
		else if(y < o.y)
		{
			return -1;
		}
		else
		{
			if(x > o.x)
				return 1;
			else if(x < o.x)
				return -1;
			else
				return 0;
		}
	}

	@Override
	public IBloc moveDown(Grille grille) {
		ArrayList<ArrayList<Bloc>> lignesHorizontales = grille.getLignesHorizontales();
		Bloc b1 = this, b2 = lignesHorizontales.get(b1.getY() - 1).get(b1.getX()), b3;
		
		if(b2.getCouleur().equals(BlocColor.Vide))
		{
			EtatBloc tmpEtat = b1.getEtat();
			BlocColor tmpColor = b1.getCouleur();
			
			b1.setCouleur(b2.getCouleur());
			b1.setEtat(b2.getEtat());
			
			b2.setCouleur(tmpColor);
			b2.setEtat(tmpEtat);
			
			if(b2.getY() > 1)
			{
				b3 = lignesHorizontales.get(b2.getY() - 1).get(b2.getX());
				if(b3.isVoid())
				{
					return b2;
				}
				else
				{
					b2.setEtat(EtatBloc.NORMAL);
					return null;
				}
			}
			else
			{
				b2.setEtat(EtatBloc.NORMAL);
				return null;
			}
		}
		else if(b2.isVoid())
		{
			return b1;	
		}
		else
		{
			b1.setEtat(EtatBloc.NORMAL);
			return null;
		}
	}

}
