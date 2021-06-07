package ru.pas;

import ru.pas.libs.Gui;
import ru.pas.libs.Logic;

public class Life{
    static boolean[][] oldGeneration;
	static Logic logic;
	static Gui gui;
	public static void main(String[] arvs){
		gui = new Gui(10);
		gui.run();
		gui.setVisible(true);
		logic = new Logic(10);
	}
	public static void start(boolean[][] _firstGeneration){
		oldGeneration = _firstGeneration;
		boolean newGeneration[][] = new boolean[10][10];
		boolean stop = false;
		int steps = 0;
		while (!stop)
		{
			steps ++;
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
		}
	}
}