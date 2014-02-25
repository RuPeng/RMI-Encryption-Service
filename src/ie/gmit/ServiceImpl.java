package ie.gmit;

/* ServiceImpl is a class that implements the remote interface Service,
 * it must implement the remote methods.
 * And the stub used by the client to communicate with the server is generated
 * from this class with the following command from a console:
 *               rmic ie.gmit.ServiceImpl  
 * This will generate a stub called ServiceImpl_Stub.class.
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceImpl extends UnicastRemoteObject implements Service {

	private Encryptor encryptor;
	private Compressor compressor;
	private static final long serialVersionUID = 1L;

	protected ServiceImpl() throws RemoteException {
		super();
		encryptor = new AES_Encryptor();
		compressor = new GZIP_Compressor();
	}

	@Override
	public void compressFile(String source, String target)
			throws Exception {
		compressor.compressFile(source, target);// delegate method

	}

	@Override
	public void decompressFile(String source, String target)
			throws Exception {
		compressor.decompressFile(source, target);// delegate method

	}

	@Override
	public void encryptFile(String srcFile, String destFile, String key)
			throws Exception {
		try {
			encryptor.encryptFile(srcFile, destFile, key);// delegate method
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void decryptFile(String srcFile, String destFile, String key)
			throws Exception {
		try {
			encryptor.decryptFile(srcFile, destFile, key);// delegate method
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
