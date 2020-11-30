
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerController {
	@FXML
	ListView<String> log = null;
	Server serverConnection;
	
	@FXML
	void initialize() {
		log = new ListView<String>();
		GuiServer.createServerConnection(log);
	}
}
