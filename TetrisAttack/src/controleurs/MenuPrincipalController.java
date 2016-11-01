package controleurs;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import principal.RessourcesAudio;
import vues.FenetrePrincipal;
import vues.MenuButton;
import vues.MenuPrincipal;

/**
 * Controleur du menu principal.
 * @author Sacha
 *
 */
public class MenuPrincipalController{

	/**
	 * Indice représentant le bouton actuellement selectionnée dans la tableau buttonsOrder.
	 */
	private int currentIndex;

	/**
	 * Tableau des boutons présents dans le menu dans l'ordre, de haut en bas.
	 */
	private MenuButton buttonsOrder[];
	
	/**
	 * Vue du menu principal.
	 */
	private MenuPrincipal menuPrincipalView;
	
	/**
	 * Booléén permettant d'empecher les actions de l'utilisateur le temps que le son de selection se joue. 
	 */
	private boolean enAttente;
	
	/**
	 * Constructeur de {@link MenuPrincipalController}. Lance la musique du menu. Instancie un nouveau {@link MenuPrincipalController} 
	 * avec l'initialisation de ses variables par défaut.
	 * @param menuPrincipalView vue du menu principal que l'on controle.
	 */
	public MenuPrincipalController(MenuPrincipal menuPrincipalView) {
		this.enAttente = false;
		this.menuPrincipalView = menuPrincipalView;
		currentIndex = 0;
		this.buttonsOrder = menuPrincipalView.getButtonsOrder();
		menuPrincipalView.paintSelectIconForButton(buttonsOrder[currentIndex],true);
		initListeners();
		RessourcesAudio.lancerMusiqueMenu();
	}
	
	/**
	 * Méthode qui permet d'initialiser les différents listeners du menu.
	 */
	private void initListeners()
	{
		this.menuPrincipalView.getBtnQuitter().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RessourcesAudio.lancerSonSelection();
				System.exit(0);
			}
		});
		
		this.menuPrincipalView.getBtnAide().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RessourcesAudio.lancerSonSelection();
				buttonsOrder[currentIndex].paintSelectIcon(false);
				FenetrePrincipal.afficherMenuAide();
			}
		});
		
		this.menuPrincipalView.getBtnOptions().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RessourcesAudio.lancerSonSelection();
				buttonsOrder[currentIndex].paintSelectIcon(false);
				FenetrePrincipal.afficherMenuOptions();
			}
		});
		
		this.menuPrincipalView.getBtnCredits().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RessourcesAudio.lancerSonSelection();
				Desktop d = Desktop.getDesktop();
				try {
					d.open(new File("Credit.pdf"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		this.menuPrincipalView.getBtnVS().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(enAttente)
					return;
				
				RessourcesAudio.arreterMusiqueMenu();
				RessourcesAudio.lancerSonSelection(new LineListener() {
					
					@Override
					public void update(LineEvent event) {
						if(event.getType().equals(LineEvent.Type.CLOSE))
						{
							buttonsOrder[currentIndex].paintSelectIcon(false);
							FenetrePrincipal.afficherPartieVersus();
							enAttente = false;
						}
					}
				});
				enAttente = true;
			}
		});
		
		this.menuPrincipalView.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {	
			}
			
			@Override
			public void keyReleased(KeyEvent e) {	
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_UP)
				{
					buttonsOrder[currentIndex].paintSelectIcon(false);
					currentIndex = (currentIndex - 1 >= 0)?(currentIndex - 1):(buttonsOrder.length - 1);
					buttonsOrder[currentIndex].paintSelectIcon(true);
					RessourcesAudio.lancerSonDeplacement();
				}
				else if(keyCode == KeyEvent.VK_DOWN)
				{
					buttonsOrder[currentIndex].paintSelectIcon(false);
					currentIndex = (currentIndex + 1) % buttonsOrder.length;
					buttonsOrder[currentIndex].paintSelectIcon(true);
					RessourcesAudio.lancerSonDeplacement();
				}
				else if(keyCode == KeyEvent.VK_ENTER)
				{
					buttonsOrder[currentIndex].doClick();
				}
				else if(keyCode == KeyEvent.VK_ESCAPE)
				{
					RessourcesAudio.arreterMusiqueMenu();
					FenetrePrincipal.afficherTitleScreen();
				}
			}
		});
	}
}
