package Game;

import Graphics.*;
import Sound.*;

public class Game {
	
	private RenderMaster renderMaster;
	private SoundMaster soundMaster;
	
	//a state machine belongs here
	
	public Game(){
		this.renderMaster = RenderMasterFactory.getRenderMaster();
		this.soundMaster = new SoundMaster();
	}

	
	public void execute()
	{		
		
		boolean Conti;
		int elec360power;

		Conti = true;
		elec360power = 0;

		renderMaster.loadModel("test");
		renderMaster.addModel("test");

		
		this.soundMaster.execute();
		

		//this.soundMaster.play();
		
		while(Conti && elec360power <= 9000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));
			renderMaster.draw();
			elec360power += 18;
			
			
		}
		System.out.println("CONTI'S ELEC 360 POWER LEVEL IS OVER 9000!!!!!!!!!!!");
		
		
	}
	
}
