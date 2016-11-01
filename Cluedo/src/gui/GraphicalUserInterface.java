package gui;

import java.awt.Dimension;
import java.io.IOException;
import java.io.PipedInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import principal.Carte;
import principal.Cluedo;
import principal.Humain;
import principal.Joueur;
import principal.Ordi;
import principal.PartieClient;
import principal.PartieServeur;
import principal.PartieSolo;

/**
 * Classe fournissant des m�thodes et variables statiques permettant de g�rer l'interface graphique du programme.
 * Cette classe ne peut pas �tre d�riv�e ou instanci�e.
 * @author Sacha Cl�ment
 *
 */
public final class GraphicalUserInterface {
	
	/**
	 * Constructeur de la classe GraphicalUserInterface, mais ne doit pas �tre utilis�e car la classe ne doit pas �tre instanci�e.
	 */
	private GraphicalUserInterface()
	{
		throw new RuntimeException();
	}
	
	/**
	 * Variable statique repr�sentant la fen�tre �tant affich�e quand le joueur doit r�futer.
	 */
	private static FenetreRefuter fenRefuter;
	
	/**
	 * Variable statique repr�sentant la fen�tre �tant affich�e quand le joueur doit jouer un coup.
	 */
	private static FenetreJouer fenJouer;
	
	/**
	 * Variable statique repr�sentant la fenetre principal du programme sous forme graphique.
	 */
	private static JFrame fenetrePrincipal = null;
	
	/**
	 * Variable static permettant repr�sentant les dimensions de l'�cran de l'utilisateur. 
	 */
	public static Dimension screenSize = null;
	
