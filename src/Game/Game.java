package Game;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Controller.ControllerManager;
import Controller.EventManager;
import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.GraphicsComponent;
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
		this.renderMaster = RenderMasterFactory.getRenderMaster();
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
		renderMaster.loadModel("aktext");
		
		renderMaster.addModel("testTer");
		GraphicsComponent text = renderMaster.addModel("aktext");
		text.setPosition(new Vector3f(-200, 40, 100));
		
		List<Kart> karts = new LinkedList<Kart>();
		Kart pk = null;
		
		for(int i = 0; i < 16; i++)
		{
			
			Kart k = new Kart(null, renderMaster);
			
			if(pk == null && i == 10)
			{
				pk = k;
			}
			
			k.killmeVec = new Vector3f(-300f + (i/4) * 150.0f, 0, -300f + (i%4) * 150.0f);
			karts.add(k);
			k.killme = i*1234f;
		}
		
		Camera cam = ((DebugRenderMaster)renderMaster).getCamera();
		
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		
		this.soundMaster.execute();
		int musicCode = this.soundMaster.addSound("assets/sound/ACiv Battle 2.wav", false);
		
		long startTime = System.currentTimeMillis();

		int frames = 0;

		this.soundMaster.playSound(musicCode);
		
		//this.soundMaster.play();
		cam.setPosition(new Vector3f(-50,40,-30));
		while(Conti && elec360power <= 9000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));

			controllerManager.poll();
			eventManager.handleEvents(controllerManager.getEvents(), stateContext, null);
			
			frames++;

			elec360power += 1;
			
			
			//runs the do donuts on each kart
			for(Kart k : karts)
			{ 
				k.killmenow(elec360power);
			}


			
			
			//update the objects
			//triforce that rotates and flies away
			triforce.setRotation(new Vector3f(3.14f * (elec360power/1500f),-3.14f * (elec360power/1500f), 3.14f * (elec360power/1500f)));
			triforce.setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
			
			//rotating text
			text.setRotation(new Vector3f(0,elec360power/1500f, 0));
			
			//set the camera position
			float latOffset = 10f;
			Vector4f campos = new Vector4f(latOffset,8,-50, 1);	//12.5 is the lateral offset of the kart, 8 height, 50 behind
			Vector4f targ = new Vector4f(latOffset,1,0.00005f,1);		//divide by 2, I don't know why, height one for overhead.
			
			
			//System.out.println("campos " + campos + "\ntarg" + targ);
			
			Matrix4f modelInv = ((DebugGraphicsComponent)pk.graphicsComponent).getInvModelMat();
			Matrix4f.transform(modelInv, campos, campos);
			modelInv = ((DebugGraphicsComponent)pk.graphicsComponent).getInvModelMat();
			Matrix4f.transform(modelInv, targ, targ);
			/*
			System.out.println("	campos " + campos + "\n\ttarg" + targ);
			System.out.println("------------------------------------------------------------");
			System.out.println(modelInv);
			System.out.println("------------------------------------------------------------");
			*/
			
			
			cam.setPosition(new Vector3f(campos.x, campos.y, campos.z));
			cam.setTarget(new Vector3f(targ.x, targ.y, targ.z));
			
			//draw
			renderMaster.draw();
			
			if(Display.isCloseRequested())
			{
				
				elec360power = 9001;
			}

		}
		this.soundMaster.cleanUpALData();
		System.out.println("CONTI'S ELEC 360 POWER LEVEL IS OVER 9000!!!!!!!!!!!");
		
		float totalTime = (System.currentTimeMillis() - startTime)/1000f;
		System.out.println(totalTime + " total seconds for " + frames + " frames");
		System.out.println("fps ave: " + ((float)frames)/totalTime);
	}
	
}
