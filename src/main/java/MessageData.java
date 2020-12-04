import java.io.Serializable;
import java.util.ArrayList;

public class MessageData implements Serializable {

	private static final long serialVersionUID = 4748665020781195022L;	
	public ArrayList<String> recipients;
	public String text;
	
	MessageData(ArrayList<String> people, String message){
		recipients = people;
		text = message;
	}

	public String toString() {
		return "Message: " + text + " recipients: " + recipients.toString(); 
	}
}
