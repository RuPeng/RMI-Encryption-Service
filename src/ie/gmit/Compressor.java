package ie.gmit;

/* Compressor also is a remote interface.
 * It include compress file and decompress file methods.
 */
import java.rmi.Remote;

public interface Compressor extends Remote {
	public void compressFile(String source, String target)
			throws Exception;

	public void decompressFile(String source, String target)
			throws Exception;

}
