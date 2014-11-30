import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import javax.crypto.spec.SecretKeySpec;




public class Main {

	public static void main(String[] args) {
		
        /*
		
		if (args.length == 1){
			Bob bob = new Bob("./bob_public_certificate.der", "./bob_private_key.der", 1337, ".\\rcvfile.txt");
		}
		else{
			try {
				Alice alice = new Alice("./alice_public_certificate.der", "./alice_private_key.der", InetAddress.getLocalHost().getHostAddress(), 1338, ".\\test.txt");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
