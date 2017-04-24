package gui;

import javafx.scene.media.AudioClip;

public class Sound {
	AudioClip audio;

	public void testPlaySound(){
		audio = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString());
		audio.play();

	}
}
