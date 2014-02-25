package ie.gmit;

import java.io.*;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.*;
import javax.servlet.http.*;

public class EncryptionServiceBootstrap extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Queue<UserReq> inQue;
	private Map<String, String> outQue;
	private Thread t;
	private Service service;

	// Because <load-on-startup> is set to 1, this init() method will be
	// executed when Tomcat is started
	public void init() throws ServletException {
		// ServletContext represents the application context for the web
		// application
		ServletContext ctx = getServletContext();
		try {
			service = (Service) Naming.lookup("rmi://localhost:1099/Server");
			t = new Thread(new Checker());
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inQue = new ConcurrentLinkedQueue<UserReq>();
		outQue = new HashMap<String, String>();

		// Add them to the ServletContext. They are now available from ANY
		// JSP/Servlet in the web application
		ctx.setAttribute("inQue", inQue);
		ctx.setAttribute("outQue", outQue);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Set the MIME type of our HTTP response
		resp.setContentType("text/html");
		// Use a PrintWriter to build the output to the client browser
		PrintWriter out = resp.getWriter();
		// Read in the value associated with a query string, e.g.
		// http://localhost:8080/ds/
		String detail = req.getParameter("id");
		if (outQue.containsKey(detail)) {
			out.print("<H1>" + outQue.get(detail) + "</H1>");
		} else {
			out.print("<H1>Please Wait...<H1>");
		}
	}

	// If anyone issues a POST request, dispatch the request and response
	// objects to the GET method
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	class Checker implements Runnable {

		@Override
		public void run() {
			while (true) {
				if (!inQue.isEmpty()) {
					UserReq ur = inQue.poll();
					String operation = ur.getOperation().trim();
					String soufile = ur.getSoufile().trim();
					String tarfile = ur.getTarfile().trim();
					String key = ur.getKey().trim();
					switch (operation) {
					default:
						try {
							service.encryptFile(soufile, tarfile, key);
							outQue.put(ur.getID(), "File Encryption Succeed!");
						} catch (Exception e) {
							outQue.put(ur.getID(), "File Encryption Failed!"
									+ e.getMessage() + "<br/>");
						}
						break;
					case "Decrypt":
						try {
							service.decryptFile(soufile, tarfile, key);
							outQue.put(ur.getID(), "File Decryption Succeed!");
						} catch (Exception e) {
							outQue.put(ur.getID(), "File Decryption Failed!"
									+ e.getMessage() + "<br/>");
						}
						break;
					case "Compress":
						try {
							service.compressFile(soufile, tarfile);
							outQue.put(ur.getID(), "File Compression Succeed!");
						} catch (Exception e) {
							outQue.put(ur.getID(), "File Compression Failed!"
									+ e.getMessage() + "<br/>");
						}
						break;
					case "Decompress":
						try {
							service.decompressFile(soufile, tarfile);
							outQue.put(ur.getID(),
									"File Decompression Succeed!");
						} catch (Exception e) {
							outQue.put(ur.getID(), "File Decompression Failed!"
									+ e.getMessage() + "<br/>");
						}
						break;
					case "EncryptAndCompress":
						try {
							service.encryptFile(soufile, tarfile, key);
							File tempFile = new File(soufile);
							String newSou = tarfile + tempFile.getName()
									+ ".aes";
							service.compressFile(newSou, tarfile);
							outQue.put(ur.getID(),
									"Encryption and Compression Succeed!");
						} catch (Exception e) {
							outQue.put(
									ur.getID(),
									"Encryption and Compression Failed!"
											+ e.getMessage() + "<br/>");
						}
						break;
					case "DecompressAndDecrypt":
						try {
							service.decompressFile(soufile, tarfile);
							File tempFile = new File(soufile);
							String newSou = tarfile
									+ tempFile.getName()
											.substring(
													0,
													tempFile.getName()
															.lastIndexOf("."));
							service.decryptFile(newSou, tarfile, key);
							outQue.put(ur.getID(),
									"Decompression and Decryption Succeed!");
						} catch (Exception e) {
							outQue.put(
									ur.getID(),
									"Decompression and Decryption Failed!"
											+ e.getMessage() + "<br/>");
						}
						break;
					}
				}
			}

		}

	}
}
