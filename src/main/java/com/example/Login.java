package com.example;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;

import net.miginfocom.swing.MigLayout;

public class Login extends JPanel {
    public Login(){
        init();
    }

    private void init(){
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            
        });
        setLayout(new MigLayout("wrap,fillx,insets 45 45 50 45" , "[fill]"));
        JLabel title = new JLabel("Login to your account" , SwingConstants.CENTER);
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JCheckBox chRemeberMe = new JCheckBox("Remember Me");
        JButton cmdLogin = new JButton("Login");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
             "fint:bolt +10");
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "" +
                    "margin: 5, 10 , 5 ,10;" +
                    "focusWidth:1;" +
                    "innerFocusWidth:0");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                    "margin: 5, 10 , 5 ,10;" +
                    "focusWidth:1;" +
                    "innerFocusWidth:0" +
                    "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                    "background:$Component.accentColor;" +
                    "borderWidth:0;" +
                    "focusWidth:0;" +
                    "innerFocusWidth:0");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        add(title);
        add(new JLabel("Username"),"gapy 28");
        add(txtUsername);
        add(new JLabel("Password"),"gapy 18");
        add(txtPassword);
        add(chRemeberMe);
        add(cmdLogin , "gapy30");
    } 
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = UIScale.scale(20);
        g2.setColor(getBackground());
        g2.setComposite(AlphaComposite.SrcOver.derive(0.6f));
        g2.fill(new RoundRectangle2D.Double(0 , 0 , getWidth() , getHeight() , arc ,arc));
        g2.dispose();
        super.paintComponent(g);
    }
}

