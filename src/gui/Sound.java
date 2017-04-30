package gui;

import java.nio.file.Paths;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class Sound {
	private AudioClip audio;
	private static Media m = new Media(Paths.get("resources/sounds/cool_struttin'.mp3").toUri().toString());
	public static MediaPlayer mp = new MediaPlayer(m);
	public GameController gc;


	public void testPlaySound(){
		audio = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString());
		audio.play();

	}

	public void playBackgroundMusic(){
		mp.play();	

	}

}
