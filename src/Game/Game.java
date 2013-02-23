package Game;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Controller.ControllerManager;
import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.QuaternionHelper;
import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundMaster;

public class Game {
	
	private RenderMaster renderMaster;
	private SoundMaster soundMaster;
	private ControllerManager 	controllerManager;
	
	//a state machine belongs here
	
	public Game(){
		this.renderMaster = RenderMasterFactory.getRenderMaster();
		this.soundMaster = new SoundMaster();
		this.controllerManager = new ControllerManager();
	}

	
	public void execute()
	{		
		
		boolean Conti;
		int elec360power;

		Conti = true;
		elec360power = 0;

		renderMaster.loadModel("test");
		renderMaster.loadModel("testTer");
		
		renderMaster.addModel("testTer");
		
		Camera cam = ((DebugRenderMaster)renderMaster).getCamera();
		
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		
		this.soundMaster.execute();
		
		

		//this.soundMaster.play();
		
		while(Conti && elec360power <= 9000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));


			elec360power += 1;
			//((DebugRenderMaster)renderMaster).cam.setFOV(10 + elec360power * (80f/9000f));
			triforce.setRotation(new Vector3f(0f,-3.14f * (elec360power/1500f),0f));
			triforce.setPosition(new Vector3f(0, -3f + 6*elec360power/1500f, 0));
			
			cam.setPosition(new Vector3f(10f*(float)Math.sin(elec360power/600f),5f,10f*(float)Math.cos(elec360power/600f)));
			
			
			renderMaster.draw();
			
			controllerManager.poll();
			
			if(Display.isCloseRequested())
			{
				elec360power = 9001;
			}

		}
		System.out.println("CONTI'S ELEC 360 POWER LEVEL IS OVER 9000!!!!!!!!!!!");
		
		
	}
	
}
