package gui;

import java.nio.file.Paths;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class.
 * @author Lykke Levin
 * @version 1.0
 *
 */
public class Sound {
	private AudioClip audio;
//	private static Media m = new Media(Paths.get("resources/sounds/cool_struttin'.mp3").toUri().toString());
	private static Media m = new Media(Sound.class.getResource("/sounds/cool_struttin'.mp3").toString());
	public static MediaPlayer mp = new MediaPlayer(m);
	public GameController gc;


	public void shuffleSound(){
		audio = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString());
		audio.play();

	}
	
	public void singleCard(){
		audio = new AudioClip(Sound.class.getResource("/sounds/cardPlace8.wav").toString());
		audio.play();
	}

	public void playBackgroundMusic(){
		mp.play();	

	}
	
	public void chipStack(){
		audio = new AudioClip(Sound.class.getResource("/sounds/chipsStacksSingle.wav").toString());
		audio.play();
	}
	//TODO Nya ljud, aktivering vid rätt plats. Volymkontroll för bakgrundmusik.

}
