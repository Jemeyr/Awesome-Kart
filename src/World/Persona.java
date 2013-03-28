package World;

import java.util.Random;

import Sound.SoundEmitter;
import Sound.SoundMaster;

public class Persona {

	private SoundMaster soundMaster;
	
	private SoundEmitter shootPerson;
	private SoundEmitter getHit;
	
	private int	id;
	
	
	public Persona(SoundMaster soundMaster){
		this.soundMaster = soundMaster;
		
		Random generator = new Random(); 
		id = generator.nextInt(4) + 1;

		switch(id){
			case 1:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Bunkai - Bunkai.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Soske - Bunkai.wav", false);
				break;
			}
			case 2:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/I gotcha now - Sam.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Angry Grunt - Sam.wav", false);
				break;
			}
			case 3:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Look out for  - Michael.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/Oh Noo - Michael.wav", false);
				break;
			}
			case 4:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Take that - Da Conti.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/I'll get you - Da Conti.wav", false);
				break;
			}
			default:{ // Justin Kaice
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/Take that - Da Conti.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
			}
		}
		
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
