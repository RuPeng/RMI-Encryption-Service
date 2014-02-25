package ie.gmit;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

import javax.servlet.*;
import javax.servlet.http.*;

public class EncryptionServiceHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Set the MIME type of our HTTP response
		resp.setContentType("text/html");
		// Use a PrintWriter to build the output to the client browser
		PrintWriter out = resp.getWriter();

		// Get the form information
		String operation = req.getParameter("cmbOperation");
		String soufile = req.getParameter("sourcefile");
		String key = req.getParameter("key");
		if (key == null) {
			// Default key
			key = "Ru";
		}
		String tarfile = req.getParameter("path");
		if (operation == null) {
			out.print("<H1>Please start from Home Page:</H1>");
			out.print("<a href=\"http://localhost:8080/ds/\">HomePage</a>");
		} else {
			// Test that the form information is okay...
			out.print("Operation: " + operation + "<br/>");
			out.print("soufile: " + soufile + "<br/>");
			out.print("tarfile: " + tarfile + "<br/>");
			out.print("key: " + key + "<br/>");
			// Do something with the information...like add to a queue...?
			Queue<UserReq> inQue = (Queue<UserReq>) getServletContext()
					.getAttribute("inQue");
			UserReq ur = new UserReq();
			// ur.setID(id);
			String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS")
					.format(new Date());
			ur.setID(dateStr);
			ur.setKey(key);
			ur.setSoufile(soufile);
			ur.setTarfile(tarfile);
			ur.setOperation(operation);
			inQue.add(ur);
			// Return some HTML back to the client that periodically polls a
			// servlet
			// to check if the request is processed.
			out.print("<a href=\"http://localhost:8080/ds/bootstrap?id="
					+ dateStr + "\">Check Result</a>");

		}
	}

	// If anyone issues a POST request, dispatch the request and response
	// objects to the GET method
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
