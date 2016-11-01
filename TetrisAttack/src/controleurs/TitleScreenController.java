package controleurs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import principal.RessourcesAudio;
import vues.FenetrePrincipal;
import vues.TitleScreenView;

/**
 * Controleur de l'écran titre.
 * @author Sacha
 *
 */
public class TitleScreenController{
	
	/**
	 * Vue de l'écran titre.
	 */
	private TitleScreenView titleScreen;
	
	/**
	 * {@link LineListener} du son de selection.
	 */
	private LineListener lstSonInteraction;
	
	/**
	 * Booléen permettant de controler les actions de l'utilisateur quand le son de selection se joue.
	 */
	private boolean enTransition;
	
	/**
	 * Constructeur de {@link TitleScreenController}.
	 * @param titleScreen vue de l'écran titre que l'on controle.
	 */
	public TitleScreenController(TitleScreenView titleScreen) {
		this.titleScreen = titleScreen;
		this.enTransition = false;
		initListeners();
		RessourcesAudio.lancerMusiqueTitre();
	}
	
	/**
	 * Méthode qui initialise les listeners.
	 */
	private void initListeners()
	{
		titleScreen.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(!enTransition)
				{
					if(keyCode == KeyEvent.VK_ESCAPE)
					{
						System.exit(0);
					}
					else
					{
						RessourcesAudio.lancerSonSelection(lstSonInteraction);
						enTransition = true;
					}
				}
			}
		});
		
		titleScreen.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!enTransition)
				{
					RessourcesAudio.lancerSonSelection(lstSonInteraction);
					enTransition = true;
				}
			}
		});
		
		lstSonInteraction = new LineListener() {
			
			@Override
			public void update(LineEvent event) {
				if(event.getType() == LineEvent.Type.CLOSE)
				{
					RessourcesAudio.arreterMusiqueTitre();
					FenetrePrincipal.afficherMenuPrincipal();
				}
			}
		};
	}
}
