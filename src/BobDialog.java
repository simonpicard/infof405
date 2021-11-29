import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class BobDialog extends JDialog {
	
	boolean flag = false;
	String bobPub;
	String bobPriv;
	int bobPort;
	String bobFp;

    public BobDialog(JFrame owner) {
        super(owner,true);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    JLabel jlblPort = new JLabel();
    JTextField jtfPort = new JTextField();
    
    
    JLabel jlblFile = new JLabel();
    JTextField jtfFile = new JTextField();
    JButton jbtnFile = new JButton();
    
    
    
    JLabel jlblFilePub = new JLabel();
    JTextField jtfFilePub = new JTextField();
    JButton jbtnFilePub = new JButton();
    

    JLabel jlblFilePrive = new JLabel();
    JTextField jtfFilePrive = new JTextField();
    JButton jbtnFilePrive = new JButton();
    
    JButton jbtnOK = new JButton();
    JButton jbtnCancel = new JButton();
    
    
    
    
  private void jbInit() throws Exception {
    	

	    this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(new Color(206, 227, 249));
        this.setTitle("Receiving");
    	
    	//

        
        
        
        //
        jlblPort.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jlblPort.setText("Port");
        jlblPort.setBounds(new Rectangle(32, 67, 67, 31));
        
        jtfPort.setBackground(Color.white);
        jtfPort.setBorder(BorderFactory.createEtchedBorder());
        jtfPort.setText("1337");
        jtfPort.setBounds(new Rectangle(129, 72, 62, 21));
        
        //
        jlblFile.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jlblFile.setText("File to save");
        jlblFile.setBounds(new Rectangle(32, 120, 100, 16));
        
        jtfFile.setBackground(Color.white);
        jtfFile.setBorder(BorderFactory.createEtchedBorder());
        jtfFile.setText("./rcvfile.txt");
        jtfFile.setBounds(new Rectangle(127, 118, 164, 21));

        jbtnFile.setBackground(new Color(236, 247, 255));
        jbtnFile.setBounds(new Rectangle(297, 116, 71, 25));
        jbtnFile.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jbtnFile.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnFile.setText("broswer");
        jbtnFile.addActionListener(new SettingDialog_jbtnFile_actionAdapter(this));

        //
        jlblFilePub.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jlblFilePub.setText("Public certificate");
        jlblFilePub.setBounds(new Rectangle(32, 165, 100, 16));
        
        jtfFilePub.setBackground(Color.white);
        jtfFilePub.setBorder(BorderFactory.createEtchedBorder());
        jtfFilePub.setText("./receiver_public_certificate.der");
        jtfFilePub.setBounds(new Rectangle(127, 163, 164, 21));

        jbtnFilePub.setBackground(new Color(236, 247, 255));
        jbtnFilePub.setBounds(new Rectangle(297, 163, 71, 25));
        jbtnFilePub.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jbtnFilePub.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnFilePub.setText("broswer");
        jbtnFilePub.addActionListener(new SettingDialog_jbtnFilePub_actionAdapter(this));

        //
        jlblFilePrive.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jlblFilePrive.setText("Private key");
        jlblFilePrive.setBounds(new Rectangle(32, 210, 100, 16));
        
        jtfFilePrive.setBackground(Color.white);
        jtfFilePrive.setBorder(BorderFactory.createEtchedBorder());
        jtfFilePrive.setText("./receiver_private_key.der");
        jtfFilePrive.setBounds(new Rectangle(127, 208, 164, 21));

        jbtnFilePrive.setBackground(new Color(236, 247, 255));
        jbtnFilePrive.setBounds(new Rectangle(297, 208, 71, 25));
        jbtnFilePrive.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jbtnFilePrive.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnFilePrive.setText("broswer");
        jbtnFilePrive.addActionListener(new SettingDialog_jbtnFilePrive_actionAdapter(this));
        

        
        
        
        jbtnCancel.setBackground(new Color(236, 247, 255));
        jbtnCancel.setBounds(new Rectangle(294, 250, 71, 25));
        jbtnCancel.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jbtnCancel.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnCancel.setText("Cancel");
        jbtnCancel.addActionListener(new
        		SettingDialog_jbtnCancel_actionAdapter(this));
        jbtnOK.setBackground(new Color(236, 247, 255));
        jbtnOK.setBounds(new Rectangle(195, 250, 71, 25));
        jbtnOK.setFont(new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 12));
        jbtnOK.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnOK.setText("OK");
        jbtnOK.addActionListener(new SettingDialog_jbtnOK_actionAdapter(this));
        
        
        

        this.getContentPane().add(jlblPort);
        this.getContentPane().add(jtfPort);
        
        
        this.getContentPane().add(jlblFile);
        this.getContentPane().add(jtfFile);
        this.getContentPane().add(jbtnFile);
        
        
        this.getContentPane().add(jlblFilePub);
        this.getContentPane().add(jtfFilePub);
        this.getContentPane().add(jbtnFilePub);
        
        
        this.getContentPane().add(jlblFilePrive);
        this.getContentPane().add(jtfFilePrive);
        this.getContentPane().add(jbtnFilePrive);
        
        
        this.getContentPane().add(jbtnCancel);
        this.getContentPane().add(jbtnOK);


        this.setSize(400,400);
        this.setLocation(300,300);
        
      
        
    }
    
  
 public void jbtnFile_actionPerformed(ActionEvent e) {
     JFileChooser jfc = new JFileChooser();
     jfc.setCurrentDirectory(new File("."));
     File aFile;
     String fileName;
     jfc.showSaveDialog(this);
     aFile = jfc.getSelectedFile();
     if (aFile != null) {
         fileName = jfc.getSelectedFile().getAbsolutePath();
         jtfFile.setText(fileName);
     }
  }
  
  
  public void jbtnFilePub_actionPerformed(ActionEvent e) {
      JFileChooser jfc=new JFileChooser();
      jfc.setCurrentDirectory(new File("."));
      String fileName="";
      
      jfc.showOpenDialog(this);
      File sendingFilePub;
      sendingFilePub = jfc.getSelectedFile();
      
      fileName=fileName+sendingFilePub.getAbsolutePath();
 
      jtfFilePub.setText(fileName);
  }
  
  
  
  public void jbtnFilePrive_actionPerformed(ActionEvent e) {

      JFileChooser jfc=new JFileChooser();
      jfc.setCurrentDirectory(new File("."));
      String fileName="";
      
      jfc.showOpenDialog(this);
      File sendingFilePrive;
      sendingFilePrive = jfc.getSelectedFile();
      
      fileName=fileName+sendingFilePrive.getAbsolutePath();
 
      jtfFilePrive.setText(fileName);
	  
  }
   
    
  public void jbtnOK_actionPerformed(ActionEvent e) {
      if(jtfPort.getText().trim().equals("")||jtfFile.getText().trim().equals("")||jtfFilePub.getText().trim().equals("")||jtfFilePrive.getText().trim().equals("")){
          JOptionPane.showMessageDialog(this,"Error");
          return;
      }
  	this.bobPub = jtfFilePub.getText();
  	this.bobPriv = jtfFilePrive.getText();

  	this.bobPort = Integer.parseInt(jtfPort.getText());
  	this.bobFp = jtfFile.getText();
      flag=true;
      this.setVisible(false);
  }
  
  

  public void jbtnCancel_actionPerformed(ActionEvent e) {
      this.setVisible(false);
  }
}

	
class SettingDialog_jbtnCancel_actionAdapter implements ActionListener {
  private BobDialog adaptee;
  SettingDialog_jbtnCancel_actionAdapter(BobDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.jbtnCancel_actionPerformed(e);
  }
}


class SettingDialog_jbtnOK_actionAdapter implements ActionListener {
  private BobDialog adaptee;
  SettingDialog_jbtnOK_actionAdapter(BobDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.jbtnOK_actionPerformed(e);
  }
}


class SettingDialog_jbtnFile_actionAdapter implements ActionListener {
  private BobDialog adaptee;
  SettingDialog_jbtnFile_actionAdapter(BobDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.jbtnFile_actionPerformed(e);
  }
}



class SettingDialog_jbtnFilePub_actionAdapter implements ActionListener {
  private BobDialog adaptee;
  SettingDialog_jbtnFilePub_actionAdapter(BobDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.jbtnFilePub_actionPerformed(e);
  }
}


class SettingDialog_jbtnFilePrive_actionAdapter implements ActionListener {
  private BobDialog adaptee;
  SettingDialog_jbtnFilePrive_actionAdapter(BobDialog adaptee) {
      this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
      adaptee.jbtnFilePrive_actionPerformed(e);
  }
}
	


    
	

