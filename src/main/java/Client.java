import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	private Consumer<Serializable> clientList;
	
	Client(Consumer<Serializable> call/*, Consumer<Serializable> clie*/){
		callback = call;
		//clientList = clie;
	}
	
	public void run() {
		
		try {
		socketClient = new Socket("127.0.0.1",5555);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
	    System.out.println("Created connection");
		}
		catch(Exception e) { 
		System.out.println("Could not connect to the server");
		System.out.println("Exception e caught ");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("connection failure");
		alert.setHeaderText("Exception e caught,");
		alert.setContentText("client didn't connect");

		alert.showAndWait();
		return;}//added return 11/30/2020 will devolop full safe stop method 
		
		
		
		
// exactly what is freezing the application has 
		//been commentented out below to be worked on
		
		
//		while(true) {
//			 
//			try {
//			MessageData data = (MessageData) in.readObject();
//			String message = data.text;
//			if(message.length() == 0) {
//				//ObservableList<String> obs = FXCollections.observableList(data.recipients);
//				//clientList.accept(data.recipients);
//			}
//			else {
//				callback.accept(message);
//			}
//			}
//			catch(Exception e) {}
//		}
	
    }
	
	public void send(String data, ArrayList<String> recipients) {
		MessageData message = new MessageData(recipients, data);
		try {
			out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
