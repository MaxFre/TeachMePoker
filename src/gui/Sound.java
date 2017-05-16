package gui;

import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */
public class Sound {
	private static Media m = new Media(Sound.class.getResource("/sounds/cool_struttin'.mp3").toString());
	public static MediaPlayer mp = new MediaPlayer(m);
	public AudioClip audio;
//	, shuffleSound, singleCard, cardFold, chipSingle, chipMulti, coinSound, wrongSound;
	public AudioClip checkSound = new AudioClip(Sound.class.getResource("/sounds/checkMeSound.m4a").toString());
	public GameController gc;
//	ArrayList<AudioClip> listClip = new ArrayList<AudioClip>();

//	public void playAClip() {
//	
//
//			listClip.add(shuffleSound = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString()));
//		listClip.add(singleCard = new AudioClip(Sound.class.getResource("/sounds/cardSlide8.wav").toString()));
//			listClip.add(cardFold = new AudioClip(Sound.class.getResource("/sounds/cardShove3.wav").toString()));
//			listClip.add(
//					chipSingle = new AudioClip(Sound.class.getResource("/sounds/chipsStacksSingle.wav").toString()));
//			listClip.add(chipMulti = new AudioClip(Sound.class.getResource("/sounds/ChipMe.m4a").toString()));
//			listClip.add(coinSound = new AudioClip(Sound.class.getResource("/sounds/ChingChingChip.m4a").toString()));
//			listClip.add(
//					wrongSound = new AudioClip(Sound.class.getResource("/sounds/Button-sound-wrong.mp3").toString()));
//		listClip.add(checkSound);
//		
//		System.out.println("LYKKE GOT DAMN IT LOOOOK " + listClip.size());
//		System.out.println("LYKKE GOT DAMN IT LOOOOK " + listClip.indexOf(coinSound));
//		listClip.get(8).play();
//
//	}

	public void shuffleSound() {

		audio = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString());
		audio.play();

	}

	public void singleCard() {

		audio = new AudioClip(Sound.class.getResource("/sounds/cardSlide8.wav").toString());
		audio.play();

	}

	public void playBackgroundMusic() {
		mp.play();

	}

	public void cardFold() {

		audio = new AudioClip(Sound.class.getResource("/sounds/cardShove3.wav").toString());
		audio.play();

	}

	public void chipSingle() {

		audio = new AudioClip(Sound.class.getResource("/sounds/chipsStacksSingle.wav").toString());
		audio.play();

	}

	public void chipMulti() {

		audio = new AudioClip(Sound.class.getResource("/sounds/ChipMe.m4a").toString());
		audio.play();

	}

	public void coinSound() {

		audio = new AudioClip(Sound.class.getResource("/sounds/ChingChingChip.m4a").toString());
		audio.play();

	}

	// public void checkSound() {
	// if(audio.getVolume()==1){
	// audio = new
	// AudioClip(Sound.class.getResource("/sounds/checkMeSound.m4a").toString());
	// audio.play();
	// }
	// }

	public void wrongSound() {

		audio = new AudioClip(Sound.class.getResource("/sounds/Button-sound-wrong.mp3").toString());
		audio.play();

	}

	// TODO Nya ljud, aktivering vid rätt plats. Volymkontroll för
	// bakgrundmusik.
//	public static void main(String[] args) {
//		Sound sounder = new Sound();
//			sounder.playAClip();
//		
//	}
}
