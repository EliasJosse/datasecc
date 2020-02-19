package q7;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.crypto.*;

public class Question7 {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		
		String msg = "Hello from the other side";
		System.out.println("Original Message");
		System.out.println(msg);
		
		byte[] msgbyte = msg.getBytes();
		int length = msgbyte.length;
		
		//KEY generation and encryption using JCE
		
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024);
		
		KeyPair pair = gen.generateKeyPair();
		
		RSAPublicKey pubKey = (RSAPublicKey) pair.getPublic();
		RSAPrivateKey privKey = (RSAPrivateKey) pair.getPrivate();
		
		Cipher cip = Cipher.getInstance("RSA");
		
		cip.init(Cipher.ENCRYPT_MODE, pubKey);
		cip.update(msgbyte);
		
		byte[] enc = cip.doFinal();
		System.out.println("\n\nEncrypted");
		System.out.println(new String(enc, "UTF8"));
		
		
		
		
		
		//Decrypting using RSA biginteger class
		
		BigInteger n = pubKey.getModulus();
		BigInteger e = pubKey.getPublicExponent();
		BigInteger d = privKey.getPrivateExponent();
		
		RSA rsa = new RSA(n, e, d);
		
		byte[] dec = rsa.decrypt(new BigInteger(enc)).toByteArray();
		
		
		dec = Arrays.copyOfRange(dec, dec.length-length, dec.length);
		
		System.out.println("\n\nDecrypted ");
		System.out.println(new String(dec));
	}

}
