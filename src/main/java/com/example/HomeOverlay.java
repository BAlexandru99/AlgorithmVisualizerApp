package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JWindow;

import com.example.components.HeaderButton;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class HomeOverlay extends JWindow {

    public PanelOverlay getOverlay() {
        return overlay;
    }

    private PanelOverlay overlay;
    private List<ModelLocation> locations;

    public HomeOverlay(JFrame jFrame, List<ModelLocation> locations) {
        super(jFrame);
        this.locations = locations;
        init();
    }

    private void init() {
        setBackground(new Color(35, 66, 134, 60));
        setLayout(new BorderLayout());
        overlay = new PanelOverlay();
        add(overlay);
    }

    public class PanelOverlay extends JPanel {

        private int index;

        public void setIndex(int index) {
            this.index = index;
            ModelLocation location = locations.get(index);
            textTitle.setText(location.getTitle());
            textDescription.setText(location.getDescription());
        }

        public PanelOverlay() {
            init();
        }

        private void init() {
            setOpaque(false);
            setLayout(new MigLayout("insets 20 360 50 50", "[]push[center]push[]", "[]"));
            createHeader();

            // MigLayout configuration: adjust for proper placement of components.
            JPanel panel = new JPanel(new MigLayout("wrap", "", "[]30[]"));            panel.setOpaque(false);

            textTitle = new JTextPane();
            textDescription = new JTextPane();
            cmdReadMore = new JButton("Read More");

            textTitle.setOpaque(false);
            textTitle.setEditable(false);
            textTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bolt +40;" +
                "border: 150,0,0,0");

            textDescription.setOpaque(false);
            textDescription.setEditable(false);
            textDescription.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bolt +2;" +
                "border:0,0,0,0");

            cmdReadMore.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:$Component.accentColor;" +
                "borderWidth:0;" +
                "margin:5,10,5,10;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "arc:999");

            // Add components each on their own row
            panel.add(textTitle, "wrap");
            panel.add(textDescription, "wrap");
            panel.add(cmdReadMore, "wrap");

            add(panel, "width 58%!");
        }

        private void createHeader() {
            header = new JPanel(new MigLayout("insets 20 30 20 30", 
                "[]20[]20[]", 
                "[]"));
            header.setOpaque(false);

            HeaderButton home = new HeaderButton("Home");
            HeaderButton about = new HeaderButton("About");

            JLabel title = new JLabel("ALGORITMS");
            title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bolt 35");

            HeaderButton pricing = new HeaderButton("Pricing");
            HeaderButton logIn = new HeaderButton("LogIn");

            header.add(home, "left");
            header.add(about, "left");

            header.add(title, "center"); // Centered title

            header.add(pricing, "right");
            header.add(logIn, "right");

            add(header, "wrap");
        }

        private JPanel header;
        private JTextPane textTitle;
        private JTextPane textDescription;
        private JButton cmdReadMore;
    }

}
