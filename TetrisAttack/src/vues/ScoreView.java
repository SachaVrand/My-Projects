package vues;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import donnees.Score;
import principal.NumberFont;
import principal.Ressources;

/**
 * Classe représentant la vue d'un score.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class ScoreView extends JPanel implements Observer{
	
	/**
	 * pas horizontale entre les composants.
	 */
	private static int HGAP = 0 * Ressources.multResolution;
	
	/**
	 * Pas verticale entre le composants.
	 */
	private static int VGAP = 10 * Ressources.multResolution;
	
	/**
	 * Hauteur du fond de cette vue.
	 */
	private static int BGHEIGHT = Ressources.BGSCOREJ1.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur du fond de cette vue.
	 */
	private static int BGWIDTH = Ressources.BGSCOREJ1.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension de cette vue.
	 */
	public static Dimension PREFDIM = new Dimension(BGWIDTH, BGHEIGHT);
	
	/**
	 * Modele du score que cette vue doit représenter.
	 */
	private Score score;
	
	/**
	 * Tableau de {@link NumberPanel} représentant le nombre du score.
	 */
	private NumberPanel[] tabScore;
	
	/**
	 * Image de fond de cette vue.
	 */
	private Image background;
	
	/**
	 * Numéro du joueur possèdent cette vue du score.
	 */
	private int noJoueur;
	
	/**
	 * Constructeur de {@link ScoreView}. Instancie une nouvelle vue du score en fonction du joueur.
	 * @param score Modele que cette vue doit représenter.
	 * @param noJoueur Numéro du joueur possèdant cette vue.
	 */
	public ScoreView(Score score, int noJoueur) {
		super(new FlowLayout(FlowLayout.CENTER,HGAP,VGAP));
		this.score = score;
		this.score.addObserver(this);
		this.noJoueur = noJoueur;
		this.background = noJoueur == 1 ? Ressources.BGSCOREJ1.getImage() : Ressources.BGSCOREJ2.getImage();
		this.initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		String scoreStr = score.toString();
		tabScore = new NumberPanel[4];
		for(int i = 0; i < tabScore.length; i++)
		{
			NumberPanel chiffreScore = new NumberPanel(getNumberImage(scoreStr.charAt(i)));
			tabScore[i] = chiffreScore;
			this.add(chiffreScore);
		}

		this.setPreferredSize(PREFDIM);
	}

	@Override
	public void update(Observable o, Object arg) {
		for(int i = 0; i < tabScore.length; i++)
		{
			tabScore[i].setImage(getNumberImage(score.toString().charAt(i)));
		}
		this.repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, BGWIDTH, BGHEIGHT, this);
	}
	
	/**
	 * Méthode qui permet de récupérer l'image associée au chiffre, sous forme de caractère.
	 * @param c chiffre dont ou souhaite l'image, sous forme de caractère.
	 * @return L'image associée au chiffre.
	 */
	private Image getNumberImage(char c)
	{
		if(noJoueur == 1)
		{
			return NumberFont.getNumberImageBlue(c);
		}
		else
		{
			return NumberFont.getNumberImageRed(c);
		}
	}

}
