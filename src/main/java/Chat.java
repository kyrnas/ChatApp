
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
import javafx.scene.input.MouseEvent;

public class Chat implements Initializable{
	@FXML
	ListView<String> chatList; // for showing messages
	@FXML
	ListView<String> userList; // for showing client list
	@FXML
	ListView<String> recipientsView; // for showing selected recipients
	@FXML
	TextField userText;
	// selected recipients
	ArrayList<String> recipients = new ArrayList<>();
	
	@FXML
	public void send(ActionEvent event) {
		GuiServer.clientConnection.send(userText.getText(), recipients);
		userText.clear();
		recipients.clear();
		updateRecipientsList();
	}
	
	// for sending messages with ENTER
	@FXML
	public void keyReleased(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {
			send(null);
		}
	}
	
	public ListView<String> getChatList() {
		return chatList;
	}
	
	// event handler for clicking on the client list (adding recipients)
	@FXML 
	public void handleMouseClick(MouseEvent event) {
		String selected = userList.getSelectionModel().getSelectedItem();
		if(!recipients.contains(selected)) {
			recipients.add(selected);
			updateRecipientsList();
		}
	}
	
	// event handler for clicking on the recipients list (removing recipients)
	@FXML
	public void mouseRecipients(MouseEvent event) {
		String selected = recipientsView.getSelectionModel().getSelectedItem();
		recipients.remove(selected);
		updateRecipientsList();
	}
	
	// method for updating the ListView from the arraylist recipients
	public void updateRecipientsList() {
		ObservableList<String> obs = FXCollections.observableList((List<String>) recipients);
		recipientsView.setItems(obs);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatList.getItems().add("Welcome. For sending messages to specific user click on them in the client list.");
	}
}
