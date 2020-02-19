import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import q7.RSA;

public class TestRSA {
	/*
	 * Test the performance of RSA encryption methods
	 * 
	 * 
	 */
	RSA rsa;
	int keysize;
	RSAPublicKey pubKey;
	RSAPrivateKey privKey;
	public TestRSA(int keysize) throws NoSuchAlgorithmException {
		this.keysize = keysize;
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(keysize);
		
		KeyPair pair = gen.generateKeyPair();
		
		pubKey = (RSAPublicKey) pair.getPublic();
		privKey = (RSAPrivateKey) pair.getPrivate();
		
		rsa = new RSA(pubKey.getModulus(), pubKey.getPublicExponent(), privKey.getPrivateExponent());
	}
	
	public void testBigIntMethod(String message) {
		System.out.println("Test of BigInt method using a keysize of " + keysize + "bits");
		System.out.println("Message: " + message);
		long delay = 0;
		
		/*
		 * Run enc
		 */
		byte[] bytes = message.getBytes();
		
		delay = System.currentTimeMillis(); //start
		BigInteger enc = rsa.encrypt(new BigInteger(bytes));
		delay = System.currentTimeMillis() - delay;	 //End
		
		System.out.println("Encryption: " + new String(enc.toByteArray()));
		
			
		System.out.println("Took " + delay +"ms");
		
		delay = System.currentTimeMillis();
		BigInteger dec = rsa.decrypt(enc);
		delay = System.currentTimeMillis() - delay;
		
		String decrypyted = new String(dec.toByteArray());
		
		
		System.out.println("Decryption: "  + decrypyted);
		
		
		
		System.out.println("Took " + delay +"ms");
		System.out.println("\n\n");
	}
	
	public void testAPIMethod(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		System.out.println("Test of API method using a keysize of " + keysize + "bits");
		System.out.println("Message: " + message);
		long delay = 0;
		
		/*
		 * Run enc
		 */
		byte[] msgbyte = message.getBytes();
		

		Cipher cip = Cipher.getInstance("RSA");	
		
		
		cip.init(Cipher.ENCRYPT_MODE, pubKey);
		cip.update(msgbyte);
		delay = System.currentTimeMillis(); //start
		byte[] enc = cip.doFinal();
		delay = System.currentTimeMillis() - delay; //End
		
		System.out.println("Encryption: " + new String(enc));
		
				
		
		System.out.println("Took " + delay +"ms");
		
		
		
		
		cip.init(Cipher.DECRYPT_MODE, privKey);
		cip.update(enc);
		delay = System.currentTimeMillis(); //Start 
		byte[] dec = cip.doFinal();
		delay = System.currentTimeMillis() - delay; //End
		
		System.out.println("Decrypted: " + new String(dec));
		System.out.println("Took " + delay + "ms\n\n\n");
	}	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		String testMsg = "Tjena bra annars eller?";
		TestRSA test = new TestRSA(1024);
		test.testBigIntMethod(testMsg);
		test.testAPIMethod(testMsg);
		TestRSA test1 = new TestRSA(512);
		test1.testBigIntMethod(testMsg);
		test1.testAPIMethod(testMsg);
		TestRSA test2 = new TestRSA(2048);
		test2.testBigIntMethod(testMsg);
		test2.testAPIMethod(testMsg);
	}

}
