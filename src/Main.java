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
		
		//System.out.println(InetAddress.getLocalHost().getHostAddress());
        
		if (args.length == 0){
			Bob bob = new Bob("./bob_public_key.der", "./bob_private_key.der", 1337, ".\\rcvfile.txt");
		}
		else{
			Alice alice = new Alice("./alice_public_key.der", "./alice_private_key.der", "172.23.218.63", 1337, ".\\test.txt");
		}
	}

}
