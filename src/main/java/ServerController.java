
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ServerController implements Initializable{
	@FXML
	ListView<String> log;
	Server serverConnection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
