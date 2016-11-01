import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Main du jeu Othello.
 * Regroupe différentes fonctions statiques telles que le main, et les fonction permettant d'afficher les diférents panel de jeu et de menu.
 * @author Vrand
 *
 */
public class OthelloMain 
{
	/**
	 * Fenêtre principal de l'application.
	 */
	public static JFrame mainFrame = null;
	
	/**
	 * Menu principal de l'application.
	 */
	private static MenuPrincipal mainMenu = null;
	
	/**
	 * Panel de jeu qui sera afficher quand un type de jeu sera lancer.
	 */
	private static Jeu jeu = null;
	
	/**
	 * Dimension de l'écran de l'utilisateur.
	 */
	public static Dimension dimensionEcran = null;
	
	/**
	 * Chemin permettant d'accder au images associées à la taille de l'écran.
	 */
	public static String imagePath = null;
	
	/**
	 * Main de l'othello.
	 * @param args aucun arguments neccessaires.
	 */
	public static void main(String[] args) 
	{
		mainFrame = new JFrame("Othello");
		dimensionEcran = new Dimension(mainFrame.getToolkit().getScreenSize());
		choixRepertoireImages();
		mainMenu = new MenuPrincipal();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		BorderLayout bl = new BorderLayout();
		mainFrame.getContentPane().setLayout(bl);
		mainFrame.getContentPane().add(mainMenu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
		mainFrame.setVisible(true);
	}
	
	/**
	 * Méthode qui crée un instance de MenuPrincipal et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */
	public static void afficherMenuPrincipal()
	{
		jeu = null;
		mainFrame.setEnabled(true);
		mainMenu = new MenuPrincipal();
		mainFrame.setContentPane(mainMenu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}

	/**
	 * Méthode qui crée un instance de JeuJcj et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */
	public static void afficherJeuJcj()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcJ();
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * Méthode qui désactive la fenêtre principale. (setEnabled(false)).
	 */
	public static void desactiverMainFrame()
	{
		mainFrame.setEnabled(false);
	}
	
	/**
	 * Méthode qui crée un instance de JeuJcIAFacile et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */
	public static void afficherJeuJcIAFacile()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcIAFacile(Couleur.BLANC);
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * Méthode qui crée un instance de JeuJcIAMoyen et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */
	public static void afficherJeuJcIAMoyen()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcIAMoyen(Couleur.BLANC);
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * Méthode qui crée un instance de JcJcIADifficile et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */
	public static void afficherJeuJcIADifficile()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcIADifficile(Couleur.BLANC);
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * Méthode qui crée un instance de MenuChoixDifficultée et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */	
	public static void afficherMenuChoixDifficultee()
	{
		MenuChoixDifficulte menu = new MenuChoixDifficulte();
		mainFrame.setContentPane(menu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * Méthode qui crée un instance de MenuChoixSauvegardes et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */	
	public static void afficherPartiesPrecedentes()
	{
		MenuChoixSauvegarde menu = new MenuChoixSauvegarde();
		mainFrame.setContentPane(menu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * Méthode qui relance un instance du Jeu en cours en créant un nouvelle instance de ce jeu 
	 * et en remplacant le ContentPane de la fenêtre principal par ce nouveau panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 */
	public static void rejouer()
	{
		if(jeu instanceof JeuJcJ)
		{
			OthelloMain.afficherJeuJcj();
		}
		else if(jeu instanceof JeuJcIAFacile)
		{
			OthelloMain.afficherJeuJcIAFacile();
		}
		else if(jeu instanceof JeuJcIADifficile)
		{
			OthelloMain.afficherJeuJcIADifficile();
		}
	}
	
	/**
	 * Méthode qui détermine quelle taille d'image choisir, et change la variable de chemin des images, pour mieux adapater le jeu à l'écran de l'utilisateur.
	 */
	private static void choixRepertoireImages()
	{
		if(dimensionEcran.height > 768 && dimensionEcran.width > 1366)
		{
			imagePath = "Images/Grandes/";
		}
		else
		{
			imagePath = "Images/Petites/";
		}
	}
	
	/**
	 * Méthode qui crée un instance de AffichageSauvegarde et remplace le ContentPane de la fenêtre princpal par ce panel.
	 * Repositionne aussi la fenêtre principal au milieu de l'écran.
	 * @param save Sauvegarde représentant la sauvegarde à rejouer.
	 */	
	public static void afficherPartieSauvegarder(Sauvegarde save)
	{
		AffichageSauvegarde jeuReplay = new AffichageSauvegarde(save);
		mainFrame.setContentPane(jeuReplay);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}

}
