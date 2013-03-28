package World;

import java.util.Random;

import Sound.SoundEmitter;
import Sound.SoundMaster;

public class Persona {

	private SoundMaster soundMaster;
	
	private SoundEmitter shootPerson;
	private SoundEmitter getHit;
	
	public Persona(SoundMaster soundMaster){
		this.soundMaster = soundMaster;
		
		Random generator = new Random(); 
		int person = generator.nextInt(4) + 1;
		
		switch(person){
			case 1:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
			}
			case 2:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
			}
			case 3:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
			}
			case 4:{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
				getHit		= this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
			}
			default :{
				shootPerson = this.soundMaster.getSoundComponent("assets/sound/alarma.wav", false);
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
}
