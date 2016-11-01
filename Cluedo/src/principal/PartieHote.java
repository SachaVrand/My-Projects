package principal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Classe r�pr�sentant les parties jou�es en tant qu'h�te, c'est � dire les parties en tant que serveur arbitre ou en solo sur la machine.
 * @author Sacha
 *
 */
public abstract class PartieHote implements IPartie{

	/**
	 * Repr�sente la liste des joueurs dans la partie.
	 */
	protected List<Joueur> joueursPartie;
	
	/**
	 * Repr�sente les cartes � d�couvrir pour r�soudre le crime.
	 */
	protected Carte[] cartesADecouvrir;
	
	/**
	 * Repr�sente l'indice, dans la liste des joueurs, du joueur actuel.
	 */
	protected int joueurActuel;
	
	/**
	 * Represente si la partie est finie ou non.
	 */
	protected boolean partieFinie;
	
	/**
	 * Nom du joueur ayant gagn�.
	 */
	protected String nomGagnant;
	
	/**
	 * Instancie une nouvelle partie avec la liste des joueurs pass�e en param�tre, l'indice du joueur actuel � 0, un nouveau tableau de 3 cartes, partieFinie � false 
	 * et distribue les cartes � chaque joueur.
	 * @param joueursPartie Collection de Joueur jouant la partie.
	 */
	public PartieHote(List<Joueur> joueursPartie)
	{
		this.joueursPartie = joueursPartie;
		this.joueurActuel = 0;
		this.cartesADecouvrir = new Carte[3];
		this.partieFinie = false;
		this.nomGagnant = "unknown";
		distribuerPaquet(Carte.creerPaquetDeCartes());
	}
	
	/**
	 * Retourne le nom du joeuur ayant gagn�
	 * @return le nom du joeuur ayant gagn�
	 */
	public String getNomGagnant() {
		return nomGagnant;
	}

	/**
	 * M�thode qui retourne si la partie est finie.
	 * @return retourne vrai si la partie est finie sinon false.
	 */
	public boolean getPartieFinie()
	{
		return partieFinie;
	}
	
	/**
	 * M�thode qui permet de distribuer les cartes pass�es en param�tre � chaque joueur pr�sent dans la partie 
	 * et de mettre les 3 carte du crime dans cartesADecouvrir.
	 * @param paquet Paquet de cartes complet du jeu Cluedo. 
	 */
	private void distribuerPaquet(List<Carte> paquet)
	{
		Collections.shuffle(paquet);
		Iterator<Carte> it = paquet.iterator();
		while(it.hasNext())
		{
			Carte c = it.next();
			if(c instanceof Arme)
			{
				if(!(cartesADecouvrir[0] instanceof Arme))
				{
					cartesADecouvrir[0] = c;
					it.remove();
				}
			}
			else if(c instanceof Lieu)
			{
				if(!(cartesADecouvrir[1] instanceof Lieu))
				{
					cartesADecouvrir[1] = c;
					it.remove();
				}		
			}
			else if(c instanceof Suspect)
			{
				if(!(cartesADecouvrir[2] instanceof Suspect))
				{
					cartesADecouvrir[2] = c;
					it.remove();
				}
			}
			
			if(cartesADecouvrir[0] instanceof Arme && cartesADecouvrir[1] instanceof Lieu && cartesADecouvrir[2] instanceof Suspect)
			{
				break;
			}
		}
		
		for(int i = 0, j = joueursPartie.size() - 1; i < paquet.size(); i++)
		{
			joueursPartie.get(j).ajouterCarte(paquet.get(i));
			j--;
			if(j == -1)
				j = joueursPartie.size() - 1;
		}
		
	}
	
	public Carte[] getCartesADecouvrir() {
		return cartesADecouvrir;
	}
}
