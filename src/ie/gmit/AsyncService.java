package ie.gmit;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class AsyncService {
	public static void main(String[] args) {
		try {
			// create a name for the object
			String name = "Server";
			// create a instance of Service
			Service service = new ServiceImpl();
			// Start the RMI registry on port 1099
			LocateRegistry.createRegistry(1099);
			// Bind the remote object to the registry with the human-readable
			// name "Server"
			Naming.rebind(name, service);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
