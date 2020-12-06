
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{
	Scene startScene;
	static Server serverConnection;
	static Client clientConnection;
	static Selector select;
	private static Stage pStage;
	
	ListView<String> listItems, listItems2, listItems3;
	
	
	public static Stage getPrimaryStage() {
        return pStage;
    }
	
	public void setPrimaryStage(Stage pStage) {
		GuiServer.pStage = pStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		pStage = primaryStage;
		primaryStage.setTitle("Chat application");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choice.fxml"));
		Parent root = loader.load();
		select = loader.getController();
		startScene = new Scene(root, 800,800);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		primaryStage.setScene(startScene);
		primaryStage.show();	
	}
	
	@Override 
	public void stop() {
		if(serverConnection != null)
			System.out.println("CLosing server");
			serverConnection.close();
		if(clientConnection != null)
			clientConnection.interrupt();
		Platform.exit();
		System.exit(0);
	}
	
	// this method is called from selector controller to create client connection thread
	@SuppressWarnings("unchecked")
	public static void createClientConnection() {
		clientConnection = new Client(data->{
				Platform.runLater(()->{
					select.chat.chatList.getItems().add(data.toString()); // to add messages
				});
			}, data->{
				Platform.runLater(()->{
					ObservableList<String> obs = FXCollections.observableList((List<String>) data); // create a new observable list
					select.chat.userList.setItems(obs); // set the client list to list returned by the server
				});
			});
		clientConnection.start();
	}
	
	// this method is called form the selector to create a server
	public static void createServerConnection() { 
		serverConnection = new Server(data -> {
			Platform.runLater(()->{
				select.serv.log.getItems().add(data.toString()); // add logs to the server window
			});
		});
	}

}
