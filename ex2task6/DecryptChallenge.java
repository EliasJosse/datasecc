
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

/*
 * A solution to https://challenges.re/50/
 * Written by Elias Josse and Amanda Nerborg
 * 
 */


public class DecryptChallenge {

	public static void main(String[] args) throws IOException {
		
		File file = new File("mfc100.dll.encrypted");  //File encrypted with XOR using 4-byte key
		File source = new File("mfc100.dll");		   //source
		File resu = new File("mfc100.dll.decprypted"); 
		FileOutputStream ostrm = new FileOutputStream(resu);
	
		byte[] filByte = Files.readAllBytes(file.toPath());
		byte[] srcfilByte = Files.readAllBytes(source.toPath());

		ByteArrayInputStream encStream = new ByteArrayInputStream(filByte);
		ByteArrayInputStream srcStream = new ByteArrayInputStream(srcfilByte);
		
		/*
		 * Compare first 4-bytes by XOR. Result is the key.
		 */
		byte[] key = new byte[4];
		
		int res = encStream.read() ^ srcStream.read();
		key[0] = (byte) res;
		
		res = encStream.read() ^ srcStream.read();
		key[1] = (byte) res;
		
		res = encStream.read() ^ srcStream.read();
		key[2] = (byte) res;
		
		res = encStream.read() ^ srcStream.read();
		key[3] = (byte) res;
		
		
		encStream.reset();
		srcStream.reset();
		
		
		// Write result in new file mfc100.dll.decprypted
		byte[] buff = new byte[4];
		while( encStream.available() > 0){
			buff[0] = (byte) ((byte) encStream.read() ^ key[0]);
			buff[1] = (byte) ((byte) encStream.read() ^ key[1]);
			buff[2] = (byte) ((byte) encStream.read() ^ key[2]);
			buff[3] = (byte) ((byte) encStream.read() ^ key[3]);
			ostrm.write(buff);
		}
		ostrm.close();
	}
}
