package World;

import java.util.Random;

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
	
	private int	id;
	
	
	public Persona(SoundMaster soundMaster, RenderMaster renderMaster){
		this.soundMaster = soundMaster;
		this.renderMaster = renderMaster;
		
		Random generator = new Random(); 
		id = generator.nextInt(4) + 1;

		switch(id){
			case 1:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Bunkai - Bunkai.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Soske - Bunkai.wav", false);
				this.model = renderMaster.addModel("sam");
				break;
			}
			case 2:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/I gotcha now - Sam.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Angry Grunt - Sam.wav", false);
				this.model = renderMaster.addModel("test");
				break;
			}
			case 3:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Look out for - Michael.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Oh Noo - Michael.wav", false);
				this.model = renderMaster.addModel("michael");
				break;
			}
			case 4:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Take that - Da Conti.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/I'll get you - Da Conti.wav", false);
				this.model = renderMaster.addModel("sam");
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
