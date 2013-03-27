package Game;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import Controller.ControllerManager;
import Controller.ControllerType;
import Controller.EventManager;
import Controller.GameController;
import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundEmitter;
import Sound.SoundMaster;
import States.StateContext;
import World.Kart;
import World.Player;

public class Game {
	
	private RenderMaster renderMaster;
	private SoundMaster soundMaster;
	private ControllerManager 	controllerManager;	
	private EventManager eventManager;
	private StateContext stateContext;
	private DigitalRightsManagement drm;
	
	public Game(){
		this.renderMaster = RenderMasterFactory.getRenderMaster();
		this.soundMaster = new SoundMaster();
		this.controllerManager = new ControllerManager();
		this.eventManager = new EventManager();
		//this.stateContext = new StateContext();
		this.drm = new DigitalRightsManagement();
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
		Player player = null;
		Vector4f playerDelta = new Vector4f();
		
		for(int i = 0; i < 16; i++)
		{
			
			Kart k;
			
			if(pk == null && i == 10)
			{
				GameController gc = controllerManager.addController(ControllerType.KEYBOARD);
				k = new Kart(renderMaster);
				pk = k;
				
				player = new Player(gc, pk, playerDelta);

				Vector3f.add(pk.position, new Vector3f(0f,-22.5f, 0f), pk.position);
			}
			
			
			k  = new Kart(renderMaster);
			k.killmeVec = new Vector3f(-300f + (i/4) * 150.0f, -22.5f, -300f + (i%4) * 150.0f);
			karts.add(k);
			k.killme = i*1234f;
		}
		
		Camera cam = ((DebugRenderMaster)renderMaster).addView(new Rectangle(0,300,800,300));
		Camera cam2 =((DebugRenderMaster)renderMaster).addView(new Rectangle(0,0,800,300));
 
		
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		
		
		
		//
		SoundEmitter pianoComponent = this.soundMaster.getSoundComponent("assets/sound/piano2.wav",true);
		SoundEmitter pewComponent = this.soundMaster.getSoundComponent("assets/sound/Pew_Pew.wav",true);
		SoundEmitter alarmComponent = this.soundMaster.getSoundComponent("assets/sound/alarma.wav",true);
		//pianoComponent.playSound();
		alarmComponent.playSound();
		//pewComponent.playSound();
		long startTime = System.currentTimeMillis();

		int frames = 0;

		//this.soundMaster.play();
		cam.setPosition(new Vector3f(-50,40,-30));
		while(Conti && elec360power <= 9000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));
			/*if(!drm.isValid()){
				System.exit(7);
			}*/
			
			controllerManager.poll();
			//eventManager.handleEvents(controllerManager.getEvents(), stateContext, renderMaster);
			alarmComponent.setSoundPosition( 0, elec360power, 0);
			
			//soundMaster.setListenerPosition(elec360power, 0, 0);
			frames++;

			elec360power += 1;
			if(elec360power > 100){
				
				pewComponent.setSoundPosition(elec360power*5, 0, 0);
			}

			
			//runs the do donuts on each kart
			for(Kart k : karts)
			{ 
				if(k != pk)
				{
					k.killmenow(elec360power);
				}
			}

			
			player.update();

			
			//update the objects
			//triforce that rotates and flies away
			triforce.setRotation(new Vector3f(3.14f * (elec360power/1500f),-3.14f * (elec360power/1500f), 3.14f * (elec360power/1500f)));
			triforce.setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
			
			//rotating text
			text.setRotation(new Vector3f(0,elec360power/1500f, 0));
			
			//set the camera position
			Vector4f campos = new Vector4f(0,35,-50, 1);	//12.5 is the lateral offset of the kart, 8 height, 50 behind
			Vector4f targ = new Vector4f(0,1,0,1);		//divide by 2, I don't know why, height one for overhead.
			Vector4f campos2 = new Vector4f(0,35, 50,1);
			
			//System.out.println("campos " + campos + "\ntarg" + targ);
			
			Matrix4f modelInv = ((DebugGraphicsComponent)pk.graphicsComponent).getInvModelMat();
			Matrix4f.transform(modelInv, campos, campos);
			modelInv = ((DebugGraphicsComponent)pk.graphicsComponent).getInvModelMat();
			Matrix4f.transform(modelInv, targ, targ);
			

			modelInv = ((DebugGraphicsComponent)pk.graphicsComponent).getInvModelMat();
			Matrix4f.transform(modelInv, campos2, campos2);
			
			
			
			
			cam.setPosition(new Vector3f(campos.x, campos.y, campos.z));
			cam.setTarget(new Vector3f(targ.x, targ.y, targ.z));
			
			cam2.setPosition(new Vector3f(campos2.x, campos2.y, campos2.z));
			cam2.setTarget(new Vector3f(targ.x, targ.y, targ.z));
			
			/*
			System.out.println("	campos " + campos + "\n\ttarg" + targ);
			System.out.println("------------------------------------------------------------");
			System.out.println(modelInv);
			System.out.println("------------------------------------------------------------");
			*/
			
			
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
