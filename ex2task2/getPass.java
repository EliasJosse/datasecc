import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class getPass {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		/*
		 * Determine password length
		 */
		Secret sec = new Secret();
		int passLen = 0;
		String pass = "";
		for (int i = 1; i < 50; i++) {
			pass += "1";
			try {
				sec.verifyPassword(pass);
				passLen = i;
				break;
			}
			catch(IllegalArgumentException e) {	
				System.out.println("It failed");
			}
		}
		System.out.println("Length of pass is " + passLen);
		
		
		/*
		 * Determine pass by saving times of each digit at place i in password then choosing the slowest.
		 */
		char[] passArr = new char[passLen];
		Arrays.fill(passArr, '0');
		
		pass = "";
		for (int i = 0; i < passArr.length; i++) {
			long[] times = new long[10];
			
			for (int j = 0; j < 10; j++) {
				
				passArr[i] = Character.forDigit(j, 10);
				String testPass = new String(passArr);
				
				long tempTime = System.currentTimeMillis();
				sec.verifyPassword(testPass);
				tempTime = System.currentTimeMillis() - tempTime;
				
				times[j] = tempTime;
			
			}
			long max = 0;
			int dig = 0;
			for (int j = 0; j < times.length; j++) {
				if(times[j] > max){
					max = times[j];
					dig = j;
				}
			}
			pass += Character.forDigit(dig, 10);
			System.out.println(pass);
			passArr[i] = Character.forDigit(dig, 10);
		}
		
		System.out.println("Password is " + pass);
		String secret = sec.getSecret();
		System.out.println("Secret is " + secret);
		
		/*  This gives:
		 *  Password: 1655540751432153
		 *	Secret: 6299DA67DB8474B907E380912F34F337FE3DF89E
		 *
		 * By checking length of Secret(160 bits) a guess can be made that the relation of the two
		 * is the SHA-1 hashing algorithm
		 * 
		 * Test:
		 */
		
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] passinByte = md.digest(pass.getBytes());
		String maybeSecret = getPass.bytesToHex(passinByte);
		maybeSecret = maybeSecret.toUpperCase();
		System.out.println("Hashing password with SHA-1 gives " + maybeSecret);
		/*
		 * This produces:
		 * 6299da67db8474b907e380912f34f337fe3df89e
		 * is this the secret?
		 */
		
		System.out.print("Are they same? ");
		System.out.println(maybeSecret.equals(secret) ? "YES" : "No");
		
		
		
	}
	
	 private static String bytesToHex(byte[] hashInBytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();

	    }

}
