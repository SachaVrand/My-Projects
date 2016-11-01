package principal;

import java.io.File;

import javax.sound.sampled.LineListener;

import donnees.AudioPlayer;

/**
 * Classe qui regroupent les ressources audio du jeu.
 * Permet aussi de jouer des sons et musique.
 * @author Sacha
 *
 */
public class RessourcesAudio {
	
	public static final File AUDIOTITRE = new File("Audio/audioAccueil.wav");
	public static final File AUDIOSELECT = new File("Audio/audioSelectAccueil.wav");
	public static final File AUDIOMENU = new File("Audio/audioMenu.wav");
	public static final File AUDIODEPLACEMENT = new File("Audio/audioDeplacement.wav");
	public static final File AUDIORETOUR = new File("Audio/audioRetourMenu.wav");
	public static final File AUDIOBREAKBLOCKS = new File("Audio/audioBreakBlocks.wav");
	public static final File AUDIOSWITCHBLOCKS = new File("Audio/audioSwitchBlocks.wav");
	public static final File AUDIOFALLBLOCKS = new File("Audio/audioFallBlocks.wav");
	public static final File AUDIORESULTATS = new File("Audio/audioResultas.wav");
	public static final File AUDIOFOREST = new File("Audio/audioForest.wav");
	public static final File AUDIOCHUTEBRIQUE = new File("Audio/audioChuteBrique.wav");
	public static final File AUDIOCAR1 = new File("Audio/audioCAR1.wav");
	public static final File AUDIOCAR2 = new File("Audio/audioCAR2.wav");
	public static final File AUDIOLOSE = new File("Audio/audioLose.wav");
	public static final File AUDIOMUR = new File("Audio/audioMurTransition.wav");
	
	private static AudioPlayer musiqueMenu = null;
	private static AudioPlayer musiqueTitre = null;
	private static AudioPlayer musiqueResultats = null;
	private static AudioPlayer musiqueJeu = null;
	
	/**
	 * Méthode qui joue le son de déplacement.
	 */
	public static void lancerSonDeplacement()
	{
		new AudioPlayer(AUDIODEPLACEMENT).start();
	}
	
	/**
	 * Méthode qui joue le son de selection.
	 */
	public static void lancerSonSelection()
	{
		new AudioPlayer(AUDIOSELECT).start();
	}
	
	/**
	 * Méthode qui joue le son de retour.
	 */
	public static void lancerSonRetour()
	{
		new AudioPlayer(AUDIORETOUR).start();
	}
	
	/**
	 * Méthode qui joue le son de switch.
	 */
	public static void lancerSonSwitch()
	{
		new AudioPlayer(AUDIOSWITCHBLOCKS).start();
	}
	
	/**
	 * Méthode qui joue le son de cassage de blocs.
	 */
	public static void lancerSonBreakBlocks()
	{
		new AudioPlayer(AUDIOBREAKBLOCKS).start();
	}
	
	/**
	 * Méthode qui joue le son de chute de blocs.
	 */
	public static void lancerSonFallBlocks()
	{
		new AudioPlayer(AUDIOFALLBLOCKS).start();
	}
	
	/**
	 * Méthode qui joue le son de chute de briques.
	 */
	public static void lancerSonChuteBrique()
	{
		new AudioPlayer(AUDIOCHUTEBRIQUE).start();
	}
	
	/**
	 * Méthode qui joue le son du compte a rebours pour le 3 ou 2 ou 1.
	 */
	public static void lancerSonCAR321()
	{
		new AudioPlayer(AUDIOCAR1).start();
	}
	
	/**
	 * Méthode qui joue le son pour le début de la partie.
	 */
	public static void lancerSonCARGo()
	{
		new AudioPlayer(AUDIOCAR2).start();
	}
	
	/**
	 * Méthode qui joue le son de défaite.
	 */
	public static void lancerSonLose()
	{
		new AudioPlayer(AUDIOLOSE).start();
	}
	
	/**
	 * Méthode qui joue le son du mur qui touche le haut.
	 */
	public static void lancerSonMur()
	{
		new AudioPlayer(AUDIOMUR).start();
	}
	
	/**
	 * Méthode qui lance la musique en boucle du menu.
	 */
	public static void lancerMusiqueMenu()
	{
		if(musiqueMenu == null || !musiqueMenu.isAlive())
		{
			musiqueMenu = new AudioPlayer(AUDIOMENU, true);
			musiqueMenu.start();
		}
	}
	
	/**
	 * Méthode qui lance la musique en boucle de l'écran titre.
	 */
	public static void lancerMusiqueTitre()
	{
		if(musiqueTitre == null || !musiqueTitre.isAlive())
		{
			musiqueTitre = new AudioPlayer(AUDIOTITRE, true);
			musiqueTitre.start();
		}
	}
	
	/**
	 * Méthode qui lance la musique en boucle des résultat de fin de manche.
	 */
	public static void lancerMusiqueResultat()
	{
		if(musiqueResultats == null || !musiqueResultats.isAlive())
		{
			musiqueResultats = new AudioPlayer(AUDIORESULTATS, true);
			musiqueResultats.start();
		}
	}
	
	/**
	 * Méthode qui lance la musique en boucle durant la partie.
	 */
	public static void lancerMusiqueJeu()
	{
		if(musiqueJeu == null || !musiqueJeu.isAlive())
		{
			musiqueJeu = new AudioPlayer(AUDIOFOREST, true);
			musiqueJeu.start();
		}
	}
	
	/**
	 * Méthode qui lance le son de selectin avec comme {@link LineListener} celui passée en paramètre.
	 * @param listener {@link LineListener} sur le son de selection.
	 */
	public static void lancerSonSelection(LineListener listener)
	{
		new AudioPlayer(AUDIOSELECT, listener).start();
	}
	
	/**
	 * Méthode qui permet d'arreter la musique de l'écran titre.
	 */
	public static void arreterMusiqueTitre()
	{
		if(musiqueTitre == null || !musiqueTitre.isAlive())
			return;
		musiqueTitre.stopper();
	}
	
	/**
	 * Méthode qui permet d'arreter la musique du menu.
	 */
	public static void arreterMusiqueMenu()
	{
		if(musiqueMenu == null || !musiqueMenu.isAlive())
			return;
		musiqueMenu.stopper();
	}
	
	/**
	 * Méthode qui permet d'arreter la musique des résultats.
	 */
	public static void arreterMusiqueResultats()
	{
		if(musiqueResultats == null || !musiqueResultats.isAlive())
			return;
		musiqueResultats.stopper();
	}
	
	/**
	 * Méthode qui permet d'arreter la musique en jeu.
	 */
	public static void arreterMusiqueJeu()
	{
		if(musiqueJeu == null || !musiqueJeu.isAlive())
			return;
		musiqueJeu.stopper();
	}
	
	/**
	 * Méthode qui permet de changer le volume de la musique dans le menu.
	 */
	public static void setVolumeMusiqueMenu()
	{
		if(musiqueMenu.isAlive())
		{
			musiqueMenu.changerVolume(Ressources.volumeMusique);
		}
	}
}
