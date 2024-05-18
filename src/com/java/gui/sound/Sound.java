package src.com.java.gui.sound;

import src.com.java.config.GuiConfig;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    Clip clip ;
    File[] soundFiles = new File[20];

    public Sound() {
        soundFiles[0] = new File(GuiConfig.resourcePath + "sfx/bg.wav");
        soundFiles[1] = new File(GuiConfig.resourcePath + "sfx/move1.wav");
        soundFiles[2] = new File(GuiConfig.resourcePath + "sfx/move2.wav");
        soundFiles[3] = new File(GuiConfig.resourcePath + "sfx/move3.wav");
        soundFiles[4] = new File(GuiConfig.resourcePath + "sfx/move4.wav");
        soundFiles[5] = new File(GuiConfig.resourcePath + "sfx/move5.wav");
        soundFiles[6] = new File(GuiConfig.resourcePath + "sfx/collision1.wav");
        soundFiles[7] = new File(GuiConfig.resourcePath + "sfx/collision2.wav");
        soundFiles[8] = new File(GuiConfig.resourcePath + "sfx/collision3.wav");
        soundFiles[9] = new File(GuiConfig.resourcePath + "sfx/note1.wav");
        soundFiles[10] = new File(GuiConfig.resourcePath + "sfx/note2.wav");
        soundFiles[11] = new File(GuiConfig.resourcePath + "sfx/note3.wav");
        soundFiles[12] = new File(GuiConfig.resourcePath + "sfx/note4.wav");
        soundFiles[13] = new File(GuiConfig.resourcePath + "sfx/note5.wav");
        soundFiles[14] = new File(GuiConfig.resourcePath + "sfx/note6.wav");
        soundFiles[15] = new File(GuiConfig.resourcePath + "sfx/note7.wav");
        soundFiles[16] = new File(GuiConfig.resourcePath + "sfx/note8.wav");
        soundFiles[17] = new File(GuiConfig.resourcePath + "sfx/note9.wav");
        soundFiles[18] = new File(GuiConfig.resourcePath + "sfx/note10.wav");

    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFiles[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) clip.start();
    }

    public void stop() {
        if (clip != null) clip.stop();
    }

    public void loop() {
        if (clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playMusic(int i) {
        setFile(i);
        play();
        loop();
    }

    public void playMoveSound() {
        int i = (int) (Math.random() * 5) + 1;
        Sound soundEffect = new Sound();
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void stopMusic() {
        stop();
    }


    public void playCollisionSound() {
        int i = (int) (Math.random() * 3) + 6;
        Sound soundEffect = new Sound();
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void playNote() {
        int i = (int) (Math.random() * 10) + 9;
        Sound soundEffect = new Sound();
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void playOST(){
        playMusic(0);
    }
}
