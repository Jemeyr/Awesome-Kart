import java.util.Random;

import Game.*;



public class Main {

		public static void main(String[] args){
			
			//This is a reminder that we need to combine these
			Random r = new Random();
			if(r.nextBoolean())
			{
				Game2 game = new Game2();
				game.execute();
			}
			else
			{
				Game2 game = new Game2();
				game.execute();
			}
		}
}
