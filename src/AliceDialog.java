import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;

import java.awt.Rectangle;

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




public class AliceDialog extends JDialog {
	
	
	
	boolean flag = false;
	String alicePub;
	String alicePriv;
	String aliceIp;
	int alicePort;
	String aliceFp;
	
    public AliceDialog(JFrame owner) {
        super(owner,true);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
    
    
    
    JLabel jlblServerName = new JLabel();
    JTextField jtfServerName = new JTextField();
    
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
    	

    	
        this.getContentPane().setBackground(new Color(206, 227, 249));
        this.setTitle("Sending");
    	
    	//
        jlblServerName.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jlblServerName.setText("Bob's IP");
        jlblServerName.setBounds(new Rectangle(32, 22, 73, 26));
        this.getContentPane().setLayout(null);
        
        jtfServerName.setBackground(Color.white);
        jtfServerName.setBorder(BorderFactory.createEtchedBorder());
        jtfServerName.setText("");
        jtfServerName.setBounds(new Rectangle(127, 25, 220, 21));
        
        
        
        //
        jlblPort.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jlblPort.setText("Port");
        jlblPort.setBounds(new Rectangle(32, 67, 67, 31));
        
        jtfPort.setBackground(Color.white);
        jtfPort.setBorder(BorderFactory.createEtchedBorder());
        jtfPort.setText("1377");
        jtfPort.setBounds(new Rectangle(129, 72, 62, 21));
        
        //
        jlblFile.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jlblFile.setText("File");
        jlblFile.setBounds(new Rectangle(32, 120, 34, 16));
        
        jtfFile.setBackground(Color.white);
        jtfFile.setBorder(BorderFactory.createEtchedBorder());
        jtfFile.setText("");
        jtfFile.setBounds(new Rectangle(127, 118, 164, 21));

        jbtnFile.setBackground(new Color(236, 247, 255));
        jbtnFile.setBounds(new Rectangle(297, 116, 71, 25));
        jbtnFile.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jbtnFile.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnFile.setText("broswer");
        jbtnFile.addActionListener(new TransFileDialog_jbtnFile_actionAdapter(this));

        //
        jlblFilePub.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jlblFilePub.setText("Public key");
        jlblFilePub.setBounds(new Rectangle(32, 165, 34, 16));
        
        jtfFilePub.setBackground(Color.white);
        jtfFilePub.setBorder(BorderFactory.createEtchedBorder());
        jtfFilePub.setText("");
        jtfFilePub.setBounds(new Rectangle(127, 163, 164, 21));

        jbtnFilePub.setBackground(new Color(236, 247, 255));
        jbtnFilePub.setBounds(new Rectangle(297, 163, 71, 25));
        jbtnFilePub.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jbtnFilePub.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnFilePub.setText("broswer");
        jbtnFilePub.addActionListener(new TransFileDialog_jbtnFilePub_actionAdapter(this));

        //
        jlblFilePrive.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jlblFilePrive.setText("Private key");
        jlblFilePrive.setBounds(new Rectangle(32, 210, 34, 16));
        
        jtfFilePrive.setBackground(Color.white);
        jtfFilePrive.setBorder(BorderFactory.createEtchedBorder());
        jtfFilePrive.setText("");
        jtfFilePrive.setBounds(new Rectangle(127, 208, 164, 21));

        jbtnFilePrive.setBackground(new Color(236, 247, 255));
        jbtnFilePrive.setBounds(new Rectangle(297, 208, 71, 25));
        jbtnFilePrive.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jbtnFilePrive.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnFilePrive.setText("broswer");
        jbtnFilePrive.addActionListener(new TransFileDialog_jbtnFilePrive_actionAdapter(this));
        

        
        
        
        jbtnCancel.setBackground(new Color(236, 247, 255));
        jbtnCancel.setBounds(new Rectangle(294, 250, 71, 25));
        jbtnCancel.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jbtnCancel.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnCancel.setText("Cancel");
        jbtnCancel.addActionListener(new
                                     TransFileDialog_jbtnCancel_actionAdapter(this));
        jbtnOK.setBackground(new Color(236, 247, 255));
        jbtnOK.setBounds(new Rectangle(195, 250, 71, 25));
        jbtnOK.setFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        jbtnOK.setBorder(BorderFactory.createRaisedBevelBorder());
        jbtnOK.setText("OK");
        jbtnOK.addActionListener(new TransFileDialog_jbtnOK_actionAdapter(this));
        
        
        
        
        this.getContentPane().add(jlblServerName, null);
        this.getContentPane().add(jtfServerName);
        
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
        JFileChooser jfc=new JFileChooser();
        jfc.setMultiSelectionEnabled(true);
        String fileName="";
        
        jfc.showOpenDialog(this);
        File sendingFile;
        sendingFile = jfc.getSelectedFile();
        
        fileName=fileName+sendingFile.getAbsolutePath();
   
        jtfFile.setText(fileName);
    }
    
    
    public void jbtnFilePub_actionPerformed(ActionEvent e) {
        JFileChooser jfc=new JFileChooser();
        jfc.setMultiSelectionEnabled(true);
        String fileName="";
        
        jfc.showOpenDialog(this);
        File sendingFilePub;
        sendingFilePub = jfc.getSelectedFile();
        
        fileName=fileName+sendingFilePub.getAbsolutePath();
   
        jtfFilePub.setText(fileName);
    }
    
    
    
