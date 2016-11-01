import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Classe jeu correspondant à un panel regroupant le menu en jeu et le plateau de jeu.
 * @author Vrand
 *
 */
public class Jeu extends JPanel{

	private static final long serialVersionUID = 2348660822982132166L;
	
	/**
	 * Panel qui sera afficher en haut de la fenêtre représentant le menu.
	 */
	protected MenuEnJeu menu = null;
	
	/**
	 * Panel qui sera affichée en bas de la fenêtre repéresentant le plateau de jeu.
	 */
	protected Plateau plateau = null;
	
	/**
	 * Fenêtre qui sera lancée pour afficher le vainqueur en fin de partie et d'autre options.
	 */
	protected PopUpVictoire popUp = null;
	
	/**
	 * Panel qui correspond au glass pane de la main frame.
	 */
	protected JPanel glass = null;
	
	/**
	 * Timer qui permet d'afficher le passage des tours.
	 */
	protected Timer t = null;
	
	/**
	 * Label qui affiche le message de passage de tour.
	 */
	protected JLabel lblTourPasse = null;
	
	/**
	 * Booléen qui permet de svaoir si le timer de jeu des sous-classes est activé. True si oui, sinon false.
	 */
	protected boolean timerJeuIAisRunning = false;
	
	/**
	 * Booléen qui permet de svaoir si la partie est fini après deux tours passés. True si finie, sinon false.
	 */
	protected boolean partieFini = false;
	
	/**
	 * Timer qui lance le deuxième passage de tour successif et donc qui annonce la fin de la partie.
	 */
	protected Timer t2 = null;
	
	/**
	 * Chaine répresentant le temps de jeu total.
	 */
	protected String tempsDeJeuTotal = null;
	
	/**
	 * Instancie un nouveau panel constitué d'un MenuEnJeu et d'un Plateau avec pour layout manager un borderlayout.
	 * Set la pop à null, et le label de la couleur jouant dans le menu à Noir.
	 */
	public Jeu()
	{
		super(new BorderLayout());
		this.menu = new MenuEnJeu();
		this.plateau = new Plateau(Couleur.NOIR,this);
		this.popUp = null;
		this.load();
		this.loadTimer();
		this.loadTimer2();
		this.menu.setLabelCouleurJouant(Couleur.NOIR);
	}
	
	/**
	 * Méthode qui charge les élements graphiques présent en permanence sur le panel
	 */
	private void load()
	{
		this.add(menu,BorderLayout.NORTH);
		this.add(plateau,BorderLayout.SOUTH);
		lblTourPasse = new JLabel();
	}
	
	/**
	 * Méthode qui les éléments graphiques sur le glass pane
	 */
	protected void loadGlass()
	{
		glass.setLayout(new BorderLayout());
		JPanel panelLabel = new JPanel(new BorderLayout());
		panelLabel.add(lblTourPasse,BorderLayout.CENTER);
		panelLabel.setPreferredSize(plateau.getSize());
		panelLabel.setOpaque(false);
		glass.add(panelLabel,BorderLayout.SOUTH);
		panelLabel.addMouseListener(new MouseListener() {
			//On recupere les actions de la souris sur le label pour bloquer les clicks sur des pions par l'utilisateur le temps du timer
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
		});
	}
	
