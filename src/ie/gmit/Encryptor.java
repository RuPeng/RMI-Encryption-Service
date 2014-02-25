package ie.gmit;

/* Encrytor also is a remote interface.
 * It include encryptFile() and decryptFile() methods 
 */

import java.rmi.Remote;

public interface Encryptor extends Remote {
	public void encryptFile(String srcFile, String destFile, String key)
			throws Exception;

	public void decryptFile(String srcFile, String destFile, String key)
			throws Exception;
}
