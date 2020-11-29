import java.io.Serializable;
import java.util.ArrayList;

public class MessageData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8342665851206839707L;
	
	public ArrayList<String> recipients;
	public String text;
	
	MessageData(ArrayList<String> people, String message){
		recipients = people;
		text = message;
	}

}
