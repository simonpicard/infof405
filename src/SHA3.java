
import java.io.IOException;

import SHA3.sha3sum;


public class SHA3 {
	public static byte[] hash(byte[] data){
		String[] argv = {".\\SHA3test.txt"};
		try {
			return sha3sum.run("sha3sum", argv, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[1];
	}

}
