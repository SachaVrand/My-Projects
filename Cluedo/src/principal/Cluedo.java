package principal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import gui.GraphicalUserInterface;

/**
 * Classe principal du programme.
 * 
 * Permet de lancer le jeu cluedo. Regroupe quelques fonctions statiques permettant entre autres d'afficher l'aide du menu principal et lancer diff�rents types de partie..
 * @author Sacha et Clement
 *
 */
public class Cluedo
{
	/**
	 * Variable statique permettant de savoir sur quel mode l'utilisateur a choisi de lancer le programme.
	 */
	public static int mode;
	
	/**
	 * Constante repr�sentant le mode graphique du programme.
	 */
	public final static int GUI_MODE = 1;
	
	/**
	 * Constante repr�sentant le mode console du programme.
	 */
	public final static int CONSOLE_MODE = 2;
	
	/**
	 * Scanner permettant de r�cup�rer les commandes entr�es par l'utilisateur. Ouvert au d�marrage et ferm�e � la fin du programme.
	 */
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * Fonction main.
	 * @param args Aucun param�tres attendus.
	 */
	public static void main(String[] args)
	{
		if(args.length > 1)
		{
			System.err.println("Un seul param�tre requis sur la ligne de commande, -gui ou -console.");
			System.exit(1);
		}
		else if(args.length == 0 || (args.length == 1 && args[0].equals("-gui")) )
		{
			mode = GUI_MODE;
			GraphicalUserInterface.afficherFenPrincipal();
		}
		else if(args.length == 1 && args[0].equals("-console"))
		{
			mode = CONSOLE_MODE;
			menuModeConsole();
		}
		else
		{
			System.out.println("Un seul param�tre requis sur la ligne de commande, -gui ou -console.");
			System.exit(1);
		}
	}
	
