package principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import gui.GraphicalUserInterface;

/**
 * Classe repr�sentant un joueur de type Humain.
 * @author Sacha et Clement
 *
 */
public class Humain extends Joueur
{
	/**
	 * Instancie un nouveau Joueur. cf Constructeur Joueur.
	 * @see Joueur#Joueur(String)
	 * @param nom Nom du joueur sous la forme de chaine de caract�res.
	 */
	public Humain(String nom)
	{
		super(nom);
	}

	/**
	 * M�thode qui permet de faire une suggestion ou une accusation � un joueur sur la console..
	 * @return Tableau de String repr�sentant les commandes taper par l'utilisateur. Null si le joueur a d�cider de quitter. String[4]. String[0] option. String[1-3] carte. [1]arme [2]lieu [3]suspect
	 */
	@Override
	public String[] jouerCoup()
	{
		if(Cluedo.mode == Cluedo.GUI_MODE)
		{
			GraphicalUserInterface.afficherFenJouer();
		}
		String[] cmdComplete = null;
		String cmd = "";
		System.out.println("\n'help' pour plus d'informations sur les commandes disponibles");
		
		do
		{
			System.out.print("[JOUER]" + nom + " > ");
			
			try
			{
				cmd = Cluedo.sc.nextLine();
			}
			catch(NoSuchElementException e)
			{
				System.err.println("Impossible de r�cup�rer le flux");
				System.exit(1);
				e.printStackTrace();
			}
			catch(IllegalStateException e)
			{
				System.err.println("Impossible de r�cup�rer le flux");
				System.exit(1);
				e.printStackTrace();
			}
			
			cmdComplete = cmd.split(" ");
			cmd = cmdComplete[0];
			
			if(cmd.equals("show") && cmdComplete.length == 1)
			{
				afficherIndices();
			}
			else if(cmd.equals("help") && cmdComplete.length == 1)
			{
				afficherAide();
			}
			else if(cmd.equals("exit") && cmdComplete.length == 1)
			{
				System.out.println(nom + " quitte la partie.");
			}
			else if(cmd.equals("move") && cmdComplete.length == 5)
			{
				
				if(cmdComplete[1].equals("suggest") || cmdComplete[1].equals("accuse"))
				{
					//Tester si les cartes pass�es sont correctes
					Carte[] cartes = Carte.testerCartes(new String[]{cmdComplete[2],cmdComplete[3],cmdComplete[4]});
					if(cartes == null)
					{
						System.out.println("Mauvaises cartes. Une carte lieu, une carte arme et une carte suspect sont requises en parametres.");
					}
					else
					{
						String[] res = new String[]{cmdComplete[1],cartes[0].getNom(),cartes[1].getNom(),cartes[2].getNom()};
						// return [0]option [1]arme [2]lieu [3]suspect
						if(Cluedo.mode == Cluedo.GUI_MODE)
						{
							GraphicalUserInterface.desafficherFenJouer();
						}
						return res;
					}
				}
				else
				{
					System.out.println("Mauvaise option, 'suggest' ou 'accuse'");
				}	
			}
			else
			{
				System.out.println("Mauvaise commande");
			}
		}while(!cmd.equals("exit") || cmdComplete.length != 1);
		if(Cluedo.mode == Cluedo.GUI_MODE)
		{
			GraphicalUserInterface.desafficherFenJouer();
		}
		return null;
	}

	/**
	 * M�thode qui permet � un joueur de r�futer.
	 * @param carteCommun Cartes en commun dans le paquet du joueur avec les cartes suggerer sous la forme de String
	 * @return chaine correspondant � la carte montr�e ou exit si le joueur souhaite quitter.
	 */
	@Override
	public String refuter(List<String> carteCommun)
	{
		if(Cluedo.mode == Cluedo.GUI_MODE)
		{
			ArrayList<Carte> listeCartesCommun = new ArrayList<Carte>();
			for(String c : carteCommun)
			{
				listeCartesCommun.add(Carte.retrouverCarte(c));
			}
			GraphicalUserInterface.afficherFenRefuter(listeCartesCommun);
		}
		String cmd = "";
		String[] cmdComplete;
		
		System.out.println();
		for(String c : carteCommun)
		{
			System.out.println("\t" + c);
		}
		System.out.println();
		
		System.out.println("'help' pour plus d'informations sur les commandes disponibles");
		System.out.println("Vous pouvez refuter la proposition. Quelle carte choisissez vous de montrer ? (show <card>)");
		do
		{
			System.out.print("[REFUTER] "+nom+" > ");
			cmd = Cluedo.sc.nextLine();
			cmdComplete = cmd.split(" ");
			cmd = cmdComplete[0];
			if(cmd.equals("help") && cmdComplete.length == 1)
			{
				afficherAideRefuter();
			}
			else if(cmd.equals("exit") && cmdComplete.length == 1)
			{
				return "exit";
			}
			else if(cmd.equals("show") && cmdComplete.length == 2)
			{
				if((Carte.contientCarte(cartesJoueur, cmdComplete[1])) && (carteCommun.contains(cmdComplete[1])))
				{
					break;
				}
				else
				{
					System.out.println("Coup invalide");
				}
			}
			else
			{
				System.out.println("Mauvaise commande !");
			}
		}while(true);
		if(Cluedo.mode == Cluedo.GUI_MODE)
		{
			GraphicalUserInterface.desafficherFenRefuter();
		}
		return cmdComplete[1];
	}
	
	/**
	 * M�thode qui permet d'afficher l'aide sur les commandes quand un joueur sugg�re ou accuse.
	 */
	private void afficherAide()
	{
		System.out.println("\nLes commandes disponibles durant la partie :\n");
		System.out.println("show");
		System.out.println("\t Voir vos indices.\n");
		System.out.println("move <type> <card1> <card2> <card3>");
		System.out.println("\t <type> : Sois 'suggest' ou 'accuse'.");
		System.out.println("\t <cardN> : une carte.\n");
		System.out.println("exit");
		System.out.println("\t Quitter la partie.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
		String c = "Cartes du jeu : Armes ->";
		for(Armes a : Armes.values())
		{
			c += " " + a.toString() + ",";
		}
		c += "\n                Lieux ->";
		for(Lieux l : Lieux.values())
		{
			c += " " + l.toString() +",";
		}
		c += "\n                Suspects ->";
		for(Suspects s : Suspects.values())
		{
			c += " " + s.toString() + ",";
		}
		System.out.println(c +"\n");
	}
	
	/**
	 * M�thode qui permet d'afficher l'aide sur les commandes quand un joueur r�fute.
	 */
	private void afficherAideRefuter()
	{
		System.out.println("Les commandes disponibles :\n");
		System.out.println("show <card>");
		System.out.println("\t la carte que vous souhaiter montrer si vous les pouvez \n");
		System.out.println("help");
		System.out.println("\t affiche ce message \n");
	}
	
	/**
	 * M�thode qui permet d'afficher les indices que le joueur poss�de.
	 */
	private void afficherIndices()
	{
		System.out.println("\n\t Indices : ");
		for(Carte c : cartesJoueur)
		{
			System.out.println(c.getNom());
		}
		System.out.println();
	}


}