	/**
	 * Méthode qui crée le timer de passage de tour
	 */
	private void loadTimer()
	{
		t = new Timer(1000,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Timer t = (Timer)e.getSource();
				t.stop();
				lblTourPasse.setIcon(new ImageIcon());;
				if(!timerJeuIAisRunning && !t2.isRunning())
				{
					glass.setVisible(false);
					glass.setEnabled(false);
				}
				if(partieFini)
				{
					afficherPopUp();
				}
			}
		});
	}
	
	/**
	 * Méthode qui crée le timer qui gère la fin de partie après 2 tours passé d'affilées
	 */
	private void loadTimer2()
	{
		t2 = new Timer(1500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Timer tmp = (Timer)arg0.getSource();
				tmp.stop();
				lancerTourPasse();
				partieFini = true;
			}
		});
	}
	
	/**
	 * Méthode de mise à jour du panel après le tour d'un joueur. Met à jour les scores, le joueur jouant,
	 * l'affichage de la pop-up en cas de fin de partie,
	 * le passage du tour d'un joueur...
	 */
	public void update()
	{
		menu.setPoints(getPoints());
		menu.setLabelCouleurJouant(plateau.getCouleurJouant());
		if(glass == null)
		{
			glass = (JPanel)OthelloMain.mainFrame.getGlassPane();
			this.loadGlass();
		}
		if(getPlateauRempli())
		{
			afficherPopUp();
		}
		else
		{
			if(!getTourJouable())
			{	
				lancerTourPasse();
				
				if(plateau.getCouleurJouant().equals(Couleur.NOIR))
				{
					plateau.setCouleurJouant(Couleur.BLANC);
				}
				else
				{
					plateau.setCouleurJouant(Couleur.NOIR);
				}
				
				plateau.activerCasesJouables(plateau.getCouleurJouant());
				
				//Fin de partie
				if(!getTourJouable())
				{
					t2.start();
				}
			}
		}
	}
	
	/**
	 * Méthode qui affiche la pop-up de fin de partie
	 */
	private void afficherPopUp()
	{
		tempsDeJeuTotal = menu.getTime();
		menu.arreterChrono();
		popUp = new PopUpVictoire(getGagnant(),this);
		popUp.setVisible(true);
		popUp.setLocation((int)((OthelloMain.dimensionEcran.width / 2) - (popUp.getWidth()/2)), (int)((OthelloMain.dimensionEcran.getHeight()/2)-(popUp.getHeight()/2)));
		OthelloMain.desactiverMainFrame();
	}
	
	/**
	 * Méthode qui gère le passage du tour d'un des joueurs en lancant le timer et activant le glass pane avec le bon message.
	 */
	private void lancerTourPasse()
	{
		if(plateau.getCouleurJouant().equals(Couleur.NOIR))
		{
			lblTourPasse.setIcon(Ressources.noirPasseTour);
			plateau.ajouterUnTourPasseALaSauvegarde(lblTourPasse.getIcon());
		}
		else
		{
			lblTourPasse.setIcon(Ressources.blancPasseTour);
			plateau.ajouterUnTourPasseALaSauvegarde(lblTourPasse.getIcon());
		}
		glass.setVisible(true);
		glass.setEnabled(true);
		t.start();
	}
	
	/**
	 * Méthode qui recupère si le tour du joueur courant est jouables, c'est à dire si il y a au moins une case jouable.
	 * @return vrai si le tour est jouable, faux sinon.
	 */
	public boolean getTourJouable()
	{
		Pion[][] tableauPion = plateau.getTableauPion();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tableauPion[i][j].getEstJouable())
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Méthode qui retourne les points des deux joueurs.
	 * @return res[0] -> noir, res[1] -> blanc
	 */
	public int[] getPoints()
	{
		int compteurNoir = 0,compteurBlanc = 0;
		Pion[][] tableauPion = plateau.getTableauPion();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tableauPion[i][j].getCouleur().equals(Couleur.NOIR))
				{
					compteurNoir++;
				}
				else if(tableauPion[i][j].getCouleur().equals(Couleur.BLANC))
				{
					compteurBlanc++;
				}
			}
		}
		int[] res = {compteurNoir,compteurBlanc};
		return res;
	}
	
	/**
	 * Méthode qui retourne le message à afficher lors de la fin de la partie
	 * @return Message disant quelle couleur a gagnée, et 'Egalité' en cas d'égalité.
	 */
	public String getGagnant()
	{
		int[] points = getPoints();
		
		if(points[0] > points[1])
		{
			return "'NOIR' remporte la partie";
		}
		else if(points[0] < points[1])
		{
			return "'BLANC' remporte la partie";
		}
		else
		{
			return "Egalité";
		}
	}
	
	/**
	 * Méthode qui retourne si le plateau est rempli ou non.
	 * @return Vrai s'il est rempli, sinon faux.
	 */
	public boolean getPlateauRempli()
	{
		Pion[][] tableauPion = plateau.getTableauPion();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tableauPion[i][j].getCouleur().equals(Couleur.VIDE) || tableauPion[i][j].getCouleur().equals(Couleur.SURBRILLANCE))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Méthode qui enregistre une partie dans le repertoire Saves avec comme nom de fichier la date et l'heure du jour. 
	 */
	public void enregistrerPartie()
	{
		Pion[][] tab =  plateau.getTableauPion();
		PionSauvegarde[][] tabSave = new PionSauvegarde[8][8];
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				tabSave[i][j] = new PionSauvegarde(tab[i][j].getJouerPar(), tab[i][j].getCouleur(), tab[i][j].getCoup());
			}
		}
		
		String format = "dd-MM-yy_HH-mm-ss"; 
		SimpleDateFormat formater = new SimpleDateFormat(format); 
		Date date = new Date();
		String datePartie = formater.format(date);
		Sauvegarde save = new Sauvegarde(date, tabSave, this.toString(),tempsDeJeuTotal,plateau.getListeSauvegarde());
		
		//Serialisation de la partie
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("Saves\\" + datePartie + ".txt"))));
			out.writeObject(save);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		return "Jeu";
	}
}
