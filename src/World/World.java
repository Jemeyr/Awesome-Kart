package World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import Graphics.DebugGraphicsComponent;
import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;

public class World {
	private static final float checkpointThresholdSquared = 10000;
	
	private RenderMaster renderMaster;

	protected List<Kart> donutKarts;
	protected List<Rocket> rockets;
	protected List<Player> players;
	protected List<Checkpoint> checkpoints;
	
	private HashMap<String, GraphicsComponent>	otherGraphics;
	
	private int elec360power;
	
	public World(RenderMaster renderMaster,List<Player> playerList)
	{
		this.renderMaster = renderMaster;
		this.players = playerList;
		
		elec360power = 0;
		
		donutKarts = new ArrayList<Kart>();
		rockets = new ArrayList<Rocket>();
		checkpoints = new ArrayList<Checkpoint>();
		
		otherGraphics		= new HashMap<String, GraphicsComponent>();
		
		//add Terrain
		addTerrain();
		
		// Add Donut Karts
		for(int i = 0; i < 16; i++)
		{
			if(i == 10) i++;
			Kart k = new Kart(this.renderMaster);
			k.killmeVec = new Vector3f(-300f + (i/4) * 150.0f, 0.0f, -300f + (i%4) * 150.0f);
			donutKarts.add(k);
			k.killme = i*1234f;
		}
		
		// Add Triforce
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("rocket");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		otherGraphics.put("Triforce", triforce);
		
		// Add Awesome Kart Text
		GraphicsComponent text = renderMaster.addModel("aktext");
		text.setPosition(new Vector3f(-200, 40, 100));
		otherGraphics.put("AKText", text);
		

		// Add Lights
		addLights();
		
		//Add the list of chekpoints
		
		//Set the last checkpoint in the list to be the finish checkpoint.
		if(checkpoints.size()!=0){
			checkpoints.get(checkpoints.size()).setFinishCheckpoint();
			
			for(int i =0; i< playerList.size();i++){
				playerList.get(i).currCheckPoint = checkpoints.get(0);
				playerList.get(i).nextCheckPoint = checkpoints.get(1);
			}

		}
		
		
		
	}
	
	public void update()
	{
		

		// DO the donut karts
		for(Kart k : donutKarts)
		{ 
			k.killmenow(elec360power);
		}
		elec360power++;
		
		//triforce that rotates and flies away
		otherGraphics.get("Triforce").setRotation(new Vector3f(3.14f * (elec360power/100f),-3.14f * (elec360power/100f), 3.14f * (elec360power/100f)));
		//otherGraphics.get("Triforce").setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
		Vector3f tempRocket = otherGraphics.get("Triforce").getTransformedVector(0, 0, 0, true);
		tempRocket.x = (float) (tempRocket.x * 0.97 + 0.03 * players.get(0).getKart().position.x);
		tempRocket.y = (float) (tempRocket.y * 0.97 + 0.03 * (players.get(0).getKart().position.y + 10));
		tempRocket.z = (float) (tempRocket.z * 0.97 + 0.03 * players.get(0).getKart().position.z);
		otherGraphics.get("Triforce").setPosition(tempRocket);
		
		
		for(Rocket r : rockets)
		{
			r.update();
			if(r.position.lengthSquared() > 320000)
			{
				//kill
			}
		}
		
		// Rotating Text
		otherGraphics.get("AKText").setRotation(new Vector3f(0,elec360power/1500f, 0));
		
	}
	
	
	public void addRocket(Vector3f position, Vector3f rotation)
	{
		Rocket r = new Rocket(position, rotation, renderMaster, this);
	
		this.rockets.add(r);
	}

	private void addLights()
	{
		Light le;
		le = renderMaster.addLight();
		le.setRad(250.0f);
		le.setPosition(new Vector3f(50,10,0));
		le.setColor(new Vector3f(1.0f, 0.0f, 0.0f));
		
		le = renderMaster.addLight();
		le.setRad(250.0f);
		le.setPosition(new Vector3f(-30,10,0));
		le.setColor(new Vector3f(0.0f, 0.0f, 1.0f));
		
		//three big corner lights
		le = renderMaster.addLight();
		le.setRad(3000f);
		le.setPosition(new Vector3f(-1000, 100, -320));
		le.setColor(new Vector3f(1,0,0));
		
		le = renderMaster.addLight();
		le.setRad(3000f);
		le.setPosition(new Vector3f(840, 100, 1000));
		le.setColor(new Vector3f(0,1,0));
		
		le = renderMaster.addLight();
		le.setRad(3000f);
		le.setPosition(new Vector3f(-1000, 100, 1000));
		le.setColor(new Vector3f(0,0,1));
		
		//green pit lights
		le = renderMaster.addLight();
		le.setRad(600f);
		le.setPosition(new Vector3f(40, 100, -120));
		le.setColor(new Vector3f(0,1,0));
		
		le = renderMaster.addLight();
		le.setRad(600f);
		le.setPosition(new Vector3f(800, 100, -916));
		le.setColor(new Vector3f(0,1,0));
		
		le = renderMaster.addLight();
		le.setRad(600f);
		le.setPosition(new Vector3f(440, 100, -360));
		le.setColor(new Vector3f(0,1,0));
		
		
		
		le = renderMaster.addLight();
		le.setRad(600f);
		le.setPosition(new Vector3f(800, 100, -240));
		le.setColor(new Vector3f(0,1,0));
		
		le = renderMaster.addLight();
		le.setRad(600f);
		le.setPosition(new Vector3f(880, 100, -880));
		le.setColor(new Vector3f(0,1,0));

		le = renderMaster.addLight();
		le.setRad(600f);
		le.setPosition(new Vector3f(220, 100, -920));
		le.setColor(new Vector3f(0,1,0));
		
		
		
	}
	
	private void addTerrain(){
		renderMaster.addModel("nightFactory");
	}
	
	/**
	 * Gets the next checkpoint in the list of checkpoints
	 *  
	 * @param curCkPt
	 * @return the next checkpoint, or the first in the case curCkPt is Null or it is the finish checkpoint
	 */
	protected Checkpoint getNextChekpoint(Checkpoint curCkPt){
		int retIndex;
		//If the current checkpt is the last checkpoint in the list, ie the finish line, get the first checkpoint.
		if( curCkPt.isFinishLine)
		{
			retIndex = 0;
		}
		else
		{
			retIndex = checkpoints.lastIndexOf(curCkPt)+1;
		} 
		
		return checkpoints.get(retIndex);
		
	}
	
	protected boolean reachedCheckpoint(Checkpoint nextCkPt, Vector3f position)
	{
		boolean retBool = false;
		Vector3f dist = new Vector3f();
		Vector3f.sub(position, nextCkPt.post, dist);
		
		if(dist.lengthSquared() < checkpointThresholdSquared)
		{
			retBool = true;
		}
		return retBool;
	}
	
	
	
	
}
