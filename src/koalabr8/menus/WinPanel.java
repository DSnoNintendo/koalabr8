package koalabr8.menus;

import koalabr8.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private JLabel winLabel;
    Color maroon = new Color (128, 0, 0);
    private Launcher lf;

    public WinPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("Title.gif"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Rematch");
        start.setFont(new Font("Courier New", Font.BOLD ,24));
        start.setBounds(150,300,300,50);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));

        winLabel = new JLabel("Player 2 Wins!");
        winLabel.setFont(new Font("Courier New", Font.BOLD ,24));
        winLabel.setForeground(maroon);
        winLabel.setBounds(150,350,175,50);


        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD ,24));
        exit.setBounds(150,400,400,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


        this.add(start);
        this.add(exit);
        this.add(winLabel);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
