package Game;


import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

import java.util.ArrayList;

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
import Graphics.Light;
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
		renderMaster.loadModel("rocket");
		
		renderMaster.loadModel("aktext");
		
		renderMaster.loadModel("lightSphere");
		
		System.out.println("Loading Complete");

		Light le = null;
		for(int h = 0; h < 40; h++)
		{
			le = renderMaster.addLight();
			le.setRad(40.0f);
			le.setPosition(new Vector3f(200 - 15 * (h % 15), -10, 200 - 15 * h/15));
		}
		

		le = renderMaster.addLight();
		le.setRad(150.0f);
		le.setPosition(new Vector3f(50,-20,0));
		le.setColor(new Vector3f(1.0f, 0.0f, 0.0f));
		
		le = renderMaster.addLight();
		le.setRad(150.0f);
		le.setPosition(new Vector3f(-30,-20,0));
		le.setColor(new Vector3f(0.0f, 0.0f, 1.0f));
		
		
		
		
		
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

		Camera cam = ((DebugRenderMaster)renderMaster).addView(new Rectangle(0,300, 800, 300));//0,300,800,300));
		Camera cam2 =((DebugRenderMaster)renderMaster).addView(new Rectangle(0,0,800,300));
		//Camera cam2 = new Camera(new Vector3f(1,2,3),new Vector3f(3,4,5), 1, 2);
		
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("rocket");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		
		//
		SoundEmitter pianoComponent = this.soundMaster.getSoundComponent("assets/sound/piano2.wav",true);
		SoundEmitter pewComponent = this.soundMaster.getSoundComponent("assets/sound/ACiv Battle 2.wav",true);
		pianoComponent.playSound();
		
		long startTime = System.currentTimeMillis();

		int frames = 0;


		cam.setPosition(new Vector3f(-50,40,-30));
		while(Conti && elec360power <= 900000){
			//System.out.println(String.format("Conti's power is at %d", ++elec360power));

			/*if(!drm.isValid()){
				System.exit(7);
			}*/
			
			controllerManager.poll();
			//eventManager.handleEvents(controllerManager.getEvents(), stateContext, renderMaster);
			
			frames++;

			elec360power += 1;
			
			if(elec360power == 100){
				/*pianoComponent.setSoundPosition(1000, 0, 0);
				pianoComponent.stopSound();
				pianoComponent.playSound();*/
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
			triforce.setPosition(new Vector3f(-30f + 60*elec360power/450f, 10, 0));
			
			//rotating text
			text.setRotation(new Vector3f(0,elec360power/1500f, 0));
			
			//set the camera position
			
			Vector4f campos = new Vector4f(0,35,-50, 1);//relative coords
			Vector4f targ = new Vector4f(0,10,0,1);		
			Vector4f campos2 = new Vector4f(0,20, 2 ,1);
			Vector4f targ2 = new Vector4f(0,20,1,1);		

			Matrix4f modelInv = ((DebugGraphicsComponent)pk.graphicsComponent).getInvModelMat(); //cache that shit? actually do I need to?

			Matrix4f.transform(modelInv, campos, campos);
			Matrix4f.transform(modelInv, targ, targ);
			
			Matrix4f.transform(modelInv, campos2, campos2);

			Matrix4f.transform(modelInv, targ2, targ2);

			
			cam.setPosition(new Vector3f(campos));
			cam.setTarget(new Vector3f(targ));
			

			cam2.setPosition(new Vector3f(campos2));
			cam2.setTarget(new Vector3f(targ2));

			
			
			//draw
			renderMaster.draw();

			if(Display.isCloseRequested())
			{
				
				elec360power = 900001;
			}

		}
		this.soundMaster.cleanUpALData();
		System.out.println("CONTI'S ELEC 360 POWER LEVEL IS OVER 9000!!!!!!!!!!!");
		
		float totalTime = (System.currentTimeMillis() - startTime)/1000f;
		System.out.println(totalTime + " total seconds for " + frames + " frames");
		System.out.println("fps ave: " + ((float)frames)/totalTime);
	}
	
}
