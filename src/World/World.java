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

	private HashMap<String, DebugGraphicsComponent> 	otherDebugGraphics;
	private HashMap<String, GraphicsComponent>			otherGraphics;
	
	private int elec360power;
	
	public World(RenderMaster renderMaster)
	{
		this.renderMaster = renderMaster;
		
		elec360power = 0;
		
		donutKarts = new ArrayList<Kart>();
		
		otherDebugGraphics	= new HashMap<String, DebugGraphicsComponent>();
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
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		otherDebugGraphics.put("Triforce", triforce);
		
		// Add Awesome Kart Text
		GraphicsComponent text = renderMaster.addModel("aktext");
		text.setPosition(new Vector3f(-200, 40, 100));
		otherGraphics.put("AKText", text);
		

		// Add Lights
		addLights();
		
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
		otherDebugGraphics.get("Triforce").setRotation(new Vector3f(3.14f * (elec360power/1500f),-3.14f * (elec360power/1500f), 3.14f * (elec360power/1500f)));
		otherDebugGraphics.get("Triforce").setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
		
		// Rotating Text
		otherGraphics.get("AKText").setRotation(new Vector3f(0,elec360power/1500f, 0));
		
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
