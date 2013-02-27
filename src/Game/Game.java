package Game;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Controller.ControllerManager;
import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundMaster;
import World.Kart;

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
		renderMaster.loadModel("kart");
		renderMaster.loadModel("hat");
		renderMaster.loadModel("wheel");
		
		renderMaster.addModel("testTer");
		
		List<Kart> karts = new LinkedList<Kart>();
		Kart pk = null;
		
		for(int i = 0; i < 16; i++)
		{
			
			Kart k = new Kart(null, renderMaster);
			
			if(pk == null)
			{
				pk = k;
			}
			
			k.position = new Vector3f((i/4) * 75.0f, 0, (i%4) * 75.0f);
			karts.add(k);
			k.rotation = new Vector3f(0,0,i*1234);
		}
		
		Camera cam = ((DebugRenderMaster)renderMaster).getCamera();
		
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		
		this.soundMaster.execute();
		
		
		//this.soundMaster.play();
		cam.setPosition(new Vector3f(-50,40,-30));
		while(Conti && elec360power <= 9000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));
			int i = 0;
			for(Kart k : karts)
			{
				
				k.killmenow(elec360power);
			}

			elec360power += 1;
			//((DebugRenderMaster)renderMaster).cam.setFOV(10 + elec360power * (80f/9000f));
			triforce.setRotation(new Vector3f(3.14f * (elec360power/1500f),-3.14f * (elec360power/1500f), 3.14f * (elec360power/1500f)));
			triforce.setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
			
	//		Vector3f campos = new Vector3f()
			
			cam.setPosition(new Vector3f(100f*(float)Math.sin(elec360power/600f),50f,100f*(float)Math.cos(elec360power/600f)));
			
			
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
