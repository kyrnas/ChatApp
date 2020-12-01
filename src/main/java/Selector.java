import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Selector implements Initializable{

	Scene clientGui;
	Scene serverGui;
	Client clientConnection;
	Server serverConnection;
	ServerController serv;
	Chat chat;
	ListView<String> listItems, listItems2;
	
	@FXML
	public void clientAction(ActionEvent e) throws IOException {
		clientGui = createClientGui();
		Stage primaryStage = GuiServer.getPrimaryStage();
		primaryStage.setScene(clientGui);
		primaryStage.setTitle("Chat");
		
		GuiServer.createClientConnection();

	}
	
	@FXML
	public void serverAction(ActionEvent e) throws IOException {
		serverGui = createServerGui();
		Stage primaryStage = GuiServer.getPrimaryStage();
		primaryStage.setScene(serverGui);
		primaryStage.setTitle("Server");
		
		GuiServer.createServerConnection();
	}
	
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
