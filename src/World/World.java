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
	private RenderMaster renderMaster;

	private List<Kart> donutKarts;
	private List<Rocket> rockets;
	
	private HashMap<String, GraphicsComponent>			otherGraphics;
	
	private int elec360power;
	
	public World(RenderMaster renderMaster)
	{
		this.renderMaster = renderMaster;
		
		elec360power = 0;
		
		donutKarts = new ArrayList<Kart>();
		rockets = new ArrayList<Rocket>();
		
		otherGraphics		= new HashMap<String, GraphicsComponent>();
		
		//add Terrain
		addTerrain();
		
		// Add Donut Karts
		for(int i = 0; i < 16; i++)
		{
			if(i == 10) i++;
			Kart k = new Kart(this.renderMaster);
			k.killmeVec = new Vector3f(-300f + (i/4) * 150.0f, -22.5f, -300f + (i%4) * 150.0f);
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
	}
	
	public void update(List<Player> playerList)
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
		tempRocket.x = (float) (tempRocket.x * 0.97 + 0.03 * playerList.get(0).getKart().position.x);
		tempRocket.y = (float) (tempRocket.y * 0.97 + 0.03 * (playerList.get(0).getKart().position.y + 10));
		tempRocket.z = (float) (tempRocket.z * 0.97 + 0.03 * playerList.get(0).getKart().position.z);
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
		Rocket r = new Rocket(position, rotation, renderMaster);
	
		this.rockets.add(r);
	}

	private void addLights()
	{
		Light le;
		le = renderMaster.addLight();
		le.setRad(250.0f);
		le.setPosition(new Vector3f(50,-20,0));
		le.setColor(new Vector3f(1.0f, 0.0f, 0.0f));
		
		le = renderMaster.addLight();
		le.setRad(250.0f);
		le.setPosition(new Vector3f(-30,-20,0));
		le.setColor(new Vector3f(0.0f, 0.0f, 1.0f));
		
		Random r = new Random();
		
		for(int h = 0; h < 40; h++)
		{
			le = renderMaster.addLight();
			le.setRad(100.0f);
			le.setColor(new Vector3f(r.nextBoolean()?1.0f:0.0f,r.nextBoolean()?1.0f:0.0f,r.nextBoolean()?1.0f:0.0f));
			le.setPosition(new Vector3f(200 - 100 * (h % 6), -10, 200 - 100 * h/6));
		}
	}
	
	private void addTerrain(){
		renderMaster.addModel("testTer");
	}
	
	
	
	
}
