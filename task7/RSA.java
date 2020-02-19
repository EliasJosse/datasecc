package q7;

import java.math.BigInteger;

public class RSA {
	private BigInteger n, e, d;
	
	public RSA(String p, String q, String e) {
		n = new BigInteger(p).multiply(new BigInteger(q));
		BigInteger w = new BigInteger(p).subtract(BigInteger.ONE).multiply(new BigInteger(q).subtract(BigInteger.ONE));
		this.e = new BigInteger(e);
		d = this.e.modInverse(w);
	}
	
	public RSA(BigInteger n, BigInteger e, BigInteger d) {
		this.n = n;
		this.e = e;
		this.d = d;
	}
	
	public BigInteger encrypt(BigInteger m) { return m.modPow(e, n); }
	
	public BigInteger decrypt(BigInteger c) { return c.modPow(d, n); }

	public String toString() {
		return "PrivateKey=["+d+", "+n+"], PublicKey=["+e+", "+n+"]";
	}
	
	public static void main(String[] args) {
		RSA rsa = new RSA("61", "53", "17");
		BigInteger c = rsa.encrypt(new BigInteger("65"));
		System.out.println("enc(65) = " + c);
		System.out.println("dec(c)  = " + rsa.decrypt(c));		
	}
}
