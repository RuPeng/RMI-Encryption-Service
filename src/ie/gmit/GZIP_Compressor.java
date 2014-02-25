package ie.gmit;

/* GZIP_Compressor is a class that implements the 
 * remote interface Compressor.
 * It implements the compressFile() and decompressFlie() methods.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP_Compressor extends UnicastRemoteObject implements Compressor {

	private static final long serialVersionUID = 1L;

	protected GZIP_Compressor() throws RemoteException {
		super();
	}

	@Override
	public void compressFile(String source, String target) throws Exception {

		// getting file input stream from source file
		FileInputStream fin = new FileInputStream(source);
		// creates tempFile to get source file name
		File tempFile = new File(source);
		// creates saveDir to get target file path
		File saveDir = new File(target);
		// if user's input is not a directory, creates the directory by the
		// user's input
		if (!saveDir.isDirectory()) {
			saveDir.mkdir();
		}
		// getting file output stream from target file,add the target file's
		// extension as .gz
		FileOutputStream fout = new FileOutputStream(saveDir.getAbsolutePath()
				+ "/" + tempFile.getName() + ".gz");
		// getting compressed output stream
		GZIPOutputStream gzout = new GZIPOutputStream(fout);
		byte[] buf = new byte[1024];// set buffer size for data reading
		// while haven't finishing reading, writing data into buffer
		int num;
		while ((num = fin.read(buf)) != -1) {
			gzout.write(buf, 0, num);
		}
		gzout.close(); // close compressed output stream
		fout.close(); // close file output stream
		fin.close(); // close file input stream

	}

	@Override
	public void decompressFile(String source, String target) throws Exception {

		// if the extension of source file equals to .gz, do decompress
		// process
		if (!source.substring(source.lastIndexOf(".")).equalsIgnoreCase(".gz")) {
			return;
		}
		// getting file input stream from source file
		FileInputStream fin = new FileInputStream(source);
		// getting compressed input stream
		GZIPInputStream gzin = new GZIPInputStream(fin);
		// creates tempFile to get source file name
		File tempFile = new File(source);
		// creates saveDir to get target file path
		File saveDir = new File(target);
		// if user's input is not a directory, creates the directory by the
		// user's input
		if (!saveDir.isDirectory()) {
			saveDir.mkdir();
		}
		// getting file output stream from target file, remove the extension
		// .gz of the file
		FileOutputStream fout = new FileOutputStream(saveDir.getAbsolutePath()
				+ "/"
				+ tempFile.getName().substring(0,
						tempFile.getName().lastIndexOf(".")));
		byte[] buf = new byte[1024]; // buffer size
		// if haven't finish reading,
		// writing data into buffer and getting output stream
		int num;
		while ((num = gzin.read(buf, 0, buf.length)) != -1) {
			fout.write(buf, 0, num);
		}
		gzin.close(); // close compressed input stream
		fout.close(); // close file output stream
		fin.close(); // close file input stream

	}
}
