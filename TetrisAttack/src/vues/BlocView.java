package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import donnees.Bloc;
import donnees.EtatBloc;
import principal.Ressources;
import principal.RessourcesBlocs;

/**
 * Classe représentant l'objet graphique d'un bloc.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class BlocView extends JPanel implements Observer{

	/**
	 * Hauteur d'un bloc.
	 */
	public static int BLOCHEIGHT = RessourcesBlocs.BLOCVIDE.getHeight() * Ressources.multResolution;
	
	/**
	 * Largeur d'un bloc.
	 */
	public static int BLOCWIDTH = RessourcesBlocs.BLOCVIDE.getWidth() * Ressources.multResolution;
	
	/**
	 * Dimension d'un bloc.
	 */
	public static Dimension PREFDIM = new Dimension(BLOCWIDTH, BLOCHEIGHT);
	
	/**
	 * Modele du bloc que cette vue représente.
	 */
	private Bloc bloc;
	
	/**
	 * Bordure normal du bloc, c'est à dire sans le curseur.
	 */
	private final static Border NORMALBORDER = BorderFactory.createEmptyBorder();
	
	/**
	 * Timer permettant de faire l'animation de combinaison.
	 */
	private Timer combinaisonAnimationTimer;
	
	/**
	 * Timer permettant de faire l'animation de warning.
	 */
	private Timer warningAnimationTimer;
	
	/**
	 * Timer permettant de faire l'animation de chute.
	 */
	private Timer landingAnimationTimer;
	
	/**
	 * Image du bloc qu'affiche cette vue.
	 */
	private BufferedImage image;
	
	/**
	 * Indice indiquant à quelle image de l'animation de combinaison on est.
	 */
	private int combiAnimInd;
	
	/**
	 * Indice indiquant à quelle image de l'animation de warning on est.
	 */
	private int warningAnimInd;
	
	/**
	 * Indice indiquant à quelle image de l'animation de chute on est.
	 */
	private int landingAnimInd;
	
	/**
	 * Constructeur de {@link BlocView}. Instancie un {@link BlocView} avec les indices d'animation à 0. 
	 * Ajoute cette vue comme observateurs au modele de bloc.
	 * Initialise les composants graphiques et les timers.
	 * @param bloc Modele que cette vue représente.
	 */
	public BlocView(Bloc bloc) {
		this.bloc = bloc;
		this.initComposant();
		this.setOpaque(false);
		this.bloc.addObserver(this);
		this.combiAnimInd = 0;
		this.warningAnimInd = 0;
		this.landingAnimInd = 0;
		initTimers();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposant()
	{
		this.setPreferredSize(PREFDIM);
		setImageBloc();
	}
	
	/**
	 * Méthode qui initialise les timers d'animations.
	 */
	private void initTimers()
	{
		this.combinaisonAnimationTimer = new Timer(140,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage[] imgs = bloc.getCouleur().getCombiAnimationImages();
				if(combiAnimInd == imgs.length)
				{
					combiAnimInd = 0;
					combinaisonAnimationTimer.stop();
				}
				else
				{
					image = imgs[combiAnimInd++];
					repaint();
				}	
			}
		});
		this.combinaisonAnimationTimer.setInitialDelay(0);
		
		this.warningAnimationTimer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage[] imgs = bloc.getCouleur().getWarningAnimationImages();
				image = imgs[warningAnimInd];
				warningAnimInd = (warningAnimInd + 1) % imgs.length;
				repaint();
			}
		});
		this.warningAnimationTimer.setInitialDelay(0);
		
		this.landingAnimationTimer = new Timer(26, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage[] imgs = bloc.getCouleur().getLandingAnimationImages();;
				if(landingAnimInd == imgs.length)
				{
					landingAnimInd = 0;
					landingAnimationTimer.stop();
				}
				else
				{
					image = imgs[landingAnimInd++];
					repaint();
				}
			}
		});
	}
	
	/**
	 * Méthode qui met à jour cette vue en fonction de l'état et de la couleur du bloc.
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		Bloc b = (Bloc)o;
		
		if(arg instanceof EtatBloc)
		{
			EtatBloc last = (EtatBloc)arg;
			if((last.equals(EtatBloc.NORMAL) || last.equals(EtatBloc.WARNING_ANIMATION)) && b.getEtat().equals(EtatBloc.FALLING))
			{
				this.warningAnimationTimer.stop();
			}
			else if(last.equals(EtatBloc.FALLING) && b.getEtat().equals(EtatBloc.NORMAL))
			{
				if(b.getCouleur().isNormalColor())
				{
					this.landingAnimInd = 0;
					this.landingAnimationTimer.restart();
				}
			}
			else if(last.equals(EtatBloc.WARNING_ANIMATION) && b.getEtat().equals(EtatBloc.NORMAL))
			{
				this.warningAnimationTimer.stop();
				this.setImageBloc();
				this.repaint();
			}
			else if(last.equals(EtatBloc.COMBINAISON_ANIMATION) && b.getEtat().equals(EtatBloc.NORMAL))
			{
				this.combinaisonAnimationTimer.stop();
			}
			else if(last.equals(EtatBloc.NORMAL) && b.getEtat().equals(EtatBloc.WARNING_ANIMATION))
			{
				this.warningAnimInd = 0;
				this.warningAnimationTimer.restart();
			}
			else if(last.equals(EtatBloc.NORMAL) && b.getEtat().equals(EtatBloc.COMBINAISON_ANIMATION))
			{
				this.landingAnimationTimer.stop();
				this.combiAnimInd = 0;
				this.combinaisonAnimationTimer.restart();
			}
			else if(last.equals(EtatBloc.WARNING_ANIMATION) && b.getEtat().equals(EtatBloc.COMBINAISON_ANIMATION))
			{
				this.warningAnimationTimer.stop();
				this.combiAnimInd = 0;
				this.combinaisonAnimationTimer.restart();
			}
			else if(last.equals(EtatBloc.WARNING_ANIMATION) && b.getEtat().equals(EtatBloc.WARNING_ANIMATION))
			{
				this.warningAnimInd = 0;
				this.warningAnimationTimer.restart();
			}
			else if(b.getEtat().equals(EtatBloc.FIN))
			{
				this.warningAnimationTimer.stop();
				this.combinaisonAnimationTimer.stop();
				this.landingAnimationTimer.stop();
				
				if(b.getY() != 0)
				{
					this.image = b.getCouleur().getImageFin();
				}
				this.repaint();
			}
		}
		else if(arg instanceof String)
		{
			String request = (String)arg;
			if(request.equals("reset"))
			{
				this.warningAnimationTimer.stop();
				this.combinaisonAnimationTimer.stop();
				this.landingAnimationTimer.stop();
				initTimers();
				this.image = b.getCouleur().getImage();
			}
			else if(request.equals("Color change"))
			{
				if(!b.getCouleur().isNormalColor())
				{
					this.warningAnimationTimer.stop();
					this.combinaisonAnimationTimer.stop();
				}
				this.landingAnimationTimer.stop();
				setImageBloc();
				this.getParent().repaint();
			}
		}
	}
	
	/**
	 * Méthode qui permet de récupérer l'image du bloc en état normal.
	 */
	private void setImageBloc()
	{
		if(bloc.getY() == 0)
		{
			this.image = bloc.getCouleur().getBottomLineImage();
		}
		else
		{
			this.image = bloc.getCouleur().getImage();
		}
	}
	
	/**
	 * Méthode qui permet de peindre le curseur sur le bloc.
	 * @param cursorBorder la bordure du curseur à peindre sur le bloc.
	 */
	public void paintCursorBorder(Border cursorBorder)
	{
		if(cursorBorder != null)
			this.setBorder(cursorBorder);
		else
			this.setBorder(NORMALBORDER);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
