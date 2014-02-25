package ie.gmit;

/* AES_Encryptor is a class that implements the
 * remote interface Encryptor.
 * It implements the encryptFile() and decryptFile() methods.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES_Encryptor extends UnicastRemoteObject implements Encryptor {

	private static final long serialVersionUID = 1L;

	protected AES_Encryptor() throws RemoteException {
		super();
	}

	@Override
	public void encryptFile(String srcFile, String destFile, String key)
			throws Exception {
		Cipher cipher;

		// get instance
		cipher = Cipher.getInstance("AES");
		// generate key
		SecretKeySpec secretKeySpec = generateKey(key);
		// the cipher is initialized for encryption
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		// getting file input stream from source file
		FileInputStream fis = new FileInputStream(srcFile);
		// creates tempFile to get source file name
		File tempFile = new File(srcFile);
		// creates saveDir to get destination file path
		File saveDir = new File(destFile);
		// if user's input is not a directory, creates the directory by the
		// user's input
		if (!saveDir.isDirectory()) {
			saveDir.mkdir();
		}
		// getting file output stream from target file,add the target file's
		// extension as .aes
		FileOutputStream fos = new FileOutputStream(saveDir.getAbsolutePath()
				+ "/" + tempFile.getName() + ".aes");
		System.out.println(destFile);
		byte[] b = new byte[2048];// set buffer size for data reading
		// while haven't finishing reading, writing data into buffer
		while (fis.read(b) != -1) {
			fos.write(cipher.doFinal(b));
		}
		fos.close();// close file output stream
		fis.close();// close file input stream

	}

	@Override
	public void decryptFile(String srcFile, String destFile, String key)
			throws Exception {
		Cipher cipher;
		// if the extension of source file is not equals to .aes
		if (!srcFile.substring(srcFile.lastIndexOf(".")).equalsIgnoreCase(
				".aes")) {
			System.out.println("the extension of source file is wrong!");
			return;
		}

		cipher = Cipher.getInstance("AES");
		SecretKeySpec secretKeySpec = generateKey(key);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		FileInputStream fis = new FileInputStream(srcFile);
		File tempFile = new File(srcFile);
		File saveDir = new File(destFile);
		if (!saveDir.isDirectory()) {
			saveDir.mkdir();
		}
		System.out.println(saveDir.getAbsolutePath());
		// getting file output stream from target file, remove the extension
		// .aes of the file
		FileOutputStream fos = new FileOutputStream(saveDir.getAbsolutePath()
				+ "/"
				+ tempFile.getName().substring(0,
						tempFile.getName().lastIndexOf(".")));
		byte[] b = new byte[2064];
		while (fis.read(b) != -1) {
			fos.write(cipher.doFinal(b));
		}
		fos.close();
		fis.close();

	}

	// change the String type key to Key type key
	private SecretKeySpec generateKey(String password)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			RemoteException {
		byte[] key = (password).getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		return secretKeySpec;

	}

}
