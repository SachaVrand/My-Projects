package donnees;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import principal.Ressources;

/**
 * Classe permettant de lire des sons.
 * @author Sacha
 *
 */
public class AudioPlayer extends Thread {

	/**
	 * Ligne où vont être écrit les données du son.
	 */
	private SourceDataLine line;
	
	/**
	 * Fichier audio que va lire cette {@link AudioPlayer}
	 */
	private File fichierAudio;
	
	/**
	 * Flux audio depuis lequelle on va récupérer les données du son.
	 */
	private AudioInputStream audioInputStream;
	
	/**
	 * Format audio du fichier à lire.
	 */
    private AudioFormat audioFormat;
    
    /**
     * Booléen permettant d'arreter le son qui est lu. Vrai s'il doit être arreter, faux sinon.
     */
	private boolean stop;
	
	/**
	 * Booléen permettant de savoir si le son doit être lu en boucle. Vrai s'il doit l'être, faux sinon.
	 */
	private boolean enBoucle;
	
	/**
	 * Constructeur de la classe {@link AudioPlayer}. Instancie un {@link AudioPlayer} avec aucun lineListener.
	 * @param fichierAudio Fichier du son qui doit être lu.
	 * @param enBoucle Vrai si le son doit être lu en boucle, sinon faux.
	 */
	public AudioPlayer(File fichierAudio, boolean enBoucle) {
		this.fichierAudio = fichierAudio;
		this.stop = false;
		this.enBoucle = enBoucle;
		this.init();
	}
	
	/**
	 * Constructeur de la classe {@link AudioPlayer}. Instancie un {@link AudioPlayer} avec le {@link LineListener} passé en paramètre.
	 * @param fichierAudio Fichier du son qui doit être lu.
	 * @param enBoucle Vrai si le son doit être lu en boucle, sinon faux.
	 * @param listener {@link LineListener} qui permet d'écouter la ligne.
	 */
	public AudioPlayer(File fichierAudio, boolean enBoucle, LineListener listener)
	{
		this(fichierAudio,enBoucle);
		this.line.addLineListener(listener);
	}
	
	/**
	 * Constructeur de la classe {@link AudioPlayer}. 
	 * Instancie un {@link AudioPlayer} avec le {@link LineListener} passé en paramètre et enBoucle à false.
	 * @param fichierAudio Fichier du son qui doit être lu.
	 * @param listener {@link LineListener} qui permet d'écouter la ligne.
	 */
	public AudioPlayer(File fichierAudio, LineListener listener)
	{
		this(fichierAudio,false,listener);
	}
	
	/**
	 * Constructeur de la classe {@link AudioPlayer}.
	 * Instancie un {@link AudioPlayer} avec aucun {@link LineListener} et enBoucle à false.
	 * @param fichierAudio Fichier du son qui doit être lu.
	 */
	public AudioPlayer(File fichierAudio) {
		this(fichierAudio,false);
	}

	/**
	 * Méthode qui permet de stopper le son qui est en train d'être lu. Si le son n'est pas en train dêtre lu, cela n'as aucun effet.
	 */
	public void stopper() {
		this.stop = true;
	}

	/**
	 * Méthode qui permet d'initialiser la {@link SourceDataLine} qui permettra de lire le son.
	 */
    public void init(){
        
        try {
        	ouvrirFlux();
	        audioFormat = audioInputStream.getFormat();
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	        line = (SourceDataLine) AudioSystem.getLine(info);
	        fermerFlux();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
    } 
    
    /**
     * Méthode run qui permet de lire un son.
     */
    @Override
	public void run() {
        
        stop = false;

        try {
            line.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }

        FloatControl masterGainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
		masterGainControl.setValue((float)Ressources.volumeMusique);
        line.start();

        do
        {
        	ouvrirFlux();
			try {
				byte[] bytes = new byte[1024];
				int bytesRead = 0;	
				while (((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) && !stop) {
					line.write(bytes, 0, bytesRead);
				}
			} catch (IOException io) {
				io.printStackTrace();
				return;
			}
			fermerFlux();
        }
        while(enBoucle && !stop);
        
        if(!stop)
        {
        	line.drain();
        }
        
		line.flush();
		line.close();

	}
	
    /**
     * Méthode qui permet de changer le volume de cette {@link AudioPlayer}.
     * @param newVolume Nouveau volume, doit être compris entre -80 et 6.
     */
	public void changerVolume(int newVolume)
	{
		FloatControl masterGainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
		masterGainControl.setValue((float)newVolume);
	}
	
	/**
	 * Méthode qui permet d'ouvrir le {@link AudioInputStream} de cette {@link AudioPlayer} à l'aide du fichier audio.
	 */
	private void ouvrirFlux()
	{
		try {
			audioInputStream = AudioSystem.getAudioInputStream(fichierAudio);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui permet de fermer le {@link AudioInputStream} de cette {@link AudioPlayer}.
	 */
	private void fermerFlux()
	{
		try {
			audioInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
