
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Chat implements Initializable{
	@FXML
	ListView<String> chatList;
	@FXML
	ListView<String> userList;
	@FXML
	TextField userText;
	ArrayList<String> recipients = new ArrayList<>();
	Client clientConnection;
	
	/*@FXML
	public void initialize() {
		chatList = new ListView<>();
		userList = new ListView<>();
		GuiServer.createClientConnection(chatList);
	}*/
	
	@FXML
	public void send(ActionEvent event) {
		GuiServer.clientConnection.send(userText.getText(), recipients);
		recipients.clear();
		userText.clear();
	}
	@FXML
	public void keyReleased(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {
			send(null);
		}
	}
	
	public ListView<String> getChatList() {
		return chatList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatList = new ListView<>();
		userList = new ListView<>();
		GuiServer.createClientConnection(chatList);
		
	}
}