	/**
	 * M�thode statique permettant d'afficher l'aide pour les commandes du menu principal.
	 */
	private static void afficherAide()
	{
		System.out.println("\nsolo <NombreJoueurs>");
		System.out.println("\t Commencer une partie solo (Humain + Ordinateur)");
		System.out.println("\t <NombreJoueurs> : de 3 � 6\n");
		System.out.println("referee <NombreJoueurs>");
		System.out.println("\t Commencer une partie en tant qu'h�te");
		System.out.println("\t <NombreJoueurs> : de 3 � 6 \n");
		System.out.println("register");
		System.out.println("\t Rechercher une partie en multijoueur");
		System.out.println("\t register <type> <addr> [<nom>]");
		System.out.println("\t <type> : 'ordi' ou 'joueur'.");
		System.out.println("\t <addr> : adresse IP de l'h�te.");
		System.out.println("\t <name> : nom du joueur.\n");
		System.out.println("exit");
		System.out.println("\t Quitter le jeu.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
	}
	
	/**
	 * M�thode permettant de lancer le cluedo et son menu sur la console.
	 */
	private static void menuModeConsole()
	{
		String cmd = "";
		String cmdComplete[];
		
		System.out.println("Cluedo 0.3");
		System.out.println("Taper 'help' pour plus d'informations");
		
		while(!cmd.equals("exit"))
		{
			System.out.print("\nMenu principal > ");
			cmd = sc.nextLine();
			cmdComplete = cmd.split(" ");
			if(cmdComplete.length == 2 && cmdComplete[0].equals("solo") && (cmdComplete[1].equals("3") || cmdComplete[1].equals("4") || cmdComplete[1].equals("5") || cmdComplete[1].equals("6")))
			{	
				List<Joueur> listJoueur = new ArrayList<Joueur>();
				listJoueur.add(new Humain("Joueur 0 (vous)"));
				for(int i = 1; i < Integer.parseInt(cmdComplete[1]); i++)
				{
					Ordi ordi = new Ordi("Joueur "+Integer.toString(i),2);
					ordi.setMyIndInList(i);
					listJoueur.add(ordi);
				}
				PartieSolo p = new PartieSolo(listJoueur);
				p.boucleJeu();
			}
			else if(cmdComplete.length == 2 && cmdComplete[0].equals("referee") && (cmdComplete[1].equals("3") || cmdComplete[1].equals("4") || cmdComplete[1].equals("5") || cmdComplete[1].equals("6")))
			{
				PartieServeur p;
				List<Joueur> listJoueur = new ArrayList<Joueur>();
				for(int i = 0; i < Integer.parseInt(cmdComplete[1]); i++)
				{
					listJoueur.add(new Humain("Joueur "+Integer.toString(i)));
				}
				try 
				{
					p = new PartieServeur(listJoueur);
					p.boucleJeu();
				} 
				catch (IOException e) 
				{
					System.out.println("Le serveur n'as pas pu �tre cr�e.");
				}	
			}
			else if((cmdComplete.length == 3 || cmdComplete.length == 4) && cmdComplete[0].equals("register") && (cmdComplete[1].equals("ordi") || cmdComplete[1].equals("joueur")))
			{
				String nomJoueur = "Joueur";
				String msgErreur;
				Joueur j = null;
				if(cmdComplete.length == 4)
				{
					nomJoueur = cmdComplete[3];
				}
				if(cmdComplete[1].equals("ordi"))
				{
					j = new Ordi(nomJoueur,2);
				}
				else if(cmdComplete[1].equals("joueur"))
				{
					j = new Humain(nomJoueur);
				}
				PartieClient pc = new PartieClient(j, cmdComplete[2]);
				if((msgErreur = pc.connexionServeur()) != PartieClient.NO_ERROR)
				{
					System.out.println(msgErreur);
				}
				else
				{
					pc.boucleJeu();
				}
			}
			else if(cmdComplete.length == 2 && cmdComplete[0].equals("test"))
			{				
				int nombreParties = Integer.parseInt(cmdComplete[1]);
				//[0]-> random, [1] -> level 1, [2]-> level 2
				int[] tabTotal = new int[3];
				int[] tmp = new int[3];
				tabTotal[0] = 0;
				tabTotal[1] = 0;
				tabTotal[2] = 0;
				
				tmp = lancerTestsIA(new String[]{"random","level1","level2"},new int[]{0,1,2},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2];
				tmp = lancerTestsIA(new String[]{"random","level2","level1"},new int[]{0,2,1},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[2] += tmp[1]; tabTotal[1] += tmp[2];
				tmp = lancerTestsIA(new String[]{"level1","random","level2"},new int[]{1,0,2},nombreParties);
				tabTotal[1] += tmp[0]; tabTotal[0] += tmp[1]; tabTotal[2] += tmp[2];
				tmp = lancerTestsIA(new String[]{"level1","level2","random"},new int[]{1,2,0},nombreParties);
				tabTotal[1] += tmp[0]; tabTotal[2] += tmp[1]; tabTotal[0] += tmp[2];
				tmp = lancerTestsIA(new String[]{"level2","level1","random"},new int[]{2,1,0},nombreParties);
				tabTotal[2] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[0] += tmp[2];
				tmp = lancerTestsIA(new String[]{"level2","random","level1"},new int[]{2,0,1},nombreParties);
				tabTotal[2] += tmp[0]; tabTotal[0] += tmp[1]; tabTotal[1] += tmp[2];
				
				System.out.println("\n <TOTAL 3  joueurs> | Random : " + tabTotal[0] + " , level1 : " + tabTotal[1] + " , level2 : " + tabTotal[2] + "\n\n");
				
				tabTotal[0] = 0; tabTotal[1] = 0; tabTotal[2] = 0;
				tmp = lancerTestsIA(new String[]{"random","random","random"},new int[]{0,0,0},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2];
				tmp = lancerTestsIA(new String[]{"level1","level1","level1"},new int[]{1,1,1},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2];
				tmp = lancerTestsIA(new String[]{"level2","level2","level2"},new int[]{2,2,2},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2];
				System.out.println("\nPremier : " + tabTotal[0] + " , Deuxieme : " + tabTotal[1] + " , Troisieme : " + tabTotal[2] + "\n");
				
				tabTotal = new int[4]; tabTotal[0] = 0; tabTotal[1] = 0; tabTotal[2] = 0; tabTotal[3] = 0;
				tmp = lancerTestsIA(new String[]{"random","random","random","random"},new int[]{0,0,0,0},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3];
				tmp = lancerTestsIA(new String[]{"level1","level1","level1","level1"},new int[]{1,1,1,1},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3];
				tmp = lancerTestsIA(new String[]{"level2","level2","level2","level2"},new int[]{2,2,2,2},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3];
				System.out.println("\nPremier : " + tabTotal[0] + " , Deuxieme : " + tabTotal[1] + " , Troisieme : " + tabTotal[2] + " , Quatrieme : " + tabTotal[3] +"\n");
				
				tabTotal = new int[5]; tabTotal[0] = 0; tabTotal[1] = 0; tabTotal[2] = 0; tabTotal[3] = 0; tabTotal[4] = 0;
				tmp = lancerTestsIA(new String[]{"random","random","random","random","random"},new int[]{0,0,0,0,0},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3]; tabTotal[4] += tmp[4];
				tmp = lancerTestsIA(new String[]{"level1","level1","level1","level1","level1"},new int[]{1,1,1,1,1},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3]; tabTotal[4] += tmp[4];
				tmp = lancerTestsIA(new String[]{"level2","level2","level2","level2","level2"},new int[]{2,2,2,2,2},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3]; tabTotal[4] += tmp[4];
				System.out.println("\nPremier : " + tabTotal[0] + " , Deuxieme : " + tabTotal[1] + " , Troisieme : " + tabTotal[2] + " , Quatrieme : " + tabTotal[3] + " , Cinquieme : " +tabTotal[4]+"\n");
				
				tabTotal = new int[6]; tabTotal[0] = 0; tabTotal[1] = 0; tabTotal[2] = 0; tabTotal[3] = 0; tabTotal[4] = 0; tabTotal[5] =0;
				tmp = lancerTestsIA(new String[]{"random","random","random","random","random","random"},new int[]{0,0,0,0,0,0},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3]; tabTotal[4] += tmp[4]; tabTotal[5] += tmp[5];
				tmp = lancerTestsIA(new String[]{"level1","level1","level1","level1","level1","level1"},new int[]{1,1,1,1,1,1},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3]; tabTotal[4] += tmp[4]; tabTotal[5] += tmp[5];
				tmp = lancerTestsIA(new String[]{"level2","level2","level2","level2","level2","level2"},new int[]{2,2,2,2,2,2},nombreParties);
				tabTotal[0] += tmp[0]; tabTotal[1] += tmp[1]; tabTotal[2] += tmp[2]; tabTotal[3] += tmp[3]; tabTotal[4] += tmp[4]; tabTotal[5] += tmp[5];
				System.out.println("\nPremier : " + tabTotal[0] + " , Deuxieme : " + tabTotal[1] + " , Troisieme : " + tabTotal[2] + " , Quatrieme : " + tabTotal[3] + " , Cinquieme : " +tabTotal[4] + " , Sixieme : " + tabTotal[5]+"\n");			
				
				testIAPosAlea(new int[]{2,2,0,0,1,1},nombreParties,20);
				testIAPosAlea(new int[]{2,2,0,1,1},nombreParties,20);
				testIAPosAlea(new int[]{2,2,1,1},nombreParties,20);
				testIAPosAlea(new int[]{2,0,1},nombreParties,20);
			}
			else if(cmdComplete.length == 1 && cmdComplete[0].equals("exit"))
			{
				System.out.println("\nAu revoir.");
			}
			else if(cmdComplete.length == 1 && cmdComplete[0].equals("help"))
			{
				afficherAide();
			}
			else
			{
				System.out.println("Mauvaise commande");
			}
		}
		sc.close();
		System.exit(0);
	}
	
	private static int[] lancerTestsIA(String[] listeNoms, int[] listeNiveaux, int nbPartiesAJouer)
	{	
		if(listeNoms.length != listeNiveaux.length && listeNoms.length < 3 && listeNoms.length > 6)
		{
			return null;
		}
		
		int[] tabRes = new int[listeNoms.length];
		for(int k = 0; k < tabRes.length; tabRes[k] = 0, k++);
		
		for(int i = 0; i< nbPartiesAJouer ; i++)
		{
			List<Joueur> lst = new ArrayList<>();
			for(int j = 0 ; j < listeNoms.length ; j++)
			{
				Ordi ordi = new Ordi(listeNoms[j],listeNiveaux[j]);
				ordi.setMyIndInList(j);
				lst.add(ordi);
			}
			PartieSolo ps = new PartieSolo(lst);
			tabRes[ps.boucleJeuTestIA()]++;
		}
		
		for(int l = 0; l < listeNoms.length ; l++)
		{
			System.out.print(listeNoms[l] + " : " + tabRes[l] + " , ");
		}
		System.out.print("\n");
		
		return tabRes;
	}
	
	private static void melangerTableau(int[] tab)
	{
		Random r = new Random();
		for(int i = 0; i< tab.length; i++)
		{
			int j = r.nextInt(tab.length);
			int tmp = tab[j];
			tab[j] = tab[i];
			tab[i] = tmp;
		}
	}
	
	private static void testIAPosAlea(int[] tabNiv, int nombreParties, int nbTests)
	{
		System.out.println("Tests avec positions al�atoires : ");
		int[] tabTotal = new int[3]; 
		int[] tmp;
		tabTotal[0] = 0; 
		tabTotal[1] = 0; 
		tabTotal[2] = 0;
		String[] tabNoms = new String[tabNiv.length];
		for(int j = 0; j < nbTests; j++)
		{
			melangerTableau(tabNiv);
			for(int i = 0; i < tabNiv.length; i++)
			{
				tabNoms[i] = "level" + tabNiv[i];
			}
			tmp = lancerTestsIA(tabNoms,tabNiv,nombreParties);
			for(int i = 0; i < tabNiv.length; i++)
			{
				tabTotal[tabNiv[i]] += tmp[i];
			}
		}
		System.out.println("Total | Random : " + tabTotal[0] + " , level 1 : " + tabTotal[1] + " , level 2 : " + tabTotal[2] +"\n");
	}
	

}
