package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import principal.Joueur;

/**
 * Classe repr�sentant un icone de joueur durant un partie de Cluedo. Un tooltip est affich�e lors du passage de la souris sur l'icone.
 * Impl�mente un MouseListener permettant de g�rer l'affichage du tootltip.
 * @author Sacha
 *
 */
public class PlayerIcon extends JLabel implements MouseListener{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilis�.
	 */
	private static final long serialVersionUID = -7340494978447112993L;
	
	/**
	 * Joueur repr�sent� par l'icone.
	 */
	private Joueur joueur;
	
	/**
	 * Fenetre repr�sentant le tooltip personnalis�e. Affiche les cartes connues du joueur.
	 */
	private JFrame tooltipPerso;

	/**
	 * Constructeur de la classe PlayerIcon. Charge les composants du labels et du tooltip. Lance l'�coute de l'icone.
	 * @param j Joueur correspondant au PlayerIcon
	 */
	public PlayerIcon(Joueur j)
	{
		super();
		joueur = j;
		this.load();
		this.addMouseListener(this);
	}
	
	/**
	 * M�thode permettant de charger les diff�rents composants graphique.
	 */
	private void load()
	{
		this.setIcon(new ImageIcon("Images/userIcon.png"));
		this.setName(joueur.getNom());
		this.setText(joueur.getNom() + " ");
		tooltipPerso = new JFrame(joueur.getNom());
		tooltipPerso.setResizable(false);
		tooltipPerso.setUndecorated(true);
		JPanel panelPrincipal = new JPanel();
		Color myColor = new Color(91, 125, 206, 50);
		panelPrincipal.setBorder(BorderFactory.createLineBorder(myColor,5,true));
		tooltipPerso.setContentPane(panelPrincipal);
		tooltipPerso.getContentPane().add(new PanelCartes(joueur.getCartesJoueur(), false));
		tooltipPerso.pack();
	}
	
	/**
	 * M�thode permettant de mettre � jouer le tooltip de l'icone.
	 */
	public void updateCardsForTooltip()
	{
		tooltipPerso.getContentPane().removeAll();
		tooltipPerso.getContentPane().add(new PanelCartes(joueur.getCartesJoueur(), false));
		tooltipPerso.pack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(tooltipPerso.isVisible())
		{
			tooltipPerso.setVisible(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JFrame tmpMainFrame = (JFrame)this.getTopLevelAncestor();
		if(tmpMainFrame.isFocused())
		{
			if(!tooltipPerso.isVisible())
			{
				Point p = this.getLocationOnScreen();
				p.x = p.x + this.getWidth();
				tooltipPerso.setLocation(p);
				tooltipPerso.setVisible(true);
			}
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		tooltipPerso.setVisible(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//do nothing
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//do nothing
		
	}

}
