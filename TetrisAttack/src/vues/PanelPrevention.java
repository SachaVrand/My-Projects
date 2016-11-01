package vues;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import donnees.EtatPartie;
import donnees.PartieVersus;
import principal.Ressources;

/**
 * Classe représentant le panel de prévention d'un joueur.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class PanelPrevention extends JPanel implements Observer{
	
	/**
	 * pas horizontale entre les composants.
	 */
	private static int HGAP = 1 * Ressources.multResolution;
	
	/**
	 * pas verticale entre les composants.
	 */
	private static int VGAP = 2 * Ressources.multResolution;
	
	/**
	 * Hauteur de cette vue.
	 */
	public static int PREFHEIGHT = 23* Ressources.multResolution;
	
	/**
	 * Largeur de cette vue.
	 */
	public static int PREFWIDTH = GrilleView.BGWIDTH;
	
	/**
	 * Dimension de cette vue.
	 */
	public static Dimension PREFDIM = new Dimension(PREFWIDTH, PREFHEIGHT);
	
	/**
	 * BriquePrevention pour une brique taille 3.
	 */
	private BriquePrevention prevBrique3;
	
	/**
	 * BriquePrevention pour une brique taille 4.
	 */
	private BriquePrevention prevBrique4;
	
	/**
	 * BriquePrevention pour une brique taille 5.
	 */
	private BriquePrevention prevBrique5;
	
	/**
	 * BriquePrevention pour une brique taille 6 ou hauteur plus de 1.
	 */
	private BriquePrevention prevBrique6p;
	
	/**
	 * Compteur du nombre de brique de taille 3 qui ont étés demandées d'afficher.
	 */
	private int cptB3;
	
	/**
	 * Compteur du nombre de brique de taille 4 qui ont étés demandées d'afficher.
	 */
	private int cptB4;
	
	/**
	 * Compteur du nombre de brique de taille 5 qui ont étés demandées d'afficher.
	 */
	private int cptB5;
	
	
	/**
	 * Compteur du nombre de brique de taille 6 plus qui ont étés demandées d'afficher.
	 */
	private int cptB6;
	
	/**
	 * Constructeur de {@link PanelPrevention}. Instancie un {@link PanelPrevention} avec les compteurs à zéro 
	 * et les image de {@link BriquePrevention} instanciées.
	 */
	public PanelPrevention() {
		super(new FlowLayout(FlowLayout.TRAILING, HGAP, VGAP));
		cptB3 = 0;
		cptB4 = 0;
		cptB5 = 0;
		cptB6 = 0;
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		prevBrique3 = new BriquePrevention(BriquePrevention.BRIQUE3);
		prevBrique4 = new BriquePrevention(BriquePrevention.BRIQUE4);
		prevBrique5 = new BriquePrevention(BriquePrevention.BRIQUE5);
		prevBrique6p = new BriquePrevention(BriquePrevention.BRIQUE6P);
		this.setOpaque(false);
		this.setPreferredSize(PREFDIM);
	}
	
	/**
	 * Méthode qui permet pévenir d'une brique.
	 * @param brique Constante de {@link BriquePrevention} de la brique à prévenir.
	 */
	public synchronized void prevenirBrique(int brique)
	{
		if(brique == BriquePrevention.BRIQUE3)
		{
			if(cptB3 == 0)
			{
				this.add(prevBrique3);
			}
			cptB3++;	
		}
		else if(brique == BriquePrevention.BRIQUE4)
		{
			if(cptB4 == 0)
			{
				this.add(prevBrique4);
			}
			cptB4++;
		}
		else if(brique == BriquePrevention.BRIQUE5)
		{
			if(cptB5 == 0)
			{
				this.add(prevBrique5);
			}
			cptB5++;
		}
		else if(brique == BriquePrevention.BRIQUE6P)
		{
			if(cptB6 == 0)
			{
				this.add(prevBrique6p);
			}
			cptB6++;
		}
		this.validate();
		this.repaint();
	}
	
	/**
	 * Méthode qui permet de finir la prevention d'une brique. Si plusieurs brique de la meme taille ont été demandées. 
	 * Elle s'enlevera quand elle auront toutes demandée d'etre enelvé.
	 * @param brique Constante de {@link BriquePrevention} de la brique à désaffiché.
	 */
	public synchronized void finPreventionBrique(int brique)
	{
		if(brique == BriquePrevention.BRIQUE3)
		{
			cptB3--;
			if(cptB3 == 0)
			{
				this.remove(prevBrique3);
			}	
		}
		else if(brique == BriquePrevention.BRIQUE4)
		{
			cptB4--;
			if(cptB4 == 0)
			{
				this.remove(prevBrique4);
			}
		}
		else if(brique == BriquePrevention.BRIQUE5)
		{
			cptB5--;
			if(cptB5 == 0)
			{
				this.remove(prevBrique5);
			}
		}
		else if(brique == BriquePrevention.BRIQUE6P)
		{
			cptB6--;
			if(cptB6 == 0)
			{
				this.remove(prevBrique6p);
			}
		}
		this.validate();
		this.repaint();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof PartieVersus)
		{
			if(((PartieVersus)arg0).getEtatPartie().equals(EtatPartie.TRANSITIONMANCHE))
			{
				this.resetPanel();
			}
		}
	}
	
	/**
	 * Méthode qui permet de reset le panel de prévention.
	 */
	private void resetPanel()
	{
		this.remove(prevBrique3);
		this.remove(prevBrique4);
		this.remove(prevBrique5);
		this.remove(prevBrique6p);
		this.cptB3 = 0;
		this.cptB4 = 0;
		this.cptB5 = 0;
		this.cptB6 = 0;
	}
}
