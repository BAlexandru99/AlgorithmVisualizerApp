package com.example;

import java.awt.geom.Area;
import java.net.URI;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.List;

import com.example.components.EventHomeOverlay;
import com.example.components.HeaderButton;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.CubicBezierEasing;

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

        public void setEventHomeOverlay(EventHomeOverlay eventHomeOverlay){
            this.eventHomeOverlay = eventHomeOverlay;
        }

        private MigLayout migLayout;
        private EventHomeOverlay eventHomeOverlay;
        private AnimationType animationType=AnimationType.NONE;
        private Animator animator;
        private Animator loginAnimator;
        private float animate;
        private int index;
        private boolean showLogin;

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
            migLayout = new MigLayout("insets 20 360 50 50", "[]push[center]push[]", "[]");
            setLayout(migLayout);
            createHeader();
            createPageButton();
            createLogin();

            JPanel panel = new JPanel(new MigLayout("wrap", "", "[]30[]"));          
            panel.setOpaque(false);

            textTitle = new JTextPane();
            textDescription = new JTextPane();
            cmdReadMore = new JButton("Read More");

            cmdReadMore.addActionListener(e -> openReadMorePage());

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
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e){
                    runLoginAnimation(false);
                }
            });

            animator = new Animator(500, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v){
                    animate = v;
                    repaint();
                }

                @Override
                public void end(){
                    if(animationType==AnimationType.CLOSE_VIDEO){
                        eventHomeOverlay.onChanged(index);
                        SwingUtilities.invokeLater(() ->{
                            sleep(500);
                            runAnimation(index, AnimationType.SHOW_VIDEO);
                        });
                    }else{
                        animationType=AnimationType.NONE;
                    }
                }
            });
            loginAnimator = new Animator(300 , new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v) {
                    float f = showLogin ? v : 1f - v ;
                    int x = (int)((358 + 180) * f);
                    migLayout.setComponentConstraints(panelLogin, "pos 100%-" + x + " 0.5al, w 350");
                    revalidate();
                }
            });
            animator.setInterpolator(CubicBezierEasing.EASE_IN);
            loginAnimator.setInterpolator(CubicBezierEasing.EASE);
        }

        private void sleep(long l){
            try {
                Thread.sleep(l);
            } catch (Exception e) {
                System.err.println(e);
            }
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
            
            logIn.addActionListener(e -> {
                runLoginAnimation(true);
            });

            header.add(home, "left");
            header.add(about, "left");

            header.add(title, "center"); // Centered title

            header.add(pricing, "right");
            header.add(logIn, "right");

            add(header, "wrap");
        }

        private void createLogin(){
            panelLogin = new Login();
            add(panelLogin , "pos 100% 0.5al, w 350");
        }

        public void openReadMorePage(){
            try{
                URI uri = new URI("https://en.wikipedia.org/wiki/Algorithm");
                Desktop.getDesktop().browse(uri);
            }catch(Exception e){
            }
        }

        private void createPageButton(){
            JPanel panel = new JPanel(new MigLayout("gapx 50"));
            panel.setOpaque(false);
            for(int i = 0 ; i < locations.size() ; i++){
                JButton cmd = new JButton("");
                cmd.putClientProperty(FlatClientProperties.STYLE,"" +
                            "margin: 5,5,5,5;" +
                            "arc:999;" +
                            "borderWidth:0;" +
                            "focusWidth:0;" +
                            "innerFocusWidth:0;" +
                            "selectedBackround:$Component.accentColor");
                cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                final int index = i;
                cmd.addActionListener(e -> {
                    if(this.index != index){
                        boolean act = runAnimation(index, AnimationType.CLOSE_VIDEO);
                        if(act){
                        setSelectedButton(panel, index);
                    }
                    }
                });
                panel.add(cmd);
            }
            add(panel, "pos 0.5al 80%");
            setSelectedButton(panel, index);
        }

        private void setSelectedButton(JPanel panel, int index){
            int count = panel.getComponentCount();
            for(int i = 0 ; i < count ; i++){
                JButton cmd = (JButton) panel.getComponent(i);
                if(i == index){
                    cmd.setSelected(true);
                    cmd.putClientProperty(FlatClientProperties.STYLE, "" +
                        "background:$Component.accentColor;" +
                        "arc:999;");
                } else {
                    cmd.setSelected(false);
                    cmd.putClientProperty(FlatClientProperties.STYLE, "" +
                        "background:$Component.background;" +
                        "arc:999;");
                }
            }
        }

        private boolean runAnimation(int index , AnimationType animationType){
            if(!animator.isRunning()){
                this.animate = 0;
                this.animationType = animationType;
                this.index = index;
                animator.start();
                return true;
            }else{
                return false;
            }
            }
        
        private void runLoginAnimation(boolean show){
            if(showLogin!=show){
                if(!loginAnimator.isRunning()){
                    showLogin=show;
                    loginAnimator.start();
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g){
            if(animationType!=AnimationType.NONE){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                g2.setColor(UIManager.getColor("Component.accentColor"));
                Rectangle rec = new Rectangle(0 , 0 , width , height);
                if(animationType==AnimationType.CLOSE_VIDEO){
                    g2.setComposite(AlphaComposite.SrcOver.derive(animate));
                    g2.fill(rec);
                
                }else{
                    Area area = new Area(rec);
                    area.subtract(new Area(createRec(rec)));

                    g2.fill(area);
                }
                g2.dispose();
            }
            super.paintComponent(g);
        }

        private Shape createRec(Rectangle rec){
            int maxSize = Math.max(rec.width , rec.height);
            float size = maxSize * animate;
            float x = (rec.height-size)/2;
            float y = (rec.height-size)/2;
            Ellipse2D ell = new Ellipse2D.Double(x , y, size ,size);
            return ell;
        }

        private JPanel header;
        private JTextPane textTitle;
        private JTextPane textDescription;
        private JButton cmdReadMore;
        private Login panelLogin;
    }

        public enum AnimationType{
            CLOSE_VIDEO, SHOW_VIDEO, NONE
        }
 
}


    

