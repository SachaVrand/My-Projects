package controleurs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import principal.RessourcesAudio;
import vues.FenetrePrincipal;
import vues.MenuAide;

/**
 * Controleur du menu d'aide.
 * @author Sacha
 *
 */
public class MenuAideController {
	
	/**
	 * Vue de l'aide.
	 */
	private MenuAide aideView;
	
	/**
	 * Constructeur de {@link MenuAideController}.
	 * intialise les listeners sur la vue.
	 * @param aideView vue du menu aide.
	 */
	public MenuAideController(MenuAide aideView) {
		this.aideView = aideView;
		this.initListeners();
	}
	
	/**
	 * Méthode qui initialise les {@link KeyListener} et {@link MouseListener} de la vue.
	 */
	private void initListeners()
	{
		this.aideView.getFlecheGauche().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				RessourcesAudio.lancerSonDeplacement();
				aideView.afficherImagePrec();
			}
		});
		
		this.aideView.getFlecheDroite().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				RessourcesAudio.lancerSonDeplacement();
				aideView.afficherImageSuiv();
			}
		});
		
		this.aideView.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_LEFT)
				{
					RessourcesAudio.lancerSonDeplacement();
					aideView.afficherImagePrec();
				}
				else if(keyCode == KeyEvent.VK_RIGHT)
				{
					RessourcesAudio.lancerSonDeplacement();
					aideView.afficherImageSuiv();
				}
				else if(keyCode == KeyEvent.VK_ESCAPE)
				{
					RessourcesAudio.lancerSonRetour();
					FenetrePrincipal.afficherMenuPrincipal();
				}
			}
		});
	}

}
