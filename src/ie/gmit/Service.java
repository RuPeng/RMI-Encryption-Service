package ie.gmit;

/* Service represents the remote interface.
 * the interface include four kinds of services,
 * they are compress file, decompress file, encrypt file,
 * and decrypt file.
 */
import java.rmi.*;

public interface Service extends Remote {
	public void compressFile(String source, String target) throws Exception;

	public void decompressFile(String source, String target) throws Exception;

	public void encryptFile(String srcFile, String destFile, String key)
			throws Exception;

	public void decryptFile(String srcFile, String destFile, String key)
			throws Exception;

}
