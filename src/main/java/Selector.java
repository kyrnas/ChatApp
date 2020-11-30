import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Selector {

	Scene clientGui;
	Scene serverGui;
	
	
	
	@FXML
	public void clientAction(ActionEvent e) throws IOException {
		/*Stage primaryStage = GuiServer.getPrimaryStage();
		VBox clientBox;
		TextField c1;
		Button b1;
		ListView<String> listItems2;
		listItems2 = new ListView<String>();
		c1 = new TextField();
		b1 = new Button("Send");
		clientBox = new VBox(10, c1,b1,listItems2);
		clientBox.setStyle("-fx-background-color: blue");
		Scene Client =  new Scene(clientBox, 400, 300);
		primaryStage.setScene(Client);
		primaryStage.setTitle("This is a client");
		//clientConnection = new Client(data->{
			//Platform.runLater(()->{listItems2.getItems().add(data.toString());*/
		//System.out.println("client called");
		
		clientGui = createClientGui();
		Stage primaryStage = GuiServer.getPrimaryStage();
		primaryStage.setScene(clientGui);
		primaryStage.setTitle("Chat");

	}
	
	@FXML
	public void serverAction(ActionEvent e) throws IOException {
		serverGui = createServerGui();
		Stage primaryStage = GuiServer.getPrimaryStage();
		primaryStage.setScene(serverGui);
		primaryStage.setTitle("Server");
	}
	
	public Scene createClientGui() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/chat.fxml"));
			return new Scene(root, 800, 800);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	public Scene createServerGui() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/server.fxml"));
			return new Scene(root, 610, 500);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
}
