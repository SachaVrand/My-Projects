package vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import donnees.Changement;
import principal.Ressources;

/**
 * Classe représentant une sous option dans le menu des options.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public abstract class PanelSousOption extends JPanel{
	
	/**
	 * Hauteur de cette vue.
	 */
	public static final int PREFHEIGHT = 30;
	
	/**
	 * Largeur de cette vue.
	 */
	public static final int PREFWIDTH = 450;
	
	/**
	 * Dimension de cette vue.
	 */
	public static final Dimension PREFDIM = new Dimension(PREFWIDTH,PREFHEIGHT);
	
	/**
	 * Image du curseur de selection sur la sous option.
	 */
	private static final Image SELECTICON = Ressources.SELECTICON.getImage();
	
	/**
	 * Label décrivant le nom de la soous option.
	 */
	protected JLabel lblOption;
	
	/**
	 * Label décrivant la valeur de la sous option.
	 */
	protected JLabel lblTxtValue;
	
	/**
	 * Label de séparation entre le label du nom de l'option et celui de la valeur.
	 */
	protected JLabel lblSeparateur;
	
	/**
	 * Timer permettant l'animation du clignotement du curseur de selection.
	 */
	private Timer clignotementCurseur;
	
	/**
	 * Timer permettant l'animation du clignotement de la valeur.
	 */
	private Timer clignotementValeur;
	
	/**
	 * Boolean indiquant si le curseur sur cette option est affichée ou non. Vrai si oui, faux sinon.
	 */
	protected boolean curseurPresent;
	
	/**
	 * Fonction de changement permettant de mettre à jour les valeurs et autres associée à cette sous option. Par exemple le raccourcis du joueur 1,
	 * il faut set dans le setting du joueur 1 la touche associée.
	 */
	private Changement changement;
	
	/**
	 * Constructeur de {@link PanelSousOption}.
	 * @param label Titre de la sous option.
	 * @param c Fonction de changement associée quand la valeur de la sous option change.
	 */
	public PanelSousOption(String label, Changement c) {
		super(new SpringLayout());
		this.curseurPresent = false;
		this.changement = c;
		initTimer();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant.
	 * @param label Label de la sous option.
	 */
	protected void initComposants(String label)
	{
		this.setPreferredSize(PREFDIM);
		
		Font font = new Font(Ressources.FONTNAMEMENU, Font.PLAIN, 25);
		this.lblOption = new JLabel(label);
		this.lblOption.setFont(font);
		this.lblOption.setHorizontalTextPosition(SwingConstants.LEFT);
		this.lblOption.setVerticalTextPosition(SwingConstants.CENTER);
		this.lblOption.setPreferredSize(new Dimension(200, 30));
		
		this.lblTxtValue = new JLabel(getTextValue());
		this.lblTxtValue.setFont(font);
		this.lblTxtValue.setHorizontalTextPosition(SwingConstants.RIGHT);
		this.lblTxtValue.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblTxtValue.setVerticalTextPosition(SwingConstants.CENTER);
		this.lblTxtValue.setPreferredSize(new Dimension(170, 30));
		
		this.lblSeparateur = new JLabel(":");
		this.lblSeparateur.setFont(font);
		this.lblSeparateur.setHorizontalTextPosition(SwingConstants.RIGHT);
		this.lblSeparateur.setVerticalTextPosition(SwingConstants.CENTER);
		this.lblSeparateur.setPreferredSize(new Dimension(20, 30));
		
		this.add(lblOption,BorderLayout.WEST);
		this.add(lblSeparateur, BorderLayout.CENTER);
		this.add(lblTxtValue, BorderLayout.EAST);
		
		SpringLayout layout = (SpringLayout)this.getLayout();
		layout.putConstraint(SpringLayout.WEST, lblOption, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, lblSeparateur, 0, SpringLayout.EAST, lblOption);
		layout.putConstraint(SpringLayout.WEST, lblTxtValue, 0, SpringLayout.EAST, lblSeparateur);
		this.setBackground(Ressources.BLUEINNER2);
	}
	
	/**
	 * Méthode qui permet d'initialiser les timers d'animation.
	 */
	private void initTimer()
	{
		this.clignotementCurseur = new Timer(250, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				curseurPresent = !curseurPresent;
				repaint();
			}
		});
		
		this.clignotementValeur = new Timer(250, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lblTxtValue.setText(lblTxtValue.getText() == "" ? getTextValue() : "");
				repaint();		
			}
		});
	}
	
	/**
	 * Méthode qui permettre de récuperer sous forme de texte la valeur associée à la sous option
	 * @return La valeur associée à la sous option sous forme de texte.
	 */
	public abstract String getTextValue();
	
	/**
	 * Méthode qui doit permettre de set la valeur de la sous option.
	 * @param o la nouvelle valeur de la sous option.
	 */
	public abstract void setValue(Object o);
	
	/**
	 * Méthode qui permet d'appliquer les changement à l'aide de la fonction de changement.
	 * @param nouvelleConfig Entier pour mettre à jour la nouvele configuration.
	 */
	public void changerConfig(int nouvelleConfig)
	{
		changement.changer(nouvelleConfig);
	}
	
	/**
	 * Méthode qui permet set le curseur sur la sous option ou de l'enlever. Le fait clignoter si il est présent.
	 * @param curseurPresent Vrai si le curseur est présent, faux sinon.
	 */
	public void setCurseurPresent(boolean curseurPresent) {
		this.curseurPresent = curseurPresent;
		if(curseurPresent)
		{
			this.clignotementCurseur.start();
		}
		else
		{
			this.clignotementCurseur.stop();
		}
		repaint();
	}
	
	/**
	 * Méthode qui permet de faire clignoter la valeur.
	 */
	public void clignoterValue()
	{
		this.clignotementValeur.start();
	}
	
	/**
	 * Méthode qui permet de stopper le clignotement de la valeur.
	 */
	public void stopperClignotement()
	{
		this.clignotementValeur.stop();
		this.lblTxtValue.setText(getTextValue());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Ressources.BGBUTTONOPT.getImage(), 0, 0, null);
		
		if(curseurPresent)
		{
			int y = this.getHeight()/2 - SELECTICON.getHeight(null)/2;
			g.drawImage(SELECTICON, 15, y, null);
		}
	}

}
