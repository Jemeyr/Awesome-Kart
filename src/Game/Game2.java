package Game;

import States.StateContext;

public class Game2 {

	private StateContext stateContext;
	
	public Game2(){
		stateContext = new StateContext();
	}
	
	public void execute(){
		int frames = 0;
		long startTime = System.currentTimeMillis();
		while (stateContext.execute()) {
			frames++;
		}
		
		System.out.println("CONTI'S ELEC 360 POWER LEVEL IS OVER 9000!!!!!!!!!!!");
		
		float totalTime = (System.currentTimeMillis() - startTime)/1000f;
		System.out.println(totalTime + " total seconds for " + frames + " frames");
		System.out.println("fps ave: " + ((float)frames)/totalTime);
	}
}
