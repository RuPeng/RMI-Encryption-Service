package ie.gmit;

/* UserReq is a class that get the request
 * from client, in order to sent the request
 * to Web Server.
 */
public class UserReq {
	private String ID;
	private String operation;
	private String soufile;
	private String tarfile;
	private String key;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSoufile() {
		return soufile;
	}

	public void setSoufile(String soufile) {
		this.soufile = soufile;
	}

	public String getTarfile() {
		return tarfile;
	}

	public void setTarfile(String tarfile) {
		this.tarfile = tarfile;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
