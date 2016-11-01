package vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import donnees.EtatPartie;
import donnees.Joueur;
import donnees.PartieVersus;
import principal.Ressources;
import principal.RessourcesAudio;

/**
 * Classe représentant la vue d'un joueur. Comprend sa grille et le panel de prévention.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class JoueurView extends JPanel implements Observer{
	
	/**
	 * Dimension de ce panel.
	 */
	public static Dimension PREFDIM = new Dimension(GrilleView.PREFDIM.width, GrilleView.PREFDIM.height + PanelPrevention.PREFHEIGHT);
	
	/**
	 * Image du "push any key".
	 */
	private final static Image IMGWAITINGKEY = Ressources.PUSHANYKEY.getImage();
	
	/**
	 * Image du win.
	 */
	private final static Image WINIMG = Ressources.WINLOGO.getImage();
	
	/**
	 * Image du lose.
	 */
	private final static Image LOSEIMG = Ressources.LOSELOGO.getImage();
	
	/**
	 * Hauteur de l'image indiquant la victoire ou la defaite.
	 */
	private static int IMAGEFINHEIGHT = Ressources.WINLOGO.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de l'image indiquant la victoire ou la defaite.
	 */
	private static int IMAGEFINWIDTH = Ressources.WINLOGO.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Hauteur de l'image du ready au compte a rebours.
	 */
	private static int READYHEIGHT = Ressources.IMGREADY.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de l'image du ready au compte a rebours.
	 */
	private static int READYWIDTH = Ressources.IMGREADY.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Hauteur de l'image des chiffres au compte a rebous.
	 */
	private static int NUMBERCARHEIGHT = Ressources.IMG1.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de l'image des chiffres au compte a rebours.
	 */
	private static int NUMBERCARWIDTH = Ressources.IMG1.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Hauteur de l'image du push any key.
	 */
	private static int IMGWAITINGKEYHEIGHT = Ressources.PUSHANYKEY.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de l'image du push any key.
	 */
	private static int IMGWAITINGKEYWIDTH = Ressources.PUSHANYKEY.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Image du ready.
	 */
	private final static Image READYIMG = Ressources.IMGREADY.getImage();	
	
	/**
	 * Vue de la grille que possède cette vue.
	 */
	private GrilleView grilleView;
	
	/**
	 * Vue du score du joueur représenter par cette vue.
	 */
	private ScoreView scoreView;
	
	/**
	 * Modele du joueur représenter par cette vue.
	 */
	private Joueur joueur;
	
	/**
	 * Image de fin. win or lose.
	 */
	private Image imageFin;
	
	/**
	 * Boolean permettant de savoir si l'on attend la manche suivante ou non.
	 */
	private boolean isWaiting;
	
	/**
	 * Timer permettant de faire l'animation du compte a rebours.
	 */
	private Timer compteARebours;
	
	/**
	 * Entier permettant de savoir ou l'on en est dans le compte a rebours.
	 */
	private int chiffreCompteAR = -2;
	
	/**
	 * Panel permettant de placer la grille dans ce panel.
	 */
	private JPanel panelGrille;
	
	/**
	 * Panel de prevention que possède cette vue.
	 */
	private PanelPrevention panelPrev;
	
	
	/**
	 * Constructeur de {@link JoueurView}.
	 * @param joueur Modele que cette vue représente.
	 * @param backgroundImage Image de fond de la grille contenu dans cette vue.
	 * @param noJoueur Numéro du joueur représenter par cette vue.
	 */
	public JoueurView(Joueur joueur, Image backgroundImage, int noJoueur) {
		super(new BorderLayout());
		this.joueur = joueur;
		this.imageFin = null;
		this.isWaiting = false;
		initComposants(backgroundImage, noJoueur);
		initTimer();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant.
	 * @param backgroundImage Image de fond de la grille.
	 * @param noJoueur Numero du joueur qui possède ce panel.
	 */
	private void initComposants(Image backgroundImage, int noJoueur)
	{
		grilleView = new GrilleView(joueur.getGrille(), backgroundImage, noJoueur);
		scoreView = new ScoreView(joueur.getScore(), noJoueur);
		this.panelPrev = new PanelPrevention();
		this.panelGrille = new JPanel(new BorderLayout()){
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(imageFin != null)
				{
					MurTransition tmp = grilleView.getMurTransition();
					int x = (this.getWidth()/2) - (IMAGEFINWIDTH/2);
					int y = (this.getHeight()/3) - (IMAGEFINHEIGHT/2) - (tmp.getHeight() - tmp.getLocation().y);
					g.drawImage(imageFin, x, y, IMAGEFINWIDTH, IMAGEFINHEIGHT, null);
				}
			}
		};
		
		this.panelGrille.add(grilleView,BorderLayout.CENTER);
		
		this.add(panelPrev,BorderLayout.NORTH);
		this.add(panelGrille,BorderLayout.CENTER);
		this.setPreferredSize(PREFDIM);
		this.setOpaque(false);
	}
	
	/**
	 * Méthode qui initialise le timer de compte a rebours.
	 */
	private void initTimer()
	{
		compteARebours = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chiffreCompteAR++;
				if(chiffreCompteAR < 3  && chiffreCompteAR > -1)
				{
					RessourcesAudio.lancerSonCAR321();
				}
				if(chiffreCompteAR == 3)
				{
					RessourcesAudio.lancerSonCARGo();
					chiffreCompteAR = -2;
					((Timer)e.getSource()).stop();
				}
				repaint();
			}
		});
		compteARebours.setInitialDelay(0);
	}
	
	/**
	 * Méthode qui retourne la vue du socre de cette vue.
	 * @return la vue du score de cette vue.
	 */
	public ScoreView getScoreView() {
		return scoreView;
	}
	
	/**
	 * Méthode qui retourne la vue de la grille de cette vue.
	 * @return la vue de la grille de cette vue.
	 */
	public GrilleView getGrilleView() {
		return grilleView;
	}
	
	/**
	 * Méthode qui retourne le joueur représenter par cette vue.
	 * @return le joueur représenter par cette vue.
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Méthode qui retourne le timer de compte a rebours.
	 * @return Le timer de compte a rebours.
	 */
	public Timer getCompteARebours() {
		return compteARebours;
	}
	
	/**
	 * Méthode qui retourne le panel de prevention de cette vue.
	 * @return le panel de prevention de cette vue.
	 */
	public PanelPrevention getPanelPrev() {
		return panelPrev;
	}
	
	/**
	 * Méthode qui permet d'afficher la victoire ou la defaite de ce joueur sur cette vue.
	 * @param hasWon Si le joueur a gagné true, sinon false.
	 */
	public void afficherFinPartie(boolean hasWon)
	{
		if(hasWon)
		{
			imageFin = WINIMG;
		}
		else
		{
			imageFin = LOSEIMG;
		}
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		/*if(imageFin != null)
		{
			MurTransition tmp = grilleView.getMurTransition();
			int x = (this.getWidth()/2) - (IMAGEFINWIDTH/2);
			int y = (this.getHeight()/3) - (IMAGEFINHEIGHT/2) - (tmp.getHeight() - tmp.getLocation().y);
			g.drawImage(imageFin, x, y, LOGOFINWIDTH, LOGOFINHEIGHT, null);
		}*/
		if(chiffreCompteAR > -2)
		{
			
			int x = (this.getWidth()/2) - (READYWIDTH/2);
			int y = (this.getHeight()/4) - (READYHEIGHT/2);
			if(chiffreCompteAR < 1)
			{
				g.drawImage(READYIMG, x, y, READYWIDTH, READYHEIGHT, null);
			}
			
			if(chiffreCompteAR > -1)
			{
				Image chiffre = Ressources.TABIMGCHIFFRE[chiffreCompteAR].getImage();
				int x1 = (this.getWidth()/2) - (NUMBERCARWIDTH/2);
				int y1 = y + READYHEIGHT + NUMBERCARHEIGHT;
				g.drawImage(chiffre, x1, y1, NUMBERCARWIDTH, NUMBERCARHEIGHT, null);
			}
		}
		
		if(isWaiting)
		{
			int x = (this.getWidth()/2) - (IMGWAITINGKEYWIDTH/2);
			int y = (this.getHeight()/4) * 3 - (IMGWAITINGKEYHEIGHT/2);
			g.drawImage(IMGWAITINGKEY, x, y, IMGWAITINGKEYWIDTH, IMGWAITINGKEYHEIGHT, this);
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PartieVersus)
		{
			PartieVersus p = (PartieVersus)o;
			if(p.getEtatPartie().equals(EtatPartie.COMPTEAREBOURS))
			{
				this.imageFin = null;
				this.isWaiting = false;
				this.grilleView.resetLocationPanelBlocs();
				this.compteARebours.start();
			}
			else if(p.getEtatPartie().equals(EtatPartie.WAITING))
			{
				this.isWaiting = true;
				this.repaint();
			}
			else if(p.getEtatPartie().equals(EtatPartie.INTERRUPTED))
			{
				this.isWaiting = false;
				this.compteARebours.stop();
			}
		}
	}

}
