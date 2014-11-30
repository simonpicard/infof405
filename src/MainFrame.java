import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;




public class MainFrame extends JFrame {

    JPanel contentPane;
    JButton jbtnSend = new JButton();//set for Alice
    JLabel jlblIP = new JLabel();
    JButton jbtnSetting = new JButton();//set for Bob
    JTextArea jlog = new JTextArea(); // log
    //JTabbedPane jtpTransFile = new JTabbedPane(); // log
    /*
    String alicePub;
    String alicePrive;
    String aliceIp;
    int alicePort;
    String aliceFp;

    String bobPub = "./bob_public_key.der";
    String bobPrive = "./bob_private_key.der";
    int bobPort = 1337;
    String bobFp = ".\\rcvfile.txt";
    */
    boolean isSender = false;
    
    
    public MainFrame() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
       this.getContentPane().setBackground(new Color(206, 227, 249));
    	setLayout(null);
    	setBackground(new Color(206, 227, 249));
        setSize(new Dimension(400, 300));
        setTitle("File Exchanger");
        this.addWindowListener(new MainFrame_this_windowAdapter(this));
        
        
        jbtnSend.setBackground(new Color(236, 247, 255));
        jbtnSend.setBounds(new Rectangle(14, 14, 85, 25));
        jbtnSend.setFont(new java.awt.Font("ËÎÌå", Font.PLAIN, 13));
        jbtnSend.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnSend.setText("Alice");
        jbtnSend.addActionListener(new MainFrame_jbtnSend_actionAdapter(this));
        
        
        jlblIP.setText("My IP£º");
        jlblIP.setFont(new java.awt.Font("ËÎÌå", Font.PLAIN, 13));
        jlblIP.setBounds(new Rectangle(197, 18, 180, 16));
        byte [] ip=InetAddress.getLocalHost().getAddress();
        jlblIP.setText("My IP£º"+(ip[0]&0xff)+"."+(ip[1]&0xff)+"."+(ip[2]&0xff)+"."+(ip[3]&0xff));
        
        
        
        jbtnSetting.setBackground(new Color(236, 247, 255));
        jbtnSetting.setBounds(new Rectangle(106, 14, 73, 25));
        jbtnSetting.setFont(new java.awt.Font("ËÎÌå", Font.PLAIN, 13));
        jbtnSetting.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnSetting.setText("Bob");
        jbtnSetting.addActionListener(new MainFrame_jbtnSetting_actionAdapter(this));
        
        jlog.setBackground(new Color(206, 227, 249));
        jlog.setDisabledTextColor(Color.orange);
        jlog.setText("log : ");
        jlog.setWrapStyleWord(true);
        jlog.setBounds(new Rectangle(20, 50, 345, 55));
        
        


        contentPane.setBackground(new Color(206, 227, 249));
        contentPane.setToolTipText("");
        contentPane.add(jlog);
        contentPane.add(jbtnSend);
        contentPane.add(jbtnSetting);
        contentPane.add(jlblIP);

    }
	
	//interaction : bob or alice is triggered
        public void jbtnSetting_actionPerformed(ActionEvent e) {
        BobDialog sd=new BobDialog(this);
   
        sd.show();
        if(sd.flag){
        	Bob bob = new Bob(sd.bobPub, sd.bobPriv, sd.bobPort, sd.bobFp, this);   
        }
      }

    public void jbtnSend_actionPerformed(ActionEvent e) {
        AliceDialog tfd=new AliceDialog(this);
        tfd.show();
        if(tfd.flag){
        	
        	Alice alice = new Alice(tfd.alicePub, tfd.alicePriv, tfd.aliceIp, tfd.alicePort, tfd.aliceFp, this);

            }
        }
    

    public void this_windowClosed(WindowEvent e) {
        this.setVisible(false);
    }
	
    
    
    
    
    
    class MainFrame_this_windowAdapter extends WindowAdapter {
        private MainFrame adaptee;
        MainFrame_this_windowAdapter(MainFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void windowClosed(WindowEvent e) {
            adaptee.this_windowClosed(e);
        }
    }


    class MainFrame_jbtnSend_actionAdapter implements ActionListener {
        private MainFrame adaptee;
        MainFrame_jbtnSend_actionAdapter(MainFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jbtnSend_actionPerformed(e);
        }
    }


    class MainFrame_jbtnSetting_actionAdapter implements ActionListener {
        private MainFrame adaptee;
        MainFrame_jbtnSetting_actionAdapter(MainFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jbtnSetting_actionPerformed(e);
        }
    }

	
	
	
	
	


}
