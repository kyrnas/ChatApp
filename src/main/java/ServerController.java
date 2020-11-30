
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ServerController implements Initializable{
	@FXML
	ListView<String> log = null;
	Server serverConnection;
	
	/*@FXML
	void initialize() {
		log = new ListView<String>();
		GuiServer.createServerConnection(log);
	}*/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		log = new ListView<String>();
		GuiServer.createServerConnection(log);
		
	}
}
