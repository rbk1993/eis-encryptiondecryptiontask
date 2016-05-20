import java.security.SecureRandom;
import java.math.BigInteger;

public class KSGenerator {
	private static SecureRandom random = new SecureRandom();
	private static String randomString;
  	public static String generateRandom16BytesString() {
		randomString = new BigInteger(80, random).toString(36);
  		return randomString;
	}
  	public static void main(String[] Args) {
  		
		String[] keyStringLibrary = new String[255];
		
		System.out.println("String[] keyStringLibrary = new String[255]");
			
		for(int i=0;i<keyStringLibrary.length;i++) {
			keyStringLibrary[i] = generateRandom16BytesString();
			System.out.println("keyStringLibrary["+i+"] = \""+keyStringLibrary[i]+"\";");
		}
 	}
}	
