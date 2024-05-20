package com.java.gui.sound;

import com.java.config.GuiConfig;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


/**
 * Class that manages the sound of the game.
 */
public class SoundManager {
    /**
     * Clip that will play the sound.
     */
    protected Clip clip;
    /**
     * Array of files that contains the sound files.
     */
    protected final File[] soundFiles = new File[14];
    /**
     * Booleans that manage the mute of the sound effects. If true, the sound effects are muted.
     */
    private boolean soundEffectsMuted = false;
    /**
     * Booleans that manage the mute of the music. If true, the music is muted.
     */
    private boolean musicMuted = false;

    /**
     * Constructor of the Sound class.
     * It initializes the sound files.
     */
    public SoundManager() {
        soundFiles[0] = new File(GuiConfig.resPath + "sfx/bg.wav");
        soundFiles[1] = new File(GuiConfig.resPath + "sfx/move1.wav");
        soundFiles[2] = new File(GuiConfig.resPath + "sfx/move2.wav");
        soundFiles[3] = new File(GuiConfig.resPath + "sfx/move3.wav");
        soundFiles[4] = new File(GuiConfig.resPath + "sfx/move4.wav");
        soundFiles[5] = new File(GuiConfig.resPath + "sfx/move5.wav");
        soundFiles[6] = new File(GuiConfig.resPath + "sfx/collision1.wav");
        soundFiles[7] = new File(GuiConfig.resPath + "sfx/collision2.wav");
        soundFiles[8] = new File(GuiConfig.resPath + "sfx/collision3.wav");
        soundFiles[9] = new File(GuiConfig.resPath + "sfx/win1.wav");
        soundFiles[10] = new File(GuiConfig.resPath + "sfx/win2.wav");
        soundFiles[11] = new File(GuiConfig.resPath + "sfx/win3.wav");
        soundFiles[12] = new File(GuiConfig.resPath + "sfx/win4.wav");
        soundFiles[13] = new File(GuiConfig.resPath + "sfx/win5.wav");
    }

    /**
     * Method that sets the file that we want to play.
     * @param i index of the file we want to set
     */
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFiles[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Error in Sound.setFile : " + e.getMessage());
        }
    }

    /**
     * Method that plays the sound.
     */
    public void play() {
        if (clip != null) clip.start();
    }

    /**
     * Method that stops the sound.
     */
    public void stop() {
        if (clip != null) clip.stop();
    }

    /**
     * Method that loops the sound.
     */
    public void loop() {
        if (clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * This method plays the music we asked for.
     * @param i index of the file we want to play
     */
    public void playMusic(int i) {
        setFile(i);
        play();
        loop();
    }

    /**
     * Method that plays the sound of a move.
     */
    public void playMoveSound() {
        int i = (int) (Math.random() * 5) + 1;
        SoundManager soundEffect = new SoundManager();
        soundEffect.setFile(i);
        soundEffect.play();
    }

    /**
     * Method that plays the sound of a collision.
     */
    public void playCollisionSound() {
        int i = (int) (Math.random() * 3) + 6;
        SoundManager soundEffect = new SoundManager();
        soundEffect.setFile(i);
        soundEffect.play();
    }

    /**
     * Method that plays the sound of a win.
     */
    public void playNote() {
        int i = (int) (Math.random() * 5) + 9;
        SoundManager soundEffect = new SoundManager();
        soundEffect.setFile(i);
        soundEffect.play();
    }

    /**
     * Method that plays the sound of a win.
     */
    public void playOST(){
        if (musicMuted) return;
        playMusic(0);
    }

    /**
     * Method that toggles the mute of the music.
     */
    public void toggleMute() {
        if (clip.isActive()) {
            stop();
            musicMuted = true;
        } else {
            play();
            musicMuted = false;
        }
    }

    /**
     * Method that toggles the mute of the sound effects.
     */
    public void toggleSoundEffects(){
        soundEffectsMuted = !soundEffectsMuted;
    }

    /**
     * @return true if the sound effects are muted
     */
    public boolean isSoundEffectsMuted() {
        return soundEffectsMuted;
    }

    /**
     * @return true if the music is muted
     */
    public boolean isMusicMuted() {
        return musicMuted;
    }
}
