package com.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.MediaEventAdapter;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class Home extends JPanel {
   
    private List<ModelLocation> locations;
    private int index = 0;
    private HomeOverlay homeOverlay;
    private MediaPlayerFactory factory;
    private EmbeddedMediaPlayer mediaPlayer;

    public Home(){
        init();
        testdate();
    }
    public void init(){
        factory = new MediaPlayerFactory();
        mediaPlayer=factory.mediaPlayers().newEmbeddedMediaPlayer();
        Canvas canvas = new Canvas();
        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter(){
            @Override
            public void timeChanged(MediaPlayer mediaPlayer , long newTime) {
                if(newTime >= mediaPlayer.status().length() - 1000){
                    mediaPlayer.controls().setPosition(0);
                }
            }
        });
        setLayout(new BorderLayout());
        add(canvas);
    }

    private void testdate(){
        locations = new ArrayList<>();
        locations.add(new ModelLocation("Power of Algorithms",
                                        "Algorithms are the hidden architects of our digital world, weaving intricate patterns of logic and computation into the fabric of our daily lives. From the moment you wake up and check your smartphone to the second you stream your favorite show, algorithms are at work behind the scenes, making decisions, solving problems, and optimizing outcomes.",
                                        "videos/video1.mp4"));

        locations.add(new ModelLocation("Text \n text",
                                        "text text text",
                                        "videos/video2.mp4"));

        locations.add(new ModelLocation("Text \n text",
                                        "text text text",
                                        "videos/video3.mp4"));
    }

    public void initOverlay (JFrame frame){
        homeOverlay=new HomeOverlay(frame, locations);
        homeOverlay.getOverlay().setEventHomeOverlay(index1 -> {
            play(index1);
        });
        mediaPlayer.overlay().set(homeOverlay);
        mediaPlayer.overlay().enable(true);
    }

    public void play(int index){
        this.index = index;
        ModelLocation location = locations.get(index);
        if(mediaPlayer.status().isPlaying()){
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play(location.getVideoPath());
        mediaPlayer.controls().play();
        homeOverlay.getOverlay().setIndex(index);
    }
    
    public void stop(){
        mediaPlayer.controls().stop();
        mediaPlayer.release();
        factory.release();
    }
}
