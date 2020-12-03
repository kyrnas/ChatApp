import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	
	
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	void close() {
		for(ClientThread cur : clients) {
			cur.interrupt();
		}
		server.interrupt();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		    callback.accept("Server started");
		  
			
		    while(true) {
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				
				count++;
				//c.updateClientList();
			    }
			}//end of try
				catch(Exception e) {
					e.printStackTrace();
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public synchronized void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					MessageData data = new MessageData(null, message);
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(data);
					}
					catch(Exception e) {}
				}
			}
			
			public synchronized void updateClients(String message, ArrayList<String> recipients) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					if(recipients.contains(Integer.toString(t.count))) {
						try {
						 t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
				}
			}
			
			public synchronized void updateClientList() {
				ArrayList<String> clientList = new ArrayList<>();
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					clientList.add(Integer.toString(t.count));
				}
				MessageData result = new MessageData(clientList, "");
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
						 t.out.writeObject(result);
					}
					catch(Exception e) {}
				}
			}
			
			@Override
			public void run(){	
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				updateClients("new client on server: client #"+count);
				updateClientList();
					
				 while(true) {
					    try {
					    	MessageData message = (MessageData) in.readObject();
					    	
					    	String data = message.text;
					    	if(message.recipients.size() == 1) {
					    		callback.accept("client: " + count + " sent a private message to " + message.recipients.toString() + ": " + data);
					    		updateClients("client #"+count+" said privately: " + data, message.recipients);
					    	}
					    	else if(message.recipients.size() == 0) {
					    		callback.accept("client: " + count + " sent a message to everyone: " + data);
					    		updateClients("client #"+count+" said to everyone: " + data);
					    	}
					    	else {
					    		callback.accept("client: " + count + " sent a group private message to " + message.recipients.toString() + ": " + data);
					    		updateClients("client #"+count+" said to a group of people: " + data, message.recipients);
					    	}
					    	
					    	}
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	clients.remove(this);
					    	updateClients("Client #"+count+" has left the server!");
					    	updateClientList();
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
