package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DigitalRightsManagement {
	
	private static final String PING_COMMAND 	= "ping";
	private static final String PING_1_ARG		= "-c";
	private static final String PING_1_VAL		= "1";
	private static final String PING_1_ADDR		= "74.125.236.73";
	private static final String EXPECTED_OUT	= "1 packets transmitted, 1 received";

	List<String> pingCommands;
	
	public DigitalRightsManagement()  { 
		pingCommands = new ArrayList<String>();
		pingCommands.add(PING_COMMAND);
		pingCommands.add(PING_1_ARG);
		pingCommands.add(PING_1_VAL	);
		pingCommands.add(PING_1_ADDR);
	}
	
	public boolean isValid() {
		try {
			ProcessBuilder pb = new ProcessBuilder(pingCommands);
		    Process process = pb.start();
	
		    String pingLine = null;
		    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    while ((pingLine = stdInput.readLine()) != null) {
		      if(pingLine.contains(EXPECTED_OUT)) {
		    	  return true;
		      }
		    }
		} catch (IOException ioe){
			return false;
		}
	    return false;
	}
}
