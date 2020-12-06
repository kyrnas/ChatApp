import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Selector implements Initializable{

	Scene clientGui;
	Scene serverGui;
	ServerController serv;
	Chat chat;
	
	// if selected client
	@FXML
	public void clientAction(ActionEvent e) throws IOException {
		clientGui = createClientGui();
		Stage primaryStage = GuiServer.getPrimaryStage();
		primaryStage.setScene(clientGui);
		primaryStage.setTitle("Chat");
		
		GuiServer.createClientConnection();
	}
	
	// if selected server
	@FXML
	public void serverAction(ActionEvent e) throws IOException {
		serverGui = createServerGui();
		Stage primaryStage = GuiServer.getPrimaryStage();
		primaryStage.setScene(serverGui);
		primaryStage.setTitle("Server");
		
		GuiServer.createServerConnection();
	}
	
	// create the client scene
	public Scene createClientGui() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chat.fxml"));
			Parent root = loader.load();
			chat = loader.getController();
			return new Scene(root, 800, 800);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	// crate the server scene
	public Scene createServerGui() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server.fxml"));
			Parent root = loader.load();
			serv = loader.getController();
			return new Scene(root, 600, 500);
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
}
