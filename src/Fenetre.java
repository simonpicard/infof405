//Les imports habituels
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class Fenetre extends JFrame {
  private JTextField jtf = new JTextField("127.0.0.1");
  private JLabel label = new JLabel("Receiver IP");
  private JFileChooser dialogue = new JFileChooser(new File("."));
  private JButton b = new JButton ("Select file");

  public Fenetre(){
    this.setTitle("Send a file");
    this.setSize(300, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    JPanel top = new JPanel();
    Font police = new Font("Arial", Font.BOLD, 14);
    jtf.setFont(police);
    jtf.setPreferredSize(new Dimension(150, 30));
    jtf.setForeground(Color.BLACK);
    top.add(label);
    top.add(jtf);
    top.add(b);
    this.setContentPane(top);
    this.setVisible(true);            
  }
}