	/**
	 * Fonction permettant de d�safficher la fen�tre pour r�futer. Cette op�ration est toujours effectu�e dans l'EDT. 
	 * D�sactive aussi le bouton quit de la fen�tre principal si elle contient le panel jeu.
	 */
	public static void desafficherFenRefuter()
	{
		if(fenRefuter == null) return;
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenRefuter.setVisible(false);
					fenRefuter.closePipeOut();
					fenRefuter = null;
					if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
					{
						PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
						tmp.setEnabledBtnQuit(false);
					}
				}
			});		
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenRefuter.setVisible(false);
			fenRefuter.closePipeOut();
			fenRefuter = null;
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(false);
			}
		}
	}
	
	/**
	 * Fonction permettant d�safficher la fen�tre pour jouer. Cette op�ration est toujours effectu�e dans l'EDT.
	 * D�sactive aussi le bouton quit de la fen�tre principal si elle contient le panel jeu.
	 */
	public static void desafficherFenJouer()
	{
		if(fenJouer == null) return;
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					fenJouer.setVisible(false);
					fenJouer.closePipeOut();
					fenJouer = null;
					if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
					{
						PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
						tmp.setEnabledBtnQuit(false);
					}
					
				}
			});		
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenJouer.setVisible(false);
			fenJouer.closePipeOut();
			fenJouer = null;
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(false);
			}
		}
	}
	
	/**
	 * Fonction permettant d'afficher et d'instancier la fen�tre principale. Cette op�ration est toujours effectu�e dans l'EDT.
	 * Le content pane de la fenetre principale instanci�e est le menu principal.
	 */
	public static void afficherFenPrincipal()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					fenetrePrincipal = new JFrame("Cluedo 1.0");
					fenetrePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					fenetrePrincipal.setResizable(false);
					afficherGUIMenuPrincipal();
					screenSize = fenetrePrincipal.getToolkit().getScreenSize(); 
					fenetrePrincipal.setLocation((screenSize.width/2)-(fenetrePrincipal.getWidth()/2), (screenSize.height/2)-(fenetrePrincipal.getHeight()/2));
					fenetrePrincipal.setVisible(true);
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenetrePrincipal = new JFrame("Cluedo 1.0");
			fenetrePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetrePrincipal.setResizable(false);
			afficherGUIMenuPrincipal();
			screenSize = fenetrePrincipal.getToolkit().getScreenSize(); 
			fenetrePrincipal.setLocation((screenSize.width/2)-(fenetrePrincipal.getWidth()/2), (screenSize.height/2)-(fenetrePrincipal.getHeight()/2));
			fenetrePrincipal.setVisible(true);
		}
	}
	
	/**
	 * Fonction permettant d'afficher le menu principal dans la fen�tre principale. Cette op�ration est toujours effectu�e dans l'EDT.
	 */
	public static void afficherGUIMenuPrincipal()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuPrincipal());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuPrincipal());
			fenetrePrincipal.pack();
		}
		
	}
	
	/**
	 * Fonction permettant d'afficher le menu solo dans la fen�tre principale. Cette op�ration est toujours effectu�e dans l'EDT.
	 */
	public static void afficherGUIMenuSolo()
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuSolo());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuSolo());
			fenetrePrincipal.pack();
		}	
	}
	
	/**
	 * Fonction permettant d'afficher le menu register dans la fen�tre principale. Cette op�ration est toujours effectu�e dans l'EDT.
	 */
	public static void afficherGUIMenuRegister()
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuRegister());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuRegister());
			fenetrePrincipal.pack();
		}	
	}
	
	/**
	 * Fonction permettant d'afficher le menu referee dans la fen�tre principale. Cette op�ration est toujours effectu�e dans l'EDT.
	 */
	public static void afficherGUIMenuReferee()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuReferee());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuReferee());
			fenetrePrincipal.pack();
		}	
	}
	
	/**
	 * Fonction permettant d'afficher et d'instancier la fen�tre pour jouer. Cette op�ration est toujours effectu�e dans l'EDT.
	 * Active le bouton quit de la fenetre principal si son contentPane est le panel jeu.
	 */
	public static void afficherFenJouer()
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						fenJouer = new FenetreJouer();
						fenJouer.setLocation((screenSize.width/2)-(fenJouer.getWidth()/2), (screenSize.height/2)-(fenJouer.getHeight()/2));
						fenJouer.setVisible(true);
						if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
						{
							PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
							tmp.setEnabledBtnQuit(true);
						}
					}
				});
			} catch (InvocationTargetException e1) {
				System.err.println("Imposible de cr�er la fen�tre pour jouer");
				System.exit(1);
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				System.err.println("Imposible de cr�er la fen�tre pour jouer");
				System.exit(1);
				e1.printStackTrace();
			}
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenJouer = new FenetreJouer();
			fenJouer.setLocation((screenSize.width/2)-(fenJouer.getWidth()/2), (screenSize.height/2)-(fenJouer.getHeight()/2));
			fenJouer.setVisible(true);
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(true);
			}
		}
		//connecter le pipeOut avec le pipeIn pour �tablir la connexion entre les deux thread 
		try {
			PipedInputStream pipeIn = new PipedInputStream();
			pipeIn.connect(fenJouer.getPipeOut());
			Cluedo.sc.close();
			Cluedo.sc = new Scanner(pipeIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction permettant d'afficher et d'instancier la fen�tre pour r�futer. Cette op�ration est toujours effectu�e dans l'EDT.
	 * Active le bouton quit de la fenetre principal si son contentPane est le panel jeu.
	 * @param listeCartesCommun liste des cartes que le joueur � droit de montrer pour r�futer.
	 */
	public static void afficherFenRefuter(final List<Carte> listeCartesCommun)
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						fenRefuter = new FenetreRefuter(listeCartesCommun);
						fenRefuter.setLocation((screenSize.width/2)-(fenRefuter.getWidth()/2), (screenSize.height/2)-(fenRefuter.getHeight()/2));
						fenRefuter.setVisible(true);
						if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
						{
							PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
							tmp.setEnabledBtnQuit(true);
						}
					}
				});
			} catch (InvocationTargetException e) {
				System.err.println("Imposible de cr�er la fen�tre pour r�futer");
				System.exit(1);
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.err.println("Imposible de cr�er la fen�tre pour r�futer");
				System.exit(1);
				e.printStackTrace();
			}
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			fenRefuter = new FenetreRefuter(listeCartesCommun);
			fenRefuter.setLocation((screenSize.width/2)-(fenRefuter.getWidth()/2), (screenSize.height/2)-(fenRefuter.getHeight()/2));
			fenRefuter.setVisible(true);
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(true);
			}
		}
		//connecter le pipeOut avec le pipeIn pour �tablir la connexion entre les deux thread 
		try {
			PipedInputStream pipeIn = new PipedInputStream();
			pipeIn.connect(fenRefuter.getPipeOut());
			Cluedo.sc.close();
			Cluedo.sc = new Scanner(pipeIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction permettant d'afficher le panel de jeu dans la fen�tre principale. Cette op�ration est toujours effectu�e dans l'EDT.
	 * @param listeJoueurs Liste des joueurs de la partie. Doit contenir le joueur jouant avec toutes ses cartes, et les autres joueurs mais sans leurs cartes.
	 * @param j Joueur jouant la partie.
	 * @param constantePanel Constante de PanelJeu pour cr�er un panel adapt� � celui voulu.	
	 */
	public static void afficherGUIJeu(final List<Joueur> listeJoueurs, final Joueur j, final int constantePanel)
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						PanelJeu panelJeu = new PanelJeu(j,listeJoueurs,constantePanel);
						fenetrePrincipal.setContentPane(panelJeu);
						fenetrePrincipal.pack();
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				System.err.println("Imposible de cr�er le panel de jeu");
				System.exit(1);
				e.printStackTrace();
			}
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			PanelJeu panelJeu = new PanelJeu(j,listeJoueurs,constantePanel);
			fenetrePrincipal.setContentPane(panelJeu);
			fenetrePrincipal.pack();
		}
	}
	
	/**
	 * Fonction qui permet d'afficher une popup avec le joueur qui a gagn�.
	 * @param message Message indiquant le joueur gagnant.
	 */
	public static void afficherPopUpVictoire(final String message)
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(fenetrePrincipal, message);
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			JOptionPane.showMessageDialog(fenetrePrincipal, message);
		}
	}
	
	/**
	 * Fonction d'envoyer le message exit a travers le pipeStream de la fenetre jouer ou r�futer.
	 */
	public static void sendExitInGame()
	{
		if(fenJouer == null && fenRefuter == null)
			return;
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					if(fenJouer != null)
					{
						fenJouer.sendExit();
					}
					else if(fenRefuter != null)
					{
						fenRefuter.sendExit();
					}
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			if(fenJouer != null)
			{
				fenJouer.sendExit();
			}
			else if(fenRefuter != null)
			{
				fenRefuter.sendExit();
			}
		}
	}
	
	/**
	 * Fonction qui met � jour le panel Jeu. Met � jour les icone de joueurs et leurs tooltips. Met � jour le panel derni�re carte.
	 * @param derCarte Derni�re carte qui � �t� montrer au joueur.
	 * @param indJoueur indice du joueur dans la liste qui � montrer la carte.
	 */
	public static void updatePanelJeu(final Carte derCarte, final int indJoueur)
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
					{
						PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
						tmp.updatePlayersIcon();
						tmp.updateDerCarte(derCarte, indJoueur);
						fenetrePrincipal.pack();
					}
				}
			});
		}
		//sinon on est d�j� dans l'EDT
		else
		{
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.updatePlayersIcon();
				tmp.updateDerCarte(derCarte, indJoueur);
			}
		}
	}
	
	/**
	 * Fonction qui permet de lancer une partie en tant que client. Cr�e un nouveau thread pour la boucle de la partie. 
	 * Si la connection n'a pas pu �tre effectu�e, affiche le message d'erreur dans le label d'erreur du menu register.
	 * @param j Joueur jouant la partie.
	 * @param hote Adresse de l'hote de la partie, sous forme de string.
	 */
	public static void lancerPartieClient(final Joueur j, final String hote)
	{
		Thread threadClient = new Thread(new Runnable() {
			
			@Override
			public void run() {

				final String err;
				PartieClient pc = new PartieClient(j, hote);
				err = pc.connexionServeur();
				if(!err.equals(PartieClient.NO_ERROR))
				{
					//afficher erreur
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof MenuRegister)
							{
								MenuRegister tmp = (MenuRegister)fenetrePrincipal.getContentPane();
								tmp.changeTextlblError(err);
								tmp.setButtonsEnabled(true);
								fenetrePrincipal.pack();
							}
						}
					});
				}
				else
				{
					String[] tmpListeNomsJoueurs = pc.getListeJoueurs();
					
					//creation liste joueurs
					List<Joueur> listeJoueurs = new ArrayList<Joueur>();
					for(int i = 0; i < tmpListeNomsJoueurs.length; i++)
					{
						if(i == pc.getMyNum())
						{
							listeJoueurs.add(j);
							j.setMyIndInList(i);
						}
						else
							listeJoueurs.add(new Humain(tmpListeNomsJoueurs[i]));
					}
					j.setPlayersInTheGame(listeJoueurs);
					final int cst = (j instanceof Humain)?(PanelJeu.PANEL_HUMAIN):(PanelJeu.PANEL_ORDI);
					afficherGUIJeu(listeJoueurs, j, cst);
					pc.boucleJeu();
					if(pc.getPartieFinie())
						afficherPopUpVictoire(pc.getNomGagnant() + " a gagn� la partie!");
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
							{
								PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
								tmp.setEnabledBtnQuit(true);
							}	
						}
					});
				}
			}
		});
		threadClient.start();
	}
	
	/**
	 * Fonction qui permet de lancer une partie en solo. Cr�e un nouveau thread pour la boucle de la partie. 
	 * @param nomJoueur le nom du joueur jouant la partie.
	 * @param nivIA le niveau choisi pour l'IA de la partie.
	 * @param nbJoueurs le nombre de joueurs choisi pour la partie.
	 */
	public static void lancerPartieSolo(final String nomJoueur, final int nivIA, final int nbJoueurs)
	{
		Thread threadJeu = new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<Joueur> listeJoueurs = new ArrayList<Joueur>();
				List<Joueur> listeAAffJeu = new ArrayList<Joueur>();
				Joueur humain = new Humain(nomJoueur);
				humain.setMyIndInList(0);
				listeJoueurs.add(humain);
				listeAAffJeu.add(humain);
				for(int i = 1; i < nbJoueurs; i++)
				{
					Ordi ordi = new Ordi("Joueur "+Integer.toString(i),nivIA);
					ordi.setMyIndInList(i);
					listeJoueurs.add(ordi);
					listeAAffJeu.add(new Ordi("Joueur "+Integer.toString(i),nivIA));
				}
				PartieSolo partie = new PartieSolo(listeJoueurs);
				afficherGUIJeu(listeAAffJeu,humain,PanelJeu.PANEL_HUMAIN);
				humain.setPlayersInTheGame(listeAAffJeu);
				partie.boucleJeu();
				if(partie.getPartieFinie())
				{
					afficherPopUpVictoire(partie.getNomGagnant() + " a gagn� la partie!");
				}
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
						{
							PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
							tmp.setEnabledBtnQuit(true);
						}	
					}
				});
			}
		});
		threadJeu.start();
	}

	public static void lancerPartieServer(final List<Joueur> listJoueur)
	{
		Thread threadJeu = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try
					{
					Joueur j = new Humain("Carte � trouver");
					PartieServeur ps = new PartieServeur(listJoueur);
					for(Carte carte : ps.getCartesADecouvrir())
					{
						j.ajouterCarte(carte);
					}
					afficherGUIJeu(listJoueur,j,PanelJeu.PANEL_HOTE);
					ps.boucleJeu();
				
					SwingUtilities.invokeLater(new Runnable() {
					
						@Override
						public void run() {
							if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
							{
								PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
								tmp.setEnabledBtnQuit(true);
							}	
						}
					});
				}
				catch (IOException e)
				{
					return;
				}
			}
		});
		threadJeu.start();
	}
	
}
