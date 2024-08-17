package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;

public class Main extends JFrame{

    private Home home;

    public Main(){
        init();
    }

    private void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(UIScale.scale(new Dimension(1365, 768)));
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),30,30));
        setLocationRelativeTo(null);
        home = new Home();
        setContentPane(home);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e){
                home.initOverlay(Main.this);
                home.play(0);
            }

            @Override
            public void windowClosing(WindowEvent e){
                home.stop();
            }
        });
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("C:\\Users\\alexi\\OneDrive\\Desktop\\demo\\src\\main\\java\\resources\\themes\\FlatLaf.properties");
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,13));
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}
