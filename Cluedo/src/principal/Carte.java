package principal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Classe repr�sentant une Carte du jeu cluedo.
 * @author Sacha et Clement
 *
 */
public class Carte {
	
	/**
	 * Image de la carte UNKNOWN
	 */
	public final static Icon IMAGE_UNKNOWN = new ImageIcon("Images/Unknown.jpg");
	
	/**
	 * Image correspondant � la carte.
	 */
	private Icon image;
	
	/**
	 * Nom repr�sentant la carte.
	 */
	private String nom;
	
	/**
	 * Instancie une nouvelle Carte avec le nom et l'image pass�s en param�tres.
	 * @param nom Nom correspondant � la Carte.
	 * @param img Image correspondant � la Carte.
	 */
	public Carte(String nom, Icon img)
	{
		this.nom = nom;
		this.image = img;
	}
	
	/**
	 * M�thode retournant le nom associ� � la carte sous la forme d'une chaine de caract�res.
	 * @return Nom de la carte sous la forme d'un String
	 */
	public String getNom()
	{
		return this.nom;
	}
	
	public Icon getImage()
	{
		return this.image;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		
		if(o instanceof Carte)
		{
			Carte tmp = (Carte)o;
			if(nom.equals(tmp.getNom()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	public static Carte retrouverCarte(String nomCarte)
	{
		Carte c = null;
		for(Armes a : Armes.values())
		{
			if(a.toString().equalsIgnoreCase(nomCarte))
			{
				c = new Arme(a.toString(),a.getImage());
				return c;
			}
		}
		for(Lieux l : Lieux.values())
		{
			if(l.toString().equalsIgnoreCase(nomCarte))
			{
				c = new Lieu(l.toString(),l.getImage());
				return c;
			}
		}
		for(Suspects s : Suspects.values())
		{
			if(s.toString().equalsIgnoreCase(nomCarte))
			{
				c = new Suspect(s.toString(),s.getImage());
				return c;
			}
		}
		return c;
	}
	
	/**
	 * M�thode qui retourne si une carte, repr�sent�e par un String, est pr�sente dans la Collection de cartes pass�e en param�tre.
	 * @param cartes Collection de cartes dans laquelle vous souhaitez chercher.
	 * @param nom Chaine de caract�res repr�sentant la carte recherch�e.
	 * @return true si la carte est pr�sente au moins une fois dans la collection, sinon false.
	 */
	public static boolean contientCarte(List<Carte> cartes, String nom)
	{
		for(Carte c : cartes)
		{
			if(c.getNom().equals(nom))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * M�thode qui retourne les cartes en commun dans la collection d'objet et le tableau de String en comparant les cartes avec leur nom.
	 * @param cartes Collection de cartes dans laquelle vous souhaitez chercher.
	 * @param noms Tableau de noms repr�sentant des cartes.
	 * @return Liste des cartes contenu dans la collection de cartes sous la forme de String, liste vide si aucune carte en commun.
	 */
	public static List<String> cartesContenuDans(List<Carte> cartes, String[] noms)
	{
		List<String> res = new ArrayList<String>();
		for(Carte c : cartes)
		{
			for(String n : noms)
			{
				if(c.getNom().equals(n))
				{
					res.add(n) ;
				}
			}
		}
		return res;
	}
	
	/**
	 * M�thode qui permet de tester les cartes repr�sent�es sous forme d'un tableau de string, si elle sont correctes.
	 * Pour qu'elles soient correctes il faut une carte de chaque type(Lieu,Suspect,Arme).
	 * @param tabCarte Tableau de String repr�sentant les 3 cartes sugg�rer par le joueur.
	 * @return Un tableau de carte correspondant aux cartes sugg�rer par le Joueur dans l'ordre : [0]Arme [1]Lieu [2]Suspect. Null si au moins l'une des chaines ne correspond pas � une carte.
	 */
	public static Carte[] testerCartes(String[] tabCarte)
	{
		//Tester si les cartes pass�es sont correctes
		//si elles sont correctes, renvoie un tableau de carte
		//sinon renvoie null;
		
		Carte[] cartes = null;
		Carte a = null;
		Carte b = null;
		Carte c = null;
		
		//on test la premi�re carte
		for(Armes arme : Armes.values())
		{
			if(arme.toString().equals(tabCarte[0]))
			{
				a = new Arme(arme.toString(), arme.getImage());
			}
		}
		if(a == null)
		{
			for(Lieux lieu : Lieux.values())
			{
				if(lieu.toString().equals(tabCarte[0]))
				{
					a = new Lieu(lieu.toString(), lieu.getImage());
				}
			}
		}
		if(a == null)
		{
			for(Suspects suspect : Suspects.values())
			{
				if(suspect.toString().equals(tabCarte[0]))
				{
					a = new Suspect(suspect.toString(), suspect.getImage());
				}
			}
		}
		
		//on test la 2�me carte (plus de conditions..)
		if(a != null)
		{
			// si la premi�re carte est une arme
			if(a instanceof Arme)
			{
				for(Lieux lieu : Lieux.values())
				{
					if(lieu.toString().equals(tabCarte[1]))
					{
						b = new Lieu(lieu.toString(), lieu.getImage());
					}
				}
				if(b == null)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[1]))
						{
							b = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
			}
			// si la premi�re carte est un lieu
			if(a instanceof Lieu)
			{
				for(Armes arme : Armes.values())
				{
					if(arme.toString().equals(tabCarte[1]))
					{
						b = new Arme(arme.toString(), arme.getImage());
					}
				}
				if(b == null)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[1]))
						{
							b = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
			}
			// si la premi�re carte est un suspect
			if(a instanceof Suspect)
			{
				for(Armes arme : Armes.values())
				{
					if(arme.toString().equals(tabCarte[1]))
					{
						b = new Arme(arme.toString(), arme.getImage());
					}
				}
				if(b == null)
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.toString().equals(tabCarte[1]))
						{
							b = new Lieu(lieu.toString(), lieu.getImage());
						}
					}
				}
			}
		}
		// on test la 3�me carte (encore plus de test...)
		if(b != null)
		{
			//si la premir�e carte est une arme
			if(a instanceof Arme)
			{
				//si la deuxi�me carte est un lieu
				if(b instanceof Lieu)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[2]))
						{
							c = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
				//si la deuxi�me carte est un suspect
				else
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.toString().equals(tabCarte[2]))
						{
							c = new Lieu(lieu.toString(), lieu.getImage());
						}
					}
				}
			}
			//si la premi�re carte est un lieu
			if(a instanceof Lieu)
			{
				//si la deuxi�me carte est une arme
				if(b instanceof Arme)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[2]))
						{
							c = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
				//si la deuxi�me carte est un suspect
				else
				{
					for(Armes arme : Armes.values())
					{
						if(arme.toString().equals(tabCarte[2]))
						{
							c = new Arme(arme.toString(), arme.getImage());
						}
					}
				}
			}
			//si la premi�re carte est un suspect
			if(a instanceof Suspect)
			{
				//si la deuxi�me carte est une arme
				if(b instanceof Arme)
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.toString().equals(tabCarte[2]))
						{
							c = new Lieu(lieu.toString(), lieu.getImage());
						}
					}
				}
				//si la deuxi�me carte est un lieu
				else
				{
					for(Armes arme : Armes.values())
					{
						if(arme.toString().equals(tabCarte[2]))
						{
							c = new Arme(arme.toString(), arme.getImage());
						}
					}
				}
			}
		}
		if(c != null)
		{
			cartes = new Carte[3];
			if(a instanceof Arme)
			{
				cartes[0] = a;
				if(b instanceof Lieu)
				{
					cartes[1] = b;
					cartes[2] = c;
				}
				else if(b instanceof Suspect)
				{
					cartes[2] = b;
					cartes[1] = c;
				}
			}
			else if(a instanceof Lieu)
			{
				cartes[1] = a;
				if(b instanceof Arme)
				{
					cartes[0] = b;
					cartes[2] = c;
				}
				else if(b instanceof Suspect)
				{
					cartes[2] = b;
					cartes[0] = c;
				}
			}
			else if(a instanceof Suspect)
			{
				cartes[2] = a;
				if(b instanceof Arme)
				{
					cartes[0] = b;
					cartes[1] = c;
				}
				else if(b instanceof Lieu)
				{
					cartes[1] = b;
					cartes[0] = c;
				}
			}
			
		}
		return cartes;
	}
	
	/**
	 * M�thode qui cr�e le paquet de cartes du jeu Cluedo.
	 * @return Collection des cartes du jeu Cluedo.
	 */
	public static List<Carte> creerPaquetDeCartes()
	{
		List<Carte> paquet = new ArrayList<Carte>();
		for(Armes a : Armes.values())
		{
			paquet.add(new Arme(a.toString(),a.getImage()));
		}
		for(Lieux l : Lieux.values())
		{
			paquet.add(new Lieu(l.toString(),l.getImage()));
		}
		for(Suspects s : Suspects.values())
		{
			paquet.add(new Suspect(s.toString(),s.getImage()));
		}
		return paquet;
	}

}
