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
	
	String name; // client name
	
	private Consumer<Serializable> callback; // used add the messages to the chat list
	private Consumer<Serializable> clientList; // used to set the client list to this array list
	
	Client(Consumer<Serializable> call, Consumer<Serializable> clie){
		callback = call;
		clientList = clie;
	}
	
	public void run() {
		
		try {
			// create the socket and connection
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
			name = in.readObject().toString(); // initialize clients name
		}catch(Exception e) {}
		
		
		while(true) {
			try {
				MessageData data = (MessageData) in.readObject();
				String message = data.text;
				if(message.length() == 0) { // if there was no message that means the server is sending the updated client list
					// this will find the current user in the list and add a "(You)" to it's representation
					// it is used for showing the user what user they are
					for(int i = 0; i < data.recipients.size(); i++) {
						if(data.recipients.get(i).equals(name)) {
							data.recipients.set(i, name + " (You)");
							break;
						}
					}
					synchronized(clientList) {
						clientList.accept(data.recipients); // set the client list
					}
				}
				else { // otherwise a message was recieved
					synchronized(callback) {
						callback.accept(message); // add the message to the list
					}
				}
			}
			catch(Exception e) {System.out.println("Connection to the server lost"); System.exit(0);}
		}
	
    }
	
	
	public synchronized void send(String data, ArrayList<String> recipients) {
		MessageData message = new MessageData(recipients, data); // create a new message
		try {
			out.writeObject(message); // send the message
			out.reset(); // make sure to reset for proper work
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
