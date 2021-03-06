package World;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import Graphics.GraphicsComponent;
import Graphics.RenderMaster;
import Sound.SoundEmitter;
import Sound.SoundMaster;

public class Persona {

	private SoundMaster soundMaster;
	private RenderMaster renderMaster;
	
	
	private SoundEmitter shootPerson;
	private SoundEmitter getHit;
	private GraphicsComponent model;
	
	protected Vector3f hatOffset;
	
	private int	id;
	
	
	public Persona(SoundMaster soundMaster, RenderMaster renderMaster){
		this.soundMaster = soundMaster;
		this.renderMaster = renderMaster;
		this.hatOffset = new Vector3f();
		
		Random generator = new Random(); 
		id = generator.nextInt(4) + 1;

		switch(id){
			case 1:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Bunkai - Bunkai.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Soske - Bunkai.wav", false);
				this.model = renderMaster.addModel("test");
				break;
			}
			case 2:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/I gotcha now - Sam.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Angry Grunt - Sam.wav", false);
				this.model = renderMaster.addModel("sam");
				this.hatOffset = new Vector3f(0f,1.5f,0f);
				break;
			}
			case 3:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Look out for - Michael.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Oh Noo - Michael.wav", false);
				this.hatOffset = new Vector3f(0f,4f,0f);
				this.model = renderMaster.addModel("michael");
				break;
			}
			case 4:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Take that - Da Conti.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/I'll get you - Da Conti.wav", false);
				this.model = renderMaster.addModel("pizza");
				this.hatOffset = new Vector3f(0f,3f,0f);
				break;
			}
			default:{ // Justin Kaice
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Take that - Da Conti.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
				this.model = renderMaster.addModel("goop");
			}
		}

	}
	
	public GraphicsComponent getModel()
	{
		return this.model;
	}
	
	public SoundEmitter getShootPerson(){
		return shootPerson;
	}
	
	public SoundEmitter getHit(){
		return getHit;
	}
	
	public int getId(){
		return id;
	}
}