    public void jbtnFilePrive_actionPerformed(ActionEvent e) {
        JFileChooser jfc=new JFileChooser();
        jfc.setMultiSelectionEnabled(true);
        String fileName="";
        
        jfc.showOpenDialog(this);
        File sendingFilePrive;
        sendingFilePrive = jfc.getSelectedFile();
        
        fileName=fileName+sendingFilePrive.getAbsolutePath();
   
        jtfFilePrive.setText(fileName);
    }
    
    

    public void jbtnOK_actionPerformed(ActionEvent e) {
        if(jtfServerName.getText().trim().equals("")||jtfPort.getText().trim().equals("")||jtfFile.getText().trim().equals("")||jtfFilePub.getText().trim().equals("")||jtfFilePrive.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,"Error！");
            return;
        }
    	this.alicePub = jtfFilePub.getText();
    	this.alicePriv = jtfFilePrive.getText();
    	this.aliceIp = jtfServerName.getText();
    	this.alicePort = Integer.parseInt(jtfPort.getText());
    	this.aliceFp = jtfFile.getText();
        flag=true;
        this.setVisible(false);
    }
    
    

    public void jbtnCancel_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}

	
class TransFileDialog_jbtnCancel_actionAdapter implements ActionListener {
    private AliceDialog adaptee;
    TransFileDialog_jbtnCancel_actionAdapter(AliceDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbtnCancel_actionPerformed(e);
    }
}


class TransFileDialog_jbtnOK_actionAdapter implements ActionListener {
    private AliceDialog adaptee;
    TransFileDialog_jbtnOK_actionAdapter(AliceDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbtnOK_actionPerformed(e);
    }
}


class TransFileDialog_jbtnFile_actionAdapter implements ActionListener {
    private AliceDialog adaptee;
    TransFileDialog_jbtnFile_actionAdapter(AliceDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbtnFile_actionPerformed(e);
    }
}


class TransFileDialog_jbtnFilePub_actionAdapter implements ActionListener {
    private AliceDialog adaptee;
    TransFileDialog_jbtnFilePub_actionAdapter(AliceDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbtnFilePub_actionPerformed(e);
    }
}


class TransFileDialog_jbtnFilePrive_actionAdapter implements ActionListener {
    private AliceDialog adaptee;
    TransFileDialog_jbtnFilePrive_actionAdapter(AliceDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbtnFilePrive_actionPerformed(e);
    }
}
	




