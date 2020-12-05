import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;




public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	String name;
	
	private Consumer<Serializable> callback;
	private Consumer<Serializable> clientList;
	
	Client(Consumer<Serializable> call, Consumer<Serializable> clie){
		callback = call;
		clientList = clie;
	}
	
	public void run() {
		
		try {
			socketClient = new Socket("127.0.0.1",5555);
		    out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) { 
			System.out.println("Could not connect to the server. Make sure it is running.");
			System.exit(1);
			return;
		}
		
		try {
			name = in.readObject().toString();
		}catch(Exception e) {}
		
		
		while(true) {
			try {
				MessageData data = (MessageData) in.readObject();
				String message = data.text;
				if(message.length() == 0) {
					for(int i = 0; i < data.recipients.size(); i++) {
						if(data.recipients.get(i).equals(name)) {
							data.recipients.set(i, name + " (You)");
							break;
						}
					}
					synchronized(clientList) {
						clientList.accept(data.recipients);
					}
				}
				else {
					synchronized(callback) {
						callback.accept(message);
					}
				}
			}
			catch(Exception e) {System.out.println("Connection to the server lost"); System.exit(0);}
		}
	
    }
	
	
	public synchronized void send(String data, ArrayList<String> recipients) {
		MessageData message = new MessageData(recipients, data);
		try {
			out.writeObject(message);
			out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
