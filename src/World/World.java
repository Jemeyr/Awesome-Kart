package World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
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
	protected List<ItemCrate> items;
	protected List<Checkpoint> checkpoints;
	protected List<CollisionBox> walls,pits;
	
	private HashMap<String, GraphicsComponent>	otherGraphics;
	
	private int elec360power;
	
	public World(RenderMaster renderMaster,List<Player> playerList)
	{
		this.renderMaster = renderMaster;
		this.players = playerList;
		
		elec360power = 0;
		
		donutKarts = new ArrayList<Kart>();
		rockets = new ArrayList<Rocket>();
		items = new ArrayList<ItemCrate>();
		checkpoints = new ArrayList<Checkpoint>();
		walls = new ArrayList<CollisionBox>();
		pits = new ArrayList<CollisionBox>();
		
		
		otherGraphics		= new HashMap<String, GraphicsComponent>();
		
		//add Terrain
		addTerrain();
		
		//add wall
		addCollisions();
		
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
		
		// Add Triforce to hit to make items
		ItemCrate itemCrate = new ItemCrate(renderMaster, this);
		itemCrate.setPosition(new Vector3f(-300f + 2f * 150.0f, -20.5f, -300f + 2f * 150.0f));
		itemCrate.setRotation(new Vector3f(0,0,0));
		items.add(itemCrate);
		

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
		
		for(Player player : players){
			for(ItemCrate ic : items){
				ic.update();
				if(ic.collisionBox.bIntersects(player.getKart().collisionBox)){
					player.setHeldItem(ic.generateItem());
					ic.disappear();
				}
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
	
	private void addCollisions()
	{
		/*
		this.walls.add(new CollisionBox(new Vector3f(-18.66f + 916.66f,0f,933.33f+8f), new Vector3f(166.66f,9000f,2200f)));
		this.walls.add(new CollisionBox(new Vector3f(-125.00f - 8.5f,0,458.33f + 18f), new Vector3f(1416.66f + 17f,9000f,583.33f + 12f)));
		this.walls.add(new CollisionBox(new Vector3f(-625f -12,0f,41.66f + 26), new Vector3f(416.66f + 16f,9000f,250f + 40f)));
		this.walls.add(new CollisionBox(new Vector3f(458.33f - 12f,0f,208.33f + 12f), new Vector3f(250f + 16f,9000f,750f)));
		this.walls.add(new CollisionBox(new Vector3f(-583.33f,0f,-666.66f + 32f), new Vector3f(833.33f + 60f,9000f,666.66f)));
		this.walls.add(new CollisionBox(new Vector3f(-83.33f -12f,0f,-541.66f + 32f), new Vector3f(166.66f + 16f,9000f,916.66f)));

		this.walls.add(new CollisionBox(new Vector3f(-1083.33f - 2f,0f,0f), new Vector3f(166.66f,9000f,2200f)));
		this.walls.add(new CollisionBox(new Vector3f(0f,0f,1083.33f + 8.0f), new Vector3f(2200f,9000f,166.66f)));
		*/
		
		/*
		 * 916.66,0 : 166.66,2000
		 * 
		 * -125 458.33:1416.66x583.33
		 * 
		 * -625,41.66: 416.66 x 250
		 * 458.33, 208.33: 250,750
		 * -583.33,-666.66:  833.33x666.66
		 * -41.66, -541.66: 250, 916.66
		 * -1083.33,0: 166.66, 2000
		 * 0,1083.33: 2000,166.66
		 * 
		 */
		
		
		/*
		 *5.5 -4.5: 3x5
		 *
		 *6,-11 :12x2
		 *
		 * 11, -6:2x8
		 * .5, -4.5: 1x9
		 */
		this.pits.add(new CollisionBox(new Vector3f(-12f + 5.5f * 83.33f,0f,20f + -4.5f * 83.33f), new Vector3f( 3f * 83.33f,9000f,5f * 83.33f)));
		this.pits.add(new CollisionBox(new Vector3f(6f * 83.33f,0f,16f + -11f * 83.33f), new Vector3f(12f * 83.33f,9000f,2f * 83.33f)));
		this.pits.add(new CollisionBox(new Vector3f(-8f + 11f * 83.33f,0f,-6f * 83.33f), new Vector3f(2f * 83.33f,9000f,8f * 83.33f)));
		this.pits.add(new CollisionBox(new Vector3f(-16f + 0.5f * 83.33f,0f,16 + -5.5f * 83.33f), new Vector3f(1f * 83.33f,9000f,9f * 83.33f)));
		
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
