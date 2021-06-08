package ru.pas;

import ru.pas.libs.Gui;
import ru.pas.libs.Logic;
import java.lang.Thread;

public class Life extends Thread {
    static boolean[][] oldGeneration;
	static boolean newGeneration[][];
	static int steps = 0;
	static boolean stop = false;
	static Logic logic;
	static Gui gui;
	static int currentGen = 0;
	static int arrayLength = 10;
	public static int next = 1;
	public static void main(String[] args){
		
		if (args.length > 0){
			try {
				arrayLength = Integer.parseInt(args[0]);
			}catch (NumberFormatException e){
				arrayLength = 10;
			}
		}
		gui = new Gui(arrayLength);
		gui.run();
		gui.setVisible(true);
		logic = new Logic(arrayLength);
	}
	public static void start(boolean[][] _firstGeneration){
		oldGeneration = _firstGeneration;
		newGeneration = new boolean[arrayLength][arrayLength];
		Runnable task = () -> {
			while (!stop){
				try{
					Thread.sleep(100);
				if (currentGen < next){
					steps ++;
					gui.setTxt("Step: " + steps);
					newGeneration = logic.createNewGeneration(oldGeneration);
					gui.setNewGeneration(newGeneration);
					logic.saveGeneration(oldGeneration);
					oldGeneration = newGeneration;
					boolean compare = logic.compareGenerations(newGeneration);
					if (compare){
						stop = true;
						gui.setTxt("Game Over: Life repeat self generation at " + steps + ".");
						return;
					}
					int liveCells = logic.cellsLiveCount(newGeneration);
					if (liveCells == 0){
						stop = true;
						gui.setTxt("Game Over: All cells die for " + steps + " steps.");
					}
					currentGen = next;
				}
				}catch(InterruptedException e){
					System.out.println("thread interrupted");
				}
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}
}