package Game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Controller.ControllerManager;
import Controller.EventManager;
import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundMaster;
import States.StateContext;
import World.Kart;

public class Game {
	
	private RenderMaster renderMaster;
	private SoundMaster soundMaster;
	private ControllerManager 	controllerManager;	
	private EventManager eventManager;
	private StateContext stateContext;
	//a state machine belongs here
	
	public Game(){
		//this.renderMaster = RenderMasterFactory.getRenderMaster();
		this.soundMaster = new SoundMaster();
		this.controllerManager = new ControllerManager();
		this.eventManager = new EventManager();
		this.stateContext = new StateContext();
	}

	
	public void execute()
	{		
		
		boolean Conti;
		int elec360power;

		Conti = true;
		elec360power = 0;

		renderMaster.loadModel("test");
		renderMaster.loadModel("testTer");
		renderMaster.loadModel("kart");
		renderMaster.loadModel("hat");
		renderMaster.loadModel("wheel");
		
		renderMaster.addModel("testTer");
		
		DebugGraphicsComponent kart = (DebugGraphicsComponent)renderMaster.addModel("kart");
		kart.setPosition(new Vector3f(5,0,0));
		DebugGraphicsComponent rider = (DebugGraphicsComponent) kart.addSubComponent("test", renderMaster);
		rider.setPosition(new Vector3f(0,4,0));
		DebugGraphicsComponent hat = (DebugGraphicsComponent) kart.addSubComponent("hat", renderMaster);
		hat.setPosition(new Vector3f(0,7,0));
		
		for(int i = 0;  i < 4; i++)
		{
			DebugGraphicsComponent temp = (DebugGraphicsComponent) kart.addSubComponent("wheel", renderMaster);
			temp.setPosition(new Vector3f(-4 + 3 * i,0.8f,0));
		}
		
		
		Camera cam = ((DebugRenderMaster)renderMaster).getCamera();
		
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		
		this.soundMaster.execute();
		
		
		//this.soundMaster.play();
		
		while(Conti && elec360power <= 9000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));
			controllerManager.poll();
			eventManager.handleEvents(controllerManager.getEvents(), stateContext);

			elec360power += 1;
			//((DebugRenderMaster)renderMaster).cam.setFOV(10 + elec360power * (80f/9000f));
			triforce.setRotation(new Vector3f(3.14f * (elec360power/1500f),-3.14f * (elec360power/1500f), 3.14f * (elec360power/1500f)));
			triforce.setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
			
			kart.setRotation(new Vector3f(0,0, 4*3.14f * (elec360power/(1 + Math.abs(4500f - elec360power)))));
			rider.setRotation(new Vector3f(0, 4*3.14f * (elec360power/(1 + Math.abs(4500f - elec360power))),0));
			
			cam.setPosition(new Vector3f(30f*(float)Math.sin(elec360power/600f),10f,30f*(float)Math.cos(elec360power/600f)));
			
			
			renderMaster.draw();
			
			if(Display.isCloseRequested())
			{
				elec360power = 9001;
			}

		}
		System.out.println("CONTI'S ELEC 360 POWER LEVEL IS OVER 9000!!!!!!!!!!!");
		
		
	}
	
}